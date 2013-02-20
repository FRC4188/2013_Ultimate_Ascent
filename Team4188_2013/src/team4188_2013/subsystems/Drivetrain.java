// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package team4188_2013.subsystems;
import team4188_2013.RobotMap;
import team4188_2013.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team4188_2013.CorpsRobotDrive;
/**
 *@author Tobore Tasker
 */
public class Drivetrain extends Subsystem {
   // public CorpsRobotDrive robotDrive;
    private boolean resetG = false, resetEnc = false;
    double 
        circumference = 0, 
        revolutions = 0;
        
    private PIDController gyroPID, leftEncPID, rightEncPID;
    Timer timer;
    private boolean timerRunning=false;
    public static final double 
        gyroTOLERANCE = 1.0,
        Pg = 0.025, 
        Ig = 0.000, 
        Dg = 0.0,     
        Pe = 0.025, 
        Ie = 0.000, 
        De = 0.0,                
        PID_LOOP_TIME = .05,
        SETTLED_TIME = 1.0,
        WHEEL_RADIUS = 0,
        BACKUP_SPEED = .15,
        FAST_BACKUP_SPEED = .23;  
    private AnalogChannel temperature;
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    CANJaguar frontLeft = RobotMap.drivetrainFrontLeft;
    CANJaguar rearLeft = RobotMap.drivetrainRearLeft;
    CANJaguar frontRight = RobotMap.drivetrainFrontRight;
    CANJaguar rearRight = RobotMap.drivetrainRearRight;
    Gyro gyro = RobotMap.drivetrainGyro;
    public CorpsRobotDrive robotDrive = RobotMap.drivetrainRobotDrive;
    Encoder leftEnc = RobotMap.drivetrainleftEnc;
    Encoder rightEnc = RobotMap.drivetrainrightEnc;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void init(){
        SmartDashboard.putNumber("driveP", Pg);
        SmartDashboard.putNumber("driveI", Ig);
        SmartDashboard.putNumber("driveD", Dg);
        try{
            frontLeft.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            frontRight.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            rearLeft.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            rearRight.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            } catch (CANTimeoutException ex) {ex.printStackTrace();}
        leftEnc.start();
        rightEnc.start();
        leftEncPID = new PIDController(Pe,Ie,De,leftEnc,frontLeft,PID_LOOP_TIME);
        rightEncPID = new PIDController(Pe,Ie,De,rightEnc,frontRight,PID_LOOP_TIME);      
        leftEncPID.setInputRange(-6,6);       // adjust?
        rightEncPID.setInputRange(-6,6);       // adjust?       
        leftEncPID.setOutputRange(-1.0,1.0);
        rightEncPID.setOutputRange(-1.0,1.0);
        gyro.reset();
        timer = new Timer();
        gyroPID = new PIDController(Pg,Ig,Dg,gyro,robotDrive,PID_LOOP_TIME);
        gyroPID.setInputRange(-90.0, 90.0);
        gyroPID.setOutputRange(-0.65, 0.65);
        temperature = new AnalogChannel(2);
    }
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new ManualDrive());
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
   public boolean autoAimPan(double targetAngle) {
        
        //RobotMap.setDriveTrainMode(RobotMap.GYRO_MODE);
        
        //encPID.disable();
        
        //accelPID.disable();
        if(!resetG) {
            gyro.reset();
            resetG = true;
        }
        if(!gyroPID.isEnable()) gyroPID.enable();
        
        gyroPID.setSetpoint(targetAngle);
        if(thereYet(targetAngle)) {
            disablePID();
            resetG = false;
            return true;
        }
        return false;
    }
   public void disablePID() {
        if(gyroPID.isEnable()) gyroPID.disable();
        //accelPID.disable();
    }
   public boolean thereYet(double target) 
   {
        if(onTarget(target) && !timerRunning) 
        {
            timer.start();
            timerRunning = true;
        }
        else if (!onTarget(target) && timerRunning)
        {
            timer.stop();
            timer.reset();
            timerRunning = false;
        }
        return timer.get() >= SETTLED_TIME;
    }
  
   public double getGyroAngle() 
   {
        return gyro.getAngle();
    }
   public void resetGyro() 
   {
      gyro.reset();
   }
   public void stop()
   {
       robotDrive.mecanumDrive_Cartesian(0.0,0.0,0.0,0.0);
   }
   private boolean onTarget(double target) 
   {
        boolean toReturn=false;
        //switch(RobotMap.getDriveTrainMode()) {
        if(target<0)
        {
        if(gyro.getAngle()<=(target+((gyroTOLERANCE/100.0)*180.0)) &&
            gyro.getAngle()>=(target-((gyroTOLERANCE/100.0)*180.0)))
        {
            toReturn=true;
        }
        else
        {
            toReturn=false;
        }
        }
        //System.out.println("gyro onTarget...");
        if(gyro.getAngle()>=(target-((gyroTOLERANCE/100.0)*180.0)) &&
            gyro.getAngle()<=(target+((gyroTOLERANCE/100.0)*180.0)))
        {
            toReturn = true;
        }
        else
        {
            toReturn = false;
        }
     return toReturn;
    }
   public void resetEncoders(){
       leftEnc.reset();
       rightEnc.reset();
   }
   public void goDistance(double meters){
      if(!resetEnc){
         resetEncoders();
         resetEnc = true;
       }
       circumference = 2 * Math.PI * WHEEL_RADIUS;
       revolutions = meters/circumference;
       if(!leftEncPID.isEnable()) leftEncPID.enable();
       if(!rightEncPID.isEnable()) rightEncPID.enable();
       leftEncPID.setSetpoint(meters);
       try {
        rearRight.setX(leftEncPID.get());  
        } catch (CANTimeoutException ex) {}
       rightEncPID.setSetpoint(-meters);
       try {
        rearLeft.setX(rightEncPID.get());  
        } catch (CANTimeoutException ex) {}       
       if(thereYet(meters)) {
            resetEnc = false;
            disablePID();
        }        
       
   }
   public double getLeftEnc(){
       return leftEnc.getRaw();
   }
   public double getRightEnc(){
       return rightEnc.getRaw();
   }
  
    
   public void setMaxVoltages(double voltage) {
     try {
        frontLeft.configMaxOutputVoltage(voltage);
        frontRight.configMaxOutputVoltage(voltage);
        rearLeft.configMaxOutputVoltage(voltage);
        rearRight.configMaxOutputVoltage(voltage);
            
        } catch (CANTimeoutException ex) { ex.printStackTrace();}
        }
   public void rightBackward(){
       try{
        rearRight.setX(BACKUP_SPEED); 
        frontRight.setX(BACKUP_SPEED);
       } catch (CANTimeoutException ex) { ex.printStackTrace();}
      }
   public void fastRightBackward(){
       try{
        rearRight.setX(FAST_BACKUP_SPEED); 
        frontRight.setX(FAST_BACKUP_SPEED);
       } catch (CANTimeoutException ex) { ex.printStackTrace();}
      } 
   
   public void stopRight(){
        try{
        rearRight.setX(0); 
        frontRight.setX(0);
       } catch (CANTimeoutException ex) { ex.printStackTrace();}      
   }
   public void stopLeft(){
        try{
        rearLeft.setX(0); 
        frontLeft.setX(0);
       } catch (CANTimeoutException ex) { ex.printStackTrace();}      
   }   
   public void leftBackward(){
       try{
        frontLeft.setX(-BACKUP_SPEED); 
        rearLeft.setX(-BACKUP_SPEED);
       } catch (CANTimeoutException ex) { ex.printStackTrace();}
      }   
   public void fastLeftBackward(){
       try{
        frontLeft.setX(-FAST_BACKUP_SPEED); 
        rearLeft.setX(-FAST_BACKUP_SPEED);
       } catch (CANTimeoutException ex) { ex.printStackTrace();}
      }     
}

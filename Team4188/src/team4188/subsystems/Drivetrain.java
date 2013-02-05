
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.CorpsLog;
import team4188.CorpsRobotDrive;
import team4188.RobotMap;
import team4188.commands.ManualDrive;

/**
 *
 * @author Alexander Nordin
 */

public class Drivetrain extends Subsystem {
    
    CANJaguar fleft, fright, bleft, bright;
    public CorpsRobotDrive Drive;
    private Gyro gyro;
    private Timer timer;
    private boolean timerRunning=false;
    public static final double 
            gyroTOLERANCE = 0.5,
            Pg = 0.006, 
            Ig = 0.0002, 
            Dg = 0.0,     // LEAVE THESE CONSTANTS ALONE!
            PID_LOOP_TIME = .05,
            SETTLED_TIME = 1.0;    // LEAVE THESE CONSTANTS ALONE!;
    private PIDController gyroPID;
    private boolean resetG = false;
    //private PIDController leftEncPID, rightEncPID;
    public void initDefaultCommand() {
        setDefaultCommand(new ManualDrive ());

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
 public void init() {
 
        try {
            fleft = new CANJaguar(RobotMap.FRONT_LEFT_DRIVE_MOTOR); // ID number of jaguar
            fright = new CANJaguar(RobotMap.FRONT_RIGHT_DRIVE_MOTOR);
            bleft = new CANJaguar(RobotMap.BACK_LEFT_DRIVE_MOTOR);
            bright = new CANJaguar(RobotMap.BACK_RIGHT_DRIVE_MOTOR);

        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

        // Create a robot using standard right/left robot drive on the CAN bus
        Drive = new CorpsRobotDrive(fleft,bleft,fright,bright);
        Drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
        Drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
        Drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight,false);
        Drive.setInvertedMotor(RobotDrive.MotorType.kRearRight,false);
        Drive.setSafetyEnabled(false);
 
        
        CorpsLog.log("proportion",Pg,true,false); //not entirely sure where these statements should be
        CorpsLog.log("integral",Ig,true,false); //^
        CorpsLog.log("derivative",Dg,true,false);  //^
        gyro = new Gyro(RobotMap.GYRO);
        gyro.reset();

        timer = new Timer();

        gyroPID = new PIDController(Pg,Ig,Dg,gyro,Drive,PID_LOOP_TIME);
        gyroPID.setInputRange(-90.0, 90.0);
        gyroPID.setOutputRange(-0.6, 0.6);
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
       Drive.mecanumDrive_Cartesian(0.0,0.0,0.0,0.0);
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
   
   
  
    
    public void setMaxVoltages(double voltage) {
        try {
            fleft.configMaxOutputVoltage(voltage);
            fright.configMaxOutputVoltage(voltage);
            bleft.configMaxOutputVoltage(voltage);
            bright.configMaxOutputVoltage(voltage);
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
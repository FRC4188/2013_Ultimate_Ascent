/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.RobotMap;
import team4188.commands.DriveWithJoystick;

/**
 *
 * @author Alexander Nordin
 */

public class Drivetrain extends Subsystem {
    
    CANJaguar fleft, fright, bleft, bright;
    RobotDrive Drive;
    private Gyro gyro;

    //private PIDController leftEncPID, rightEncPID;
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick ());

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
        Drive = new RobotDrive(fleft,bleft,fright,bright);
        gyro = new Gyro(RobotMap.GYRO);
        Drive.setSafetyEnabled(false);
 
    }

   public void driveCartesian(double X, double Y, double T, Joystick stick){
       
       Drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
       Drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
       Drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight,false);
       Drive.setInvertedMotor(RobotDrive.MotorType.kRearRight,false);
       Drive.mecanumDrive_Cartesian(X, Y, T, 0.0);
   }
   public void driveCartesianGyro(double X, double Y, double T, double angle){
      Drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
      Drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
      Drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight,false);
      Drive.setInvertedMotor(RobotDrive.MotorType.kRearRight,false);
      Drive.mecanumDrive_Cartesian(X,Y,T,angle);
   }
   public void DriveStraight(){
       Drive.mecanumDrive_Cartesian(RobotMap.aS,0.0,0.0,0.0);
   }
   public void moveLeft(){
       Drive.mecanumDrive_Cartesian(0.0,-RobotMap.aS,0.0,0.0);
   }
   public void moveRight(){
       Drive.mecanumDrive_Cartesian(0.0,RobotMap.aS,0.0,0.0);
   }
   public void driveBack(){
       Drive.mecanumDrive_Cartesian(-RobotMap.aS,0.0,0.0,0.0);
   }
   public void stop(){
       Drive.mecanumDrive_Cartesian(0.0,0.0,0.0,0.0);
   }
   public void autoSpin(){
       Drive.mecanumDrive_Cartesian(0.0,0.0,RobotMap.sS,0.0);
   }
   public void negAutoSpin(){
       Drive.mecanumDrive_Cartesian(0.0,0.0,-RobotMap.sS,0.0);
   }
   public double getGyroAngle() {
        return gyro.getAngle();
    }
   public void resetGyro() {
      gyro.reset();
   }
   
   //public void disablePID() {
    
        //if(leftEncPID.isEnable()) leftEncPID.disable();
        //if(rightEncPID.isEnable()) rightEncPID.disable();
       
  //  }
    
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

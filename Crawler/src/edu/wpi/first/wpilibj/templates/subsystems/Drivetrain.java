/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.*;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.commands.DriveWithJoystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author Alexander Nordin
 */

public class Drivetrain extends Subsystem {

    CANJaguar left, right;
    private Encoder leftEnc;
    private Encoder rightEnc;
    RobotDrive driveTrain;
    public final static double MAX_VOLTAGE = 5.0;
    //private PIDController leftEncPID, rightEncPID;
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick ());

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
 public void init() {
 
        try {
            left = new CANJaguar(RobotMap.LEFT_DRIVE_MOTOR); // ID number of jaguar
            right = new CANJaguar(RobotMap.RIGHT_DRIVE_MOTOR);
            left.configMaxOutputVoltage(MAX_VOLTAGE);
            right.configMaxOutputVoltage(MAX_VOLTAGE);
            //driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
            //driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

        // Create a robot using standard right/left robot drive on the CAN bus
        driveTrain = new RobotDrive( left,right);

        
        //driveTrain.setSafetyEnabled(false);
 
    }       
   public void drive(Joystick stick) {
       // disablePID();
       Joystick joy;
        // -Y for test bot, Y for real bot / X on real bot, -X on test bot.
        joy = stick; 
        // Y for test bot, -Y for real bot / X on real bot, -X on test bot.
        driveTrain.arcadeDrive(joy);
    }
    public void disablePID() {
    
        //if(leftEncPID.isEnable()) leftEncPID.disable();
        //if(rightEncPID.isEnable()) rightEncPID.disable();
       
    }
    public void setMaxVoltages(double voltage) {
        try {
            left.configMaxOutputVoltage(voltage);
            right.configMaxOutputVoltage(voltage);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }


}

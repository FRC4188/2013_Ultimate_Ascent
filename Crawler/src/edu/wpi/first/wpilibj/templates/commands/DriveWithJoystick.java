/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.commands;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.templates.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.templates.commands.*;
import edu.wpi.first.wpilibj.command.Scheduler;
/**
 *
 * @author Alexander Nordin
 */
public class DriveWithJoystick extends CommandBase {
    private double throttle = 1.0;
    public DriveWithJoystick() {
        //driveTrain_m = Drivetrain.getInstance();
        requires(drivetrain); //reserves the Drivetrain subsystem
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    //drivetrain.driveWithJoystick(oi.getJoystick());
        //throttle = ((oi.getJoystick().getThrottle()*0.6)+0.4);
        //if(throttle == 0){
       //     throttle = 1.0;
        
        //}
        //drivetrain.drive((oi.getJoystick().getY()*throttle),(oi.getJoystick().getX()*throttle), true);
        //throttle = oi.getJoystick().getThrottle();
        //if(throttle < 0){throttle = throttle * -1;}
        
      //  drivetrain.setMaxVoltages(throttle*12.0);
        drivetrain.drive(oi.getJoystick());
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188_2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import team4188_2013.Robot;

/**
 *
 *@author Tobore Tasker
 * attempt to fix jaguar shutting down in autonomous
 * do not use this, use ramp up 
 */
public class HalfSpeed extends Command {
    
    public HalfSpeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.shooter.firstWheelHalf();
        Robot.shooter.secondWheelHalf();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

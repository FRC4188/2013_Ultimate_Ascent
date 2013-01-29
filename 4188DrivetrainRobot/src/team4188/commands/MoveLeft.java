/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

/**
 *
 * @author Alexander Nordin
 */
public class MoveLeft extends CommandBase {
    private double Timeout;
    public MoveLeft(double time) {
    Timeout=time;
        requires(drivetrain);    
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(Timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    drivetrain.moveLeft();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import team4188.RobotMap;

/**
 *
 * @author Alexander Nordin
 */
public class spinDegrees extends CommandBase {
    private double degree;
    public spinDegrees(double degrees) {
    requires(drivetrain);
    degree=degrees;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    setTimeout(degree/RobotMap.turnSpeed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    //create loop for degrees to spin or change command to match degrees to spin
        drivetrain.autoSpin();
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

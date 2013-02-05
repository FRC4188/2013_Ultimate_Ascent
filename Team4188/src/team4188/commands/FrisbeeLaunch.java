/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Sarah
 */
public class FrisbeeLaunch extends CommandBase {
    boolean shot;
    public FrisbeeLaunch() {
        requires(shooter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    if(shooter.isOn)
        {
            shooter.push();
            Timer.delay(0.1);
            shooter.retract();
        }   
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

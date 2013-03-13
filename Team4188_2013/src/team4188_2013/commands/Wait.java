/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188_2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Erin King
 */
public class Wait extends Command {
    private double time;
    private boolean isDone = false;
    private Timer timer;
    
    public Wait(double time) {
        this.time = time;
        timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(timer.get() >= time) isDone = true;
        else isDone = false;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}

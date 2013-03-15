/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188_2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import team4188_2013.Robot;
/**
 *
 * @author toboretasker
 */
public class EnableCompressor extends Command {
    
    public EnableCompressor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        if(!Robot.climber.isCompressorEnabled()){
            Robot.climber.enableCompressor();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.climber.isCompressorEnabled();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

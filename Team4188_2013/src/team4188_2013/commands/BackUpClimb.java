// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package team4188_2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import team4188_2013.Robot;
/**
 *@author Tobore Tasker
 */
public class  BackUpClimb extends Command {

    public BackUpClimb() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    // Called just before this Command runs the first time
    protected void initialize() {
        if(Robot.climber.isRetracted()){
            Robot.climber.extendClimber();
        }
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Robot.climber.isExtended())
        {
            if(Robot.climber.hitRight() && Robot.climber.hitLeft()){
                Robot.drivetrain.rightBackward();
                Robot.drivetrain.leftBackward();
            }
            else if(!Robot.climber.hitRight() && Robot.climber.hitLeft()){
                Robot.drivetrain.fastLeftBackward();
                Robot.drivetrain.stopRight();
            }
            else if(Robot.climber.hitRight() && !Robot.climber.hitLeft()){
                Robot.drivetrain.fastRightBackward();
                Robot.drivetrain.stopLeft();
            }
            else{
                Robot.drivetrain.stopLeft();
                Robot.drivetrain.stopRight();
                if(Robot.climber.isExtended()){
                    Robot.climber.retractClimber();

                }            
            }       
        }
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
    // Called once after isFinished returns true
    protected void end() {
        Robot.drivetrain.stopLeft();
        Robot.drivetrain.stopRight();        
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}

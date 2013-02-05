/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package team4188.commands;
import team4188.RobotMap;
import team4188.commands.CommandBase;

/**
 *
 * @author Alexander Nordin
 */
public class ManualDrive extends CommandBase {
    double direction=0;
    public ManualDrive() {
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
    
        //x strafe value
        //y move forward value
        //z twist value


        if(RobotMap.fOM){
            direction=drivetrain.getGyroAngle();
        }
        else{
            direction=0.0;
        }
        drivetrain.Drive.mecanumDrive_Cartesian((oi.pilot.getX())*-1*oi.pilot.getThrottle()
                ,oi.pilot.getY()*oi.pilot.getThrottle()
                ,oi.pilot.getTwist()*-1*oi.pilot.getThrottle(), direction);
        
        
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
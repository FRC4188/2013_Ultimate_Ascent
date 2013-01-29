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
public class DriveWithJoystick extends CommandBase {
    double direction=0;
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
    
        
        //x strafe value
        //y move forward value
        //z twist value
        if(oi.getJoystick().getTrigger())
        {
            drivetrain.resetGyro();
        }
        if (oi.getJoystick().getRawButton(11)&&RobotMap.fOM){
            RobotMap.fOM = false;
        }
        else if (oi.getJoystick().getRawButton(12)&&!RobotMap.fOM){
            RobotMap.fOM = true;
        }
        if(RobotMap.fOM){
            direction=drivetrain.getGyroAngle();
        }
        else{
            direction=0.0;
        }
        drivetrain.driveMecanum((oi.getJoystick().getX()+0.00)*-1*oi.stick.getThrottle(),oi.getJoystick().getY()*oi.stick.getThrottle(),oi.getJoystick().getTwist()*-1*oi.stick.getThrottle(), direction);
        
        
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
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
    //drivetrain.driveWithJoystick(oi.getJoystick());
        //throttle = ((oi.getJoystick().getThrottle()*0.6)+0.4);
        //if(throttle == 0){
       //     throttle = 1.0;
        
        //}
        //drivetrain.drive((oi.getJoystick().getY()*throttle),(oi.getJoystick().getX()*throttle), true);
        oi.stick.setXDeadZone(RobotMap.xNegDeadZone*oi.stick.getThrottle()
        ,RobotMap.xPosDeadZone*oi.stick.getThrottle());
        oi.stick.setYDeadZone(RobotMap.yNegDeadZone*oi.stick.getThrottle(),RobotMap.yPosDeadZone*oi.stick.getThrottle());
        oi.stick.setTwistDeadZone(RobotMap.zNegDeadZone*oi.stick.getThrottle(),RobotMap.zPosDeadZone*oi.stick.getThrottle());        
        double x=oi.getJoystick().getX()*-1; //strafe value
        double y=oi.getJoystick().getY()*1; //move forward value
        double z=oi.getJoystick().getTwist()*-1; //twist value
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
        drivetrain.driveCartesianGyro(x*oi.stick.getThrottle(), y*oi.stick.getThrottle(), z*oi.stick.getThrottle(), direction);
        
        //drivetrain.drive(mag*throttle,angle*throttle,z*throttle,oi.getJoystick());
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

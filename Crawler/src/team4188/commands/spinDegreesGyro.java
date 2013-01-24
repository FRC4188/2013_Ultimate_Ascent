/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

/**
 *
 * @author Alexander Nordin
 */
public class spinDegreesGyro extends CommandBase {
    private double degree;
    private double degreeChecker;
    private double initialDegree;
    private boolean onTarget=false;
    public spinDegreesGyro(double degrees) {
    requires(drivetrain);
    degree=degrees;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    //angle from camera
    initialDegree=drivetrain.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    //drivetrain.resetGyro();
//    if(degree>0)
//        {
            while(drivetrain.getGyroAngle()<degree)
            {
            drivetrain.autoSpin();
            }
            onTarget=true;
//        }
//    if(degree<0)
//        {
//            while((degreeChecker-initialDegree)>(degree))
//            {
//            drivetrain.negAutoSpin();
//            }
//        }
//        onTarget=true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return onTarget;
    }

    // Called once after isFinished returns true
    protected void end() {
    onTarget=false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    end();
    }
}

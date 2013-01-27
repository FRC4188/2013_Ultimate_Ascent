/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;
import team4188.OI;
import team4188.CorpsServo;
import team4188.subsystems.Vision;

/**
 *
 * @author toboretasker
 */
public class Manual extends CommandBase {
    private double throttle;
    public Manual() {
        // Use requires() here to declare subsystem dependencies
         requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("Manual Intializing");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
         //vision.panX.goToPosition(oi.getJoystick().getX());
         throttle = ((oi.stick.getThrottle()));
        
         drivetrain.manualControl(-oi.stick.getX()*throttle,1);
         drivetrain.manualControl(oi.stick.getY()*throttle,2);
          //drivetrain.panY.set(.5);
         
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

/**
 *
 * @author Sarah
 */
public class turnXDegrees extends CommandBase {
    private double Angle;
    private boolean isAimed;
    public turnXDegrees(double angle) {
        requires(drivetrain);
        this.Angle=angle;
        System.out.println("im in the constructor");
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    isAimed=false;
    drivetrain.resetGyro();
    System.out.println("im in the init");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    isAimed=drivetrain.autoAimPan(this.Angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isAimed;
    }

    // Called once after isFinished returns true
    protected void end() {
    drivetrain.disablePID();
    isAimed=false;
    System.out.println("im done");
    
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    end();
    }
}

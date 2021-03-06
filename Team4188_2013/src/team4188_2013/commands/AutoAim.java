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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team4188_2013.Robot;
/**
 *@author Tobore Tasker
 */
public class  AutoAim extends Command {
    private ParticleAnalysisReport target;
    private double angle;
    private boolean isAimed;
    final static double 
            TOLERANCE =0.01,
            mTilt = -.406,
            bTilt = 159.56,
            mPan = .56,
            bPan = -1.63,
            MID_DISTANCE = 210;
    boolean 
            isTargeted = false, 
            aimed = false;
    double 
        targetDistance = 0.0, 
        angleX = 0.0, 
        angleY = 0.0,
        tiltValue = 0.0;
    int input = 0;
    Timer timer = new Timer();
  
    public AutoAim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.shooter);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("AutoAim Initializing");
        Robot.servo.yUp();
        aimed = false;
        isTargeted = false;
        angleX = 0.0;
        angleY = 0.0;
        
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!Robot.vision.areLightsOn()){
            Robot.vision.turnlightsOn();
        }
        if(Robot.vision.getReports())
        {   
            
            //SmartDashboard.putInt("Targets", vision.getFound());
            //TargetDistance = vision.getDistance();
            isTargeted = Robot.vision.getTargeted();
            System.out.println("isTargeted: " + isTargeted);
            if(isTargeted == false){
               // SmartDashboard.putString("Pan", "Looking...");
                System.out.println("Looking for targets...");
                angleX = 0.0;
                angleY = 0.0;
                targetDistance = 0.0;
            }
            else{
               System.out.println("Aiming...");
               
               target = Robot.vision.getTopTarget();
               targetDistance = Robot.vision.getDistance();
               SmartDashboard.putNumber("Distance", targetDistance);
               angleX = Robot.vision.calculateHorizontalAngle(target, targetDistance);
               SmartDashboard.putNumber("AngleX", angleX);
               angleY = Robot.vision.calculateTiltAngle(target, targetDistance);
               SmartDashboard.putNumber("AngleY", angleY);
               System.out.println("Angle Y: " + angleY);
               //isAimed();
               aim();
//                if(aimed == false)
//                {
//                  aim();
//                }
            }        
        }        
    }
    private void aim(){
       // double move = 0;
        //double servoAngle = panX.getAngle();
        //double servoPos = panX.get();
        /*
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();      
        angleX = vision.calculateHorizontalAngle(target, TargetDistance);
       */
        //angleX = getAverageAngle();
       // System.out.println("Angle: " + angleX);
       // move = angleX/5;
    //    drivetrain.panX.goToAngle(drivetrain.getPosition() - (move));
        
        //vision.panX.goToAngle(position - (move/2));
       // System.out.println("Panning Angle: "+ servoAngle);
        //System.out.println("Panning Position: "+ servoPos);
      //  turnXDegrees(vision.calculateHorizontalAngle(target, targetDistance));
        //tiltValue = targetDistance * mTilt + bTilt;
        if(targetDistance < (MID_DISTANCE)){
            tiltValue = Robot.shooter.getFrontLeft();
        }
        else{
            tiltValue = Robot.shooter.getBackLeft();
        }
        System.out.println("TiltValue from AutoAim: " + tiltValue);
        //angleX = angleX + angleX * mPan + bPan;
        if(Robot.shooter.isPIDEnable()){
            Robot.shooter.updateSetPoint(tiltValue);
        }
        else{
            Robot.shooter.updateSetPoint(tiltValue);
            Robot.shooter.autoTilt();
        }
        //Robot.shooter.autoTilt(tiltValue);
        //Robot.drivetrain.autoAimPan(-angleX);
        if(!aimed){
            //isAimed = Robot.drivetrain.autoAimPan(angleX);  
            aimed = true;
        }
        
        
        //drivetrain.setPosition(drivetrain.getPosition()-move);
       // position = vision.getPosition(); //- (move) - move/3;
       // System.out.println("Position: " + drivetrain.getPosition());
    }
    private void isAimed(){
        //double sAngle = panX.getAngle();
        //double diff = Math.abs(sAngle - angleX);
        System.out.println("AngleX: " + angleX);
        if((Math.abs(angleX) >= TOLERANCE)){
           // System.out.println("Angle Diff: " + diff);
            aimed = false;
        }
        else{
            aimed = true;
        }
        
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return aimed;
    }
    // Called once after isFinished returns true
    protected void end() {
        Robot.drivetrain.disablePID();
        isAimed=false;
        aimed = false;
        Robot.vision.turnlightsOff();
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}

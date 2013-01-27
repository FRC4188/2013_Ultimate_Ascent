/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import team4188.subsystems.Vision;
import team4188.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import team4188.OI;
import team4188.CorpsServo;
/**
 *
 * @author toboretasker
 */
public class Pan extends CommandBase {
    private ParticleAnalysisReport target;

    final static double 
            xMin = 0,
            xMax = 58,
            yMin = 0,
            yMax = 100,
            TOLERANCE = 1.0,
            //BoardDistance = 60.0, //60.0 inches 
            X_RANGE = 116;
    static double position = 90.0;
    
    boolean isTargeted = false, aimed = false;
    double 
        targetDistance = 0.0, angleX = 0.0, angleY = 0.0;
    int input = 0;
    Timer timer = new Timer();
  
    public Pan() {
        // Use requires() here to declare subsystem dependencies
          requires(vision);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("Pan Initializing");

        aimed = false;
        isTargeted = false;
        angleX = 0.0;
        angleY = 0.0;
        //position = 90; //start at 90 degrees
         //go to starting position
       // panX.setAngle(input);
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // panX.goToPosition(oi.getJoystick().getX());
         //System.out.println(oi.getJoystick().getY());
         //panY.goToPosition(-oi.getJoystick().getY());
         
        
        vision.getReports();
        //SmartDashboard.putInt("Targets", vision.getFound());
        //TargetDistance = vision.getDistance();
        isTargeted = vision.getTargeted();
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
           target = vision.getTopTarget();
           targetDistance = vision.getDistance();
           angleX = vision.calculateHorizontalAngle(target, targetDistance);
           angleY = vision.calculateTiltAngle(target, targetDistance);
            isAimed();
            //System.out.println("Angle X: "+ angleX);
           System.out.println("Angle Y: " + angleY);
            if(aimed == false)
            {
             aim();
            }
            //TargetDistance = 0.0;
            //panY.setAngle(((angleY)+1)/2);
           // System.out.println("Titling");
            
            //panX.setAngle(((angleX)+1)/4);
            //System.out.println("panning..");
           // SmartDashboard.putString("pan", "Panning");
        }        
        
        
        
        
        
        /*
        for(int i = 0; i < 18; i++)
        {
            System.out.println(input);
            panX.goToAngle(input);
            input += 10;
            timer.delay(1);
            
        }
        */
    }

    
    private void aim(){
        double move = 0;
        //double servoAngle = panX.getAngle();
        //double servoPos = panX.get();
        /*
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();      
        angleX = vision.calculateHorizontalAngle(target, TargetDistance);
       */
        //angleX = getAverageAngle();
        System.out.println("Angle: " + angleX);
        move = angleX/5;
        drivetrain.panX.goToAngle(drivetrain.getPosition() - (move));
        //vision.panX.goToAngle(position - (move/2));
       // System.out.println("Panning Angle: "+ servoAngle);
        //System.out.println("Panning Position: "+ servoPos);
        
        aimed = true;
        drivetrain.setPosition(drivetrain.getPosition()-move);
       // position = vision.getPosition(); //- (move) - move/3;
        System.out.println("Position: " + drivetrain.getPosition());
    }
    private double getAverageAngle(){
        double average = 0, a = 0, b = 0, c = 0;
        vision.getReports();
        target = vision.getTopTarget();
        targetDistance = vision.getDistance();
        a = vision.calculateHorizontalAngle(target, targetDistance);
        /*
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();
        b = vision.calculateHorizontalAngle(target, TargetDistance); 
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();
        c = vision.calculateHorizontalAngle(target, TargetDistance); 
        */
        average = (a );
        System.out.println("Average Horizontal Angle: " + average );
        return average;
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

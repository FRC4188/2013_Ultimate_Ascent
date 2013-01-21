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
    CorpsServo panY, panX; 
    final static double 
            xMin = 0,
            xMax = 116,
            TOLERANCE = 1.0,
            //BoardDistance = 60.0, //60.0 inches 
            X_RANGE = 116;
    static double position = 90;
    boolean isTargeted = false;
    boolean aimed = false;
    double TargetDistance = 0.0;
    double angleX = 0.0, angleY = 0.0;
    int input = 0;
    Timer timer = new Timer();
    public Pan() {
        // Use requires() here to declare subsystem dependencies
          requires(vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("Pan Initializing");
        panX = new CorpsServo(xMin,xMax, RobotMap.panX);
        panY = new CorpsServo(0, 100, RobotMap.panY);
        panX.goToAngle(90.0);
        aimed = false;
        isTargeted = false;
       // panX.setAngle(input);
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // panX.goToPosition(oi.getJoystick().getX());
         //System.out.println(oi.getJoystick().getY());
         //panY.goToPosition(-oi.getJoystick().getY());
         
        
        vision.getReports();
        SmartDashboard.putInt("Targets", vision.getFound());
        //TargetDistance = vision.getDistance();
        isTargeted = vision.getTargeted();
        if(isTargeted == false){
           // SmartDashboard.putString("Pan", "Looking...");
            System.out.println("Looking for targets...");
         
        }
        
        else{
            target = vision.getTopTarget();
            TargetDistance = vision.getTopDistance();
            angleX = vision.calculateHorizontalAngle(target, TargetDistance);
            angleY = vision.calculateTiltTop(TargetDistance);
            isAimed();
            System.out.println("Angle X: "+ angleX);
           // System.out.println("Angle Y: " + angleY);
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

    // Make this return true when this Command no longer needs to run execute()
    private void aim(){
        //double servoAngle = panX.getAngle();
        //double servoPos = panX.get();
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();      
        angleX = vision.calculateHorizontalAngle(target, TargetDistance);
        panX.goToAngle(position - angleX);
       // System.out.println("Panning Angle: "+ servoAngle);
        //System.out.println("Panning Position: "+ servoPos);
        aimed = true;
        position = position - angleX;
        System.out.println("Position: " + position);
    }
    private double getAverageAngle(){
        double average = 0, a = 0, b = 0, c = 0;
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();
        a = vision.calculateHorizontalAngle(target, TargetDistance);
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();
        b = vision.calculateHorizontalAngle(target, TargetDistance); 
        vision.getReports();
        target = vision.getTopTarget();
        TargetDistance = vision.getTopDistance();
        c = vision.calculateHorizontalAngle(target, TargetDistance); 
        average = (a + b +c)/3;
        System.out.println("Average Horizontal Angle: " + average );
        return average;
    }
    private void isAimed(){
        //double sAngle = panX.getAngle();
        //double diff = Math.abs(sAngle - angleX);
        if((Math.abs(angleX)) >= TOLERANCE){
           // System.out.println("Angle Diff: " + diff);
            aimed = false;
        }
        else{
            aimed = true;
        }
        
    }
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

    public double acos(double x) {
       return (-0.69813170079773212 * x * x - 0.87266462599716477) * x + 1.5707963267948966;
    }
}

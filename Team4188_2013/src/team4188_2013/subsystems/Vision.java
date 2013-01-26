/*
 * Vision class.
 * Defines the components and methods of the camera component on
 * Team 4188's robot for the 2012 FIRST Robotics game Rebound Rumble.
 */
package team4188_2013.subsystems;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import team4188_2013.RobotMap;
import team4188_2013.CorpsLog;
/**
 * @author Erin
 */
public class Vision extends Subsystem {
    private static final boolean USE_CAMERA = true;
    private AxisCamera camera;
    //private Servo camX, camY;
    private ColorImage original;
    private BinaryImage thresholdImage, targetImage, filledInImage, image;
    private CriteriaCollection criteria;
    ParticleAnalysisReport[] reports = null;
    private static final int
            RED_LOW = 0,        // this set is for the green camera on production robot
            RED_HIGH = 30,
            GREEN_LOW = 30,
            GREEN_HIGH = 255,
            BLUE_LOW = 0,
            BLUE_HIGH = 75;
            /*RED_LOW = 0,      // this set is for the green camera on test robot
            RED_HIGH = 230,
            GREEN_LOW = 200,
            GREEN_HIGH = 255,
            BLUE_LOW = 100,
            BLUE_HIGH = 255;*/
            /*RED_LOW = 80,     // this set is for the still image
            RED_HIGH = 255,
            GREEN_LOW = 0,
            GREEN_HIGH = 180,
            BLUE_LOW = 0,
            BLUE_HIGH = 180;*/
            /*HUE_LOW = 0,      // this set is for the white camera
            HUE_HIGH = 255,
            SAT_LOW = 0,
            SAT_HIGH = 255,
            LUM_LOW = 220,
            LUM_HIGH = 255;*/
    private static final double BACKBOARD_DISTANCE = 0.381,  // Distance from center of hoop to backboard [15 inches]
            TARGET_HEIGHT = (2.7686-0.83185),   // Height from shooter pivot to center of top target [shooter=32.75 in./target=109 in.]
            REAL_TARGET_WIDTH = 0.6096,         // 24 inches converted to meters
            REAL_TARGET_HEIGHT = 0.4572,        // 18 inches converted to meters
            DIST_MID_OUTER = 0.695325,          // Real distance between top(middle) target and either left/right target [27.375 in. = 0.695325 m]
            FOV_RADS = 0.827,                   // ~41 degrees
            //TOTAL_PIX_WIDTH = 320.0,
            DIST_FULL_VIEW_W = (REAL_TARGET_WIDTH/2.0)/Math.tan(FOV_RADS/2.0),
            DIST_FULL_VIEW_H = (REAL_TARGET_HEIGHT/2.0)/Math.tan(FOV_RADS*0.75/2.0),
            G = 9.8,                // Gravitational acceleration constant = 9.8 m/(s^2)
            MIN_ANGLE = 0.436,      // 0.436 radians = 25 degrees, our minimum.
            MAX_ANGLE = 0.942;      // 0.942 radians = 54 degrees, our maximum.
    public void initDefaultCommand() {}
    
    public void init() {
        CorpsLog.log("Vision Status","initializing",false,true);
        if (USE_CAMERA) {
          camera = AxisCamera.getInstance();
          camera.writeResolution(AxisCamera.ResolutionT.k320x240);
          camera.writeCompression(30);
        }
        
        criteria = new CriteriaCollection();
        criteria.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 25, 400, false);
        criteria.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 20, 400, false);
        
        // SERVO CHANNELS
        //camX = new Servo(RobotMap.camPan);
        //camY = new Servo(RobotMap.camTilt);
        
        CorpsLog.log("Vision Status","initialized",false,true);
    }
    
    /*public void steerForward(double inputX, double inputY) {
        // Control the camera-orientation servo motors.
        camX.set(((inputX)+1)/4);     // X control of half of the servo range.
        camY.set(((inputY)+1)/2);     // Y control stays the same.
    }
    
    public void steerReverse(double inputX, double inputY) {
        // Control the camera-orientation servo motors.
        camX.set(((inputX)+3)/4);     // X control of the other half of the servo range.
        camY.set(((inputY)+1)/2);     // Y control stays the same.
    }*/
    
    private ParticleAnalysisReport[] getReports() {
        try {
            if (USE_CAMERA) {
              original = camera.getImage();
            } else {
              original = new RGBImage("/10ft2.jpg");
            }
            thresholdImage = original.thresholdRGB(RED_LOW, RED_HIGH, GREEN_LOW,
                    GREEN_HIGH, BLUE_LOW, BLUE_HIGH);     // For green camera
            //thresholdImage = original.thresholdHSL(HUE_LOW, HUE_HIGH, SAT_LOW,
            //        SAT_HIGH, LUM_LOW, LUM_HIGH);           // For white camera
            targetImage = thresholdImage; // comment out to removeSmallObjects
            //targetImage = thresholdImage.removeSmallObjects(false, 2);// remove small artifacts
            filledInImage = targetImage.convexHull(false);            // fill in occluded rectangles
            image = filledInImage.particleFilter(criteria);           // find filled in rectangles
            //image = targetImage;
            CorpsLog.log("Number of Particles",image.getNumberParticles(),true,true);
            if (image.getNumberParticles() != 0)
                return image.getOrderedParticleAnalysisReports();
            else return null;
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
            return null;
        } catch (NIVisionException ex) {
            ex.printStackTrace();
            return null;
        } finally {freeImages();}
    }
    
    public ParticleAnalysisReport getTopTarget() {
        int top = 0;
        reports = getReports();
        for(int i = 0; i < reports.length; i++){
            ParticleAnalysisReport r = reports[i];          // print results
            CorpsLog.log("Particle " + i + "  center of mass y: ",r.imageHeight - r.center_mass_y,false,true);
            // images are upside-down: 0 on the Y is at the top,
            // so the highest target has the lowest Y value.
            if (reports[i].center_mass_y < reports[top].center_mass_y) top = i;
        }
        CorpsLog.log("Top Target particle",top,false,true);
        return reports[top];
    }
    
    public ParticleAnalysisReport getLeftRight() {
        int left = 0, right = 0, closest = 0;
        reports = getReports();
        for(int i = 0; i < reports.length; i++){
            ParticleAnalysisReport r = reports[i];          // print results
            CorpsLog.log("Particle " + i + "  center of mass x: ",r.center_mass_x,false,true);
            if (reports[i].center_mass_x < reports[left].center_mass_x) left = i;
            else if (reports[i].center_mass_x > reports[right].center_mass_x) right = i;
        }
        CorpsLog.log("Left Target particle",left,false,true);
        CorpsLog.log("Right Target particle",right,false,true);
        if(reports[left].boundingRectWidth > reports[right].boundingRectWidth)
            closest = left;
        else if(reports[left].boundingRectWidth < reports[right].boundingRectWidth)
            closest = right;
        CorpsLog.log("Closest particle",closest,false,true);
        return reports[closest];
    }
    
    public double getDistanceToTarget(ParticleAnalysisReport target) {
        double targetPixelWidth, targetPixelHeight, diagonal, answer;
        //double theta;
        //targetPixelWidth = target.boundingRectWidth;
        targetPixelHeight = target.boundingRectHeight;
            //CorpsLog.log("Target width",targetPixelWidth,false,true);
            CorpsLog.log("Target height",targetPixelHeight,false,true);
            //CorpsLog.log("Image width",target.imageWidth);
        // [not used] theta = topGoalWidth*(FOV_RADS/TOTAL_PIX_WIDTH);
        //theta = FOV_RADS*(topGoalWidth/target.imageWidth);
            //CorpsLog.log("FOV angle of target",theta);
        //diagonal = REAL_TARGET_WIDTH/theta;
        //diagonal = (target.imageWidth/targetPixelWidth)*DIST_FULL_VIEW_W;
        diagonal = (target.imageHeight/targetPixelHeight)*DIST_FULL_VIEW_H;
            //CorpsLog.log("Distance to target that takes up whole FOV",DIST_FULL_VIEW);
            CorpsLog.log("Diagonal distance to target",diagonal,false,true);
        answer = Math.sqrt((diagonal*diagonal)-(TARGET_HEIGHT*TARGET_HEIGHT));//-BACKBOARD_DISTANCE;
            //CorpsLog.log("Distance to target Luther's way",answer1);
            CorpsLog.log("Distance to target",answer,true,true);
        return answer;
    }
    
    /**
     * @param distance Horizontal distance from camera to target.
     * @param v0 Velocity of ball projectiles from shooter
     * @return Returns angle (in degrees) at which the tilt mechanism needs to be aimed.
     */
    public double calculateTiltTargetAngle(double distance, double v0) {
        double angle = MIN_ANGLE, h = 0.0,
                tolerance = 0.05;           // 1 cm of error
        while(h<TARGET_HEIGHT-tolerance || h>TARGET_HEIGHT+tolerance){
            if(h<TARGET_HEIGHT-tolerance) angle+=0.01;
            else if (h>TARGET_HEIGHT+tolerance) angle-=0.01;
            h = (distance*Math.tan(angle))-((distance*distance*G)/
                    (2*v0*v0*Math.cos(angle)*Math.cos(angle)));
            //System.out.println("h: "+h);
            //System.out.println("theta: "+angle);
            if(Math.abs(angle) > MAX_ANGLE || Math.abs(angle) < MIN_ANGLE) {
                angle = 0.0;
                break;
            }
        }
        angle = Math.toDegrees(angle);
        CorpsLog.log("Target angle",angle,true,true);
        return angle;
    }
    /**
     * @param target Particle of the top goal target from the camera.
     * @return Returns angle (in degrees) to which the DriveTrain needs to pan.
     */
    public double calculatePanTargetAngle(ParticleAnalysisReport target,
            double distanceToTopTarget, ParticleAnalysisReport otherTarget) {
        double center_center = target.center_mass_x;
        //double side_center = otherTarget.center_mass_x;
        double image_width = target.imageWidth;
        double angle = FOV_RADS*((center_center-(image_width/2))/image_width);
        //double phi = FOV_RADS*((side_center-center_center)/image_width);
        //CorpsLog.log("phi",phi,false,true);
        //Camera on test bot is angled slightly off shooter's center...
        angle = Math.toDegrees(angle);
        CorpsLog.log("original pan angle",angle,false,true);
        //angle = adjustAngle(angle,phi,distanceToTopTarget,otherTarget);
        angle = angle + RobotMap.getPanTrim();
        CorpsLog.log("Target pan position",angle,true,true);
        return angle;
    }
    
    private double adjustAngle(double x, double phi, double distanceToTopTarget, ParticleAnalysisReport otherTarget) {
        double toReturn;
        boolean neg = false;
        if(phi < 0 ) neg = true;
        phi = Math.abs(phi);          //??
        double distanceToExtremety = getDistanceToTarget(otherTarget);
        double y = (BACKBOARD_DISTANCE*Math.sqrt(1-(((distanceToExtremety/DIST_MID_OUTER)*Math.sin(phi))*
                ((distanceToExtremety/DIST_MID_OUTER)*Math.sin(phi)))));
        double z = distanceToTopTarget+(BACKBOARD_DISTANCE*((distanceToExtremety/DIST_MID_OUTER)*Math.sin(phi)));
        double answer = arcTan(y/z);
        if(neg) toReturn = x - answer;
        else toReturn = x + answer;
        CorpsLog.log("adjusted angle",toReturn,false,true);
        return toReturn;
    }
    
    /**
     * uses first three terms of Taylor polynomial series to find Tan^(-1),
     * because a cRio can't.
     * @return angle in radians.
     */
    private double arcTan(double x) {
        return x-((x*x*x)/3)+((x*x*x*x*x)/5);//-((x*x*x*x*x*x*x)/7);
    }
    
    public void displayTargets() {
        setNetworkTable(reports);
    }
    
    private void setNetworkTable(ParticleAnalysisReport[] r) {
        for(int i = 0; i < r.length; i++) {
            NetworkTable nt = NetworkTable.getTable("vision"+i);
            nt.putInt("count",r.length);
            nt.putInt("centerx",r[i].center_mass_x);
            nt.putInt("centery",r[i].center_mass_y);
            nt.putInt("width",r[i].boundingRectWidth);
            nt.putInt("height",r[i].boundingRectHeight);
        }
    }
    
    private void freeImages() {
        try {
            if(original!=null) original.free();
            if(thresholdImage!=null) thresholdImage.free();
            if(targetImage!=null) targetImage.free();
            if(filledInImage!=null) filledInImage.free();
            if(image!=null) image.free();
        }
        catch (NIVisionException ex) {
        }
    }
}

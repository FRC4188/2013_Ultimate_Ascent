package team4188.subsystems;

import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.commands.*;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVision.Rect;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import team4188.RobotMap;
import team4188.OI;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Sample program to use NIVision to find rectangles in the scene that are illuminated
 * by a LED ring light (similar to the model from FIRSTChoice). The camera sensitivity
 * is set very low so as to only show light sources and remove any distracting parts
 * of the image.
 * 
 * The CriteriaCollection is the set of criteria that is used to filter the set of
 * rectangles that are detected. In this example we're looking for rectangles with
 * a minimum width of 30 pixels and maximum of 400 pixels.
 * 
 * The algorithm first does a color threshold operation that only takes objects in the
 * scene that have a bright green color component. Then a convex hull operation fills 
 * all the rectangle outlines (even the partially occluded ones). Then a small object filter
 * removes small particles that might be caused by green reflection scattered from other 
 * parts of the scene. Finally all particles are scored on rectangularity, aspect ratio,
 * and hollowness to determine if they match the target.
 *
 * Look in the VisionImages directory inside the project that is created for the sample
 * images as well as the NI Vision Assistant file that contains the vision command
 * chain (open it with the Vision Assistant)
 */

public class Vision extends Subsystem {
    
    boolean targeted = false;
    final int 
            XMAXSIZE = 24,
            XMINSIZE = 24,
            YMAXSIZE = 24,
            YMINSIZE = 48;
    final double
            TOP_HEIGHT = 104.125, // height above the ground, in inches, of top of top target
            BOTTOM_HEIGHT = 88.625, // height above the ground, in inches, of bottom of top target
            ERROR =  -1.0513,
            B = 124.76;
    final double xMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double xMin[] = {.4, .6, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, 0.6, 0};
    final double yMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double yMin[] = {.4, .6, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
                     								.05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
    								.05, .05, .6, 0};
    ParticleAnalysisReport 
            toptarget = null, 
            lowtarget = null;
    int 
            top = 0, 
            bottom = 0,
            found = 0;
    final static double 
            REAL_TARGET_WIDTH = 62, 
            //REAL_TARGET_WIDTH = 1.3716, 
            REAL_TARGET_HEIGHT = 0.6096,
            topGoalWidth = 387, //adjust
            topGoalHeight = 640, //adjust
            FOV_RADS = 0.92729,
            DIST_FULL_VIEW_W = (REAL_TARGET_WIDTH/2.0)/Math.tan(FOV_RADS/2.0),
            DIST_FULL_VIEW_H = (REAL_TARGET_HEIGHT/2.0)/Math.tan(FOV_RADS*0.75/2.0),
            DIS_H = 4,
            DIS_W = 14,
            DIS_MID = 14,
           // VIEW_ANGLE = 48,        //Axis M1011 camera
            VIEW_ANGLE = 54.0;       //Axis 206 camera;
                  
   
    final int 
            X_IMAGE_RES = 640,          //X Image resolution in pixels, should be 160, 320 or 640
            /*HUE_LOW = 10, 
            HUE_HIGH = 255 , 
            SAT_LOW = 182, 
            SAT_HIGH = 255,  //for test target with one ring of LED's
            VALUE_HIGH = 255, 
            VALUE_LOW = 200, */
             /*HUE_LOW = 50, 
            HUE_HIGH = 238 ,
            SAT_LOW = 41,
            SAT_HIGH = 255,   //with only one ring of LED's
            VALUE_LOW = 148,
            VALUE_HIGH = 203;*/
            HUE_LOW = 0, 
            HUE_HIGH = 255 ,
            SAT_LOW = 222,  //with three rings of LED's
            SAT_HIGH = 255,  
            VALUE_LOW = 224,
            VALUE_HIGH = 255;
    double 
            topdistance = 0,
            tiltAngle = 0,
            distance = 0.0,
            lowdistance = 0;
    final static int 
            RECTANGULARITY_LIMIT = 60,
            ASPECT_RATIO_LIMIT = 75,
            X_EDGE_LIMIT = 40,
            Y_EDGE_LIMIT = 60,
            PARTICLE_ANALYSIS_REPORTS = 20;
    
    ParticleAnalysisReport[] reports = null;
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation

    
    public class Scores {
        double rectangularity;
        double aspectRatioInner;
        double aspectRatioOuter;
        double xEdge;
        double yEdge;
    }

    public void init() {

        System.out.println("Initializing Vision");
        reports = new ParticleAnalysisReport[PARTICLE_ANALYSIS_REPORTS]; //new targets array
        camera = AxisCamera.getInstance();  // get an instance of the camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 500, 65535, false);
        camera.writeCompression(30); // write camera resolution 0 - 100
        camera.writeResolution(AxisCamera.ResolutionT.k640x480); // write camera resolution
        System.out.println("Vision Initialized");
    }

    public ParticleAnalysisReport[] getReports() {
        try {
            /**
             * Do the image capture with the camera and apply the algorithm described above. This
             * sample will either get images from the camera or from an image file stored in the top
             * level directory in the flash memory on the cRIO. The file name in this case is "testImage.jpg"
             * 
             */
            reports = new ParticleAnalysisReport[PARTICLE_ANALYSIS_REPORTS];
            ColorImage image = camera.getImage();     // comment if using stored images
            //ColorImage image;                           // next 2 lines read image from flash on cRIO
            //image = new RGBImage("/testImage.jpg");		// get the sample image from the cRIO flash
            BinaryImage thresholdImage = image.thresholdHSV(HUE_LOW, HUE_HIGH , SAT_LOW, SAT_HIGH,  VALUE_LOW, VALUE_HIGH);   // keep only red objects
            //thresholdImage.write("/threshold.bmp");
            BinaryImage convexHullImage = thresholdImage.convexHull(false);          // fill in occluded rectangles
            //convexHullImage.write("/convexHull.bmp");
            BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // filter out small particles
            //filteredImage.write("/filteredImage.bmp");

            //iterate through each particle and score to see if it is a target
            Scores scores[] = new Scores[filteredImage.getNumberParticles()];
            int j = 0;
            for (int i = 0; i < scores.length; i++) {
                
                ParticleAnalysisReport report = filteredImage.getParticleAnalysisReport(i);
                
                scores[i] = new Scores();
                scores[i].rectangularity = scoreRectangularity(report);
                scores[i].aspectRatioOuter = scoreAspectRatio(filteredImage,report, i, true);
                scores[i].aspectRatioInner = scoreAspectRatio(filteredImage, report, i, false);
                scores[i].xEdge = scoreXEdge(thresholdImage, report);
                scores[i].yEdge = scoreYEdge(thresholdImage, report);

                if(scoreCompare(scores[i], false))
                {
                    
                    reports[j++] = report;
                    
                    System.out.println("particle: " + (j -1)  + " is a High Goal  centerX: " + report.center_mass_x + " centerY: " + report.center_mass_y);
                    //distance = computeDistance(thresholdImage, report, i, false);
                    distance = getDistanceToTarget(report);
                    System.out.println("Distance: " + distance);
                   // if(topdistance == 0) distance = topDistance;
                    //if(topdistance < distance) distance = topDistance;
                    targeted = true;
                    
                    top = (j-1);
                    
                    
                    
                } else if (scoreCompare(scores[i], true)) {
                    System.out.println("particle: " + i + "is a Middle Goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
                    distance = computeDistance(thresholdImage, report, i, false);
                    System.out.println("Distance: " + distance);
                    if(lowdistance == 0) distance = lowdistance;
                    if(distance < lowdistance){
                        lowdistance = distance;
                        bottom = i;
                        found++;
                    }                    
                } else {
                    //targeted = false;
                    //System.out.println("particle: " + i + "is not a goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
                }
                    //System.out.println("rect: " + scores[i].rectangularity + "ARinner: " + scores[i].aspectRatioInner);
                    //System.out.println("ARouter: " + scores[i].aspectRatioOuter + "xEdge: " + scores[i].xEdge + "yEdge: " + scores[i].yEdge);	
                }
            if(reports != null){
                displayTargets();
            }
            if(reports[top] == null){
                targeted = false;
                top = 0;
            }

            /**
             * all images in Java must be freed after they are used since they are allocated out
             * of C data structures. Not calling free() will cause the memory to accumulate over
             * each pass of this loop.
             */
            filteredImage.free();
            convexHullImage.free();
            thresholdImage.free();
            image.free();

            } catch (AxisCameraException ex) {        // this is needed if the camera.getImage() is called
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    

    
    public void initDefaultCommand() {
         //setDefaultCommand(new Manual());
    }

    
    /**
     * Computes the estimated distance to a target using the height of the particle in the image. For more information and graphics
     * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
     * 
     * @param image The image to use for measuring the particle estimated rectangle
     * @param report The Particle Analysis Report for the particle
     * @param outer True if the particle should be treated as an outer target, false to treat it as a center target
     * @return The estimated distance to the target in Inches.
     */
   //copied from Erin's code, then edited. 
    public double getDistanceToTarget(ParticleAnalysisReport target) {
        double targetPixelWidth, targetPixelHeight, diagonal, answer;
        double theta;
        targetPixelWidth = target.boundingRectWidth;
        targetPixelHeight = target.boundingRectHeight;
            //CorpsLog.log("Target width",targetPixelWidth,false,true);
      //      CorpsLog.log("Target height",targetPixelHeight,false,true);
            //CorpsLog.log("Image width",target.imageWidth);
        System.out.println("Target Width: " + target.boundingRectWidth);
        theta = FOV_RADS*(target.boundingRectWidth/topGoalWidth);
            
        //CorpsLog.log("FOV angle of target",theta);
        diagonal = REAL_TARGET_WIDTH/theta;
        //diagonal = (target.imageWidth/targetPixelWidth)*DIST_FULL_VIEW_W;
        //diagonal = (target.imageHeight/targetPixelHeight)*DIST_FULL_VIEW_H;
        
        

          //CorpsLog.log("Distance to target that takes up whole FOV",DIST_FULL_VIEW);
        //    CorpsLog.log("Diagonal distance to target",diagonal,false,true);
       //answer = Math.sqrt((diagonal*diagonal)-(REAL_TARGET_HEIGHT*REAL_TARGET_HEIGHT));//-BACKBOARD_DISTANCE;
            //CorpsLog.log("Distance to target Luther's way",answer1);
         
        return diagonal;
    }
    /*
    public double getDistanceToTarget(ParticleAnalysisReport target) {
        double targetPixelHeight, answer;
        targetPixelHeight = target.boundingRectHeight;
        System.out.println(target.boundingRectHeight);
        answer = targetPixelHeight * ERROR + B;
        return answer;
    }  */
    public double calculateTiltAngle(ParticleAnalysisReport target, double distance){
        double targetPixelHeight, centerHeight, opposite, angle;
        centerHeight = target.imageHeight/2;
        targetPixelHeight = target.boundingRectHeight;
        opposite = targetPixelHeight - centerHeight;
        angle = arcTan(opposite/distance);
        angle = Math.toDegrees(angle);
        return angle;
    }

    

     /**
     * @param target Particle of the top goal target from the camera.
     * @return Returns angle (in degrees) to which the DriveTrain needs to pan.
     */
    //Copied from Erin's Code, unedited.
    public double calculateHorizontalAngle(ParticleAnalysisReport target, double distanceToTopTarget) 
    {
        double center_center = target.center_mass_x;
        double image_width = target.imageWidth;
        double angle = FOV_RADS*((center_center-(image_width/2))/image_width);
        angle = Math.toDegrees(angle);
        return angle;
    }

    
    double computeDistance (BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException {
            double rectShort, height;
            int targetHeight;

            rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
            //using the smaller of the estimated rectangle short side and the bounding rectangle height results in better performance
            //on skewed rectangles
            height = Math.min(report.boundingRectHeight, rectShort);
            targetHeight = outer ? 29 : 21;

            return X_IMAGE_RES * targetHeight / (height * 12 * 2 * Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
    }
    

    
    public double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException
    {
        double rectLong, rectShort, aspectRatio, idealAspectRatio;

        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        idealAspectRatio = outer ? (62/29) : (62/20);	//Dimensions of goal opening + 4 inches on all 4 sides for reflective tape
	
        //Divide width by height to measure aspect ratio
        if(report.boundingRectWidth > report.boundingRectHeight){
            //particle is wider than it is tall, divide long by short
            aspectRatio = 100*(1-Math.abs((1-((rectLong/rectShort)/idealAspectRatio))));
        } else {
            //particle is taller than it is wide, divide short by long
                aspectRatio = 100*(1-Math.abs((1-((rectShort/rectLong)/idealAspectRatio))));
        }
	return (Math.max(0, Math.min(aspectRatio, 100.0)));		//force to be in range 0-100
    }
    
    /**
     * Compares scores to defined limits and returns true if the particle appears to be a target
     * 
     * @param scores The structure containing the scores to compare
     * @param outer True if the particle should be treated as an outer target, false to treat it as a center target
     * 
     * @return True if the particle meets all limits, false otherwise
     */
    boolean scoreCompare(Scores scores, boolean outer){
            boolean isTarget = true;

            isTarget &= scores.rectangularity > RECTANGULARITY_LIMIT;
            if(outer){
                    isTarget &= scores.aspectRatioOuter > ASPECT_RATIO_LIMIT;
            } else {
                    isTarget &= scores.aspectRatioInner > ASPECT_RATIO_LIMIT;
            }
            isTarget &= scores.xEdge > X_EDGE_LIMIT;
            isTarget &= scores.yEdge > Y_EDGE_LIMIT;

            return isTarget;
    }
    
    /**
     * Computes a score (0-100) estimating how rectangular the particle is by comparing the area of the particle
     * to the area of the bounding box surrounding it. A perfect rectangle would cover the entire bounding box.
     * 
     * @param report The Particle Analysis Report for the particle to score
     * @return The rectangularity score (0-100)
     */
    double scoreRectangularity(ParticleAnalysisReport report){
            if(report.boundingRectWidth*report.boundingRectHeight !=0){
                    return 100*report.particleArea/(report.boundingRectWidth*report.boundingRectHeight);
            } else {
                    return 0;
            }	
    }
    
    /**
     * Computes a score based on the match between a template profile and the particle profile in the X direction. This method uses the
     * the column averages and the profile defined at the top of the sample to look for the solid vertical edges with
     * a hollow center.
     * 
     * @param image The image to use, should be the image before the convex hull is performed
     * @param report The Particle Analysis Report for the particle
     * 
     * @return The X Edge Score (0-100)
     */
    public double scoreXEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException
    {
        double total = 0;
        LinearAverages averages;
        
        Rect rect = new Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
        averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_COLUMN_AVERAGES, rect);
        float columnAverages[] = averages.getColumnAverages();
        for(int i=0; i < (columnAverages.length); i++){
                if(xMin[(i*(XMINSIZE-1)/columnAverages.length)] < columnAverages[i] 
                   && columnAverages[i] < xMax[i*(XMAXSIZE-1)/columnAverages.length]){
                        total++;
                }
        }
        total = 100*total/(columnAverages.length);
        return total;
    }
    
    /**
	 * Computes a score based on the match between a template profile and the particle profile in the Y direction. This method uses the
	 * the row averages and the profile defined at the top of the sample to look for the solid horizontal edges with
	 * a hollow center
	 * 
	 * @param image The image to use, should be the image before the convex hull is performed
	 * @param report The Particle Analysis Report for the particle
	 * 
	 * @return The Y Edge score (0-100)
	 *
    */

    public double scoreYEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException
    {
        double total = 0;
        LinearAverages averages;
        
        Rect rect = new Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
        averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_ROW_AVERAGES, rect);
        float rowAverages[] = averages.getRowAverages();
        for(int i=0; i < (rowAverages.length); i++){
                if(yMin[(i*(YMINSIZE-1)/rowAverages.length)] < rowAverages[i] 
                   && rowAverages[i] < yMax[i*(YMAXSIZE-1)/rowAverages.length]){
                        total++;
                }
        }
        total = 100*total/(rowAverages.length);
        return total;
    }
        /**
     * uses first three terms of Taylor polynomial series to find Tan^(-1),
     * because a cRio can't.
     * @return angle in radians.
     */
    //Copied from Erin's Code
    private double arcTan(double x) {
        return x-((x*x*x)/3)+((x*x*x*x*x)/5);//-((x*x*x*x*x*x*x)/7);
    }
    public void displayTargets() {
        setNetworkTable(reports);
    }
    
    private void setNetworkTable(ParticleAnalysisReport[] r) {
        int length = 0;
        System.out.println("r.length = " + r.length);
        for(int i = 0; i < r.length; i++){
           if(r[i] == null){
               System.out.println("r[" + i + "] is null!");
               break;
           }
           length++;
        }
        
        for(int i = 0; i < length; i++) {
            System.out.println("r[" + i + "] = " + r[i]);
            //if(r[i] == null)break;
            NetworkTable nt = NetworkTable.getTable("vision"+i);
            nt.putInt("count",length);
            nt.putInt("centerx",r[i].center_mass_x);
            nt.putInt("centery",r[i].center_mass_y);
            nt.putInt("width",r[i].boundingRectWidth);
            nt.putInt("height",r[i].boundingRectHeight);
            
        }
    } 
        public boolean getTargeted(){
        return targeted;
    }
    public int getFound(){
        return found;
    }
    public ParticleAnalysisReport getTopTarget() {
        return reports[top];
    }
    public ParticleAnalysisReport getbottomTarget() {
        return reports[bottom];
    }
    public double getTopDistance()
    {
        return topdistance;
    }
    public double getLowDistance()
    {
        return lowdistance;
    }
    public double getDistance()
    {
        return distance;
    }
    public double arcCos(double x) {
        double answer;
        answer  = (-0.69813170079773212 * x * x - 0.87266462599716477) * x + 1.5707963267948966;
        answer = Math.toDegrees(answer);
        return answer;
    }    
}

// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package team4188_2013;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; 
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Vector;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
        /* DAR I don't think these static methods and variables are appropriate in this class.
       RobotMap is understood to be a place for setting system-wide constants, not implementing
       functionality, isn't it?  Would these be better in RobotMain?
       */
    private static final double MAX_ANGLE = 54.0;
    private static double currentAngle = MAX_ANGLE,      //MAX ANGLE?
            //pan trim is in degrees off center, speed is in rps
            panTrim = -1.0, speedTrim = 1.5;
    
    private static String pilotMode = "Manual Driving",
            copilotMode = "Manual Aiming";
    private static int prevCopilotXScale = 2;// driveTrainMode = 0;
    //private static Shooter.SpeedMode shooterVoltage = Shooter.SpeedMode.kLow;
    private static double shooterSpeed = 0.0;
    private static boolean driveReverse = false;
    
    public static double getCurrentAngle() {return MAX_ANGLE;}              // this is temp. - change to currentAngle if you want manual control...
    public static void setCurrentAngle(double angle) {currentAngle = angle;}
    public static double getPanTrim() {return panTrim;}
    public static void setPanTrim(double trim) {panTrim = trim;}
    public static double getSpeedTrim() {return speedTrim;}
    public static void setSpeedTrim(double trim) {speedTrim = trim;}
    //public static int getDriveTrainMode() {return driveTrainMode;}
    //public static void setDriveTrainMode(int newMode) {driveTrainMode = newMode;}
    
    public static String getPilotMode() {return pilotMode;}
    public static String getCopilotMode() {return copilotMode;}
    public static int getPrevCopilotXScale() {return prevCopilotXScale;}
    public static void setPilotMode(String newMode) {pilotMode = newMode;}
    public static void setCopilotMode(String newMode) {copilotMode = newMode;}
    public static void setPrevCopilotXScale(int newScale) {prevCopilotXScale = newScale;}
    //public static void setShooterSpeed(Shooter.SpeedMode voltage) {shooterVoltage = voltage;}
    //public static Shooter.SpeedMode getShooterSpeed() {return shooterVoltage;}
    public static void setShooterSpeed(double speed) {shooterSpeed = speed;}
    public static double getShooterSpeed() {return shooterSpeed;}
    // true is for reverse, false is for forward
    public static void setDriveReverse(boolean set) {driveReverse = set;}
    public static boolean getDriveReverse() {return driveReverse;}
    
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANJaguar drivetrainCANJaguar1;
    public static CANJaguar drivetrainCANJaguar2;
    public static CANJaguar drivetrainCANJaguar3;
    public static CANJaguar drivetrainCANJaguar4;
    public static RobotDrive drivetrainRobotDrive;
    public static Gyro drivetrainGyro;
    public static SpeedController drivetrainVirtualSpeedController;
    public static PIDController drivetrainPanPIDControl;
    public static CANJaguar shooterCANJaguar5;
    public static Encoder shooterTiltEncoder;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        try { 
            drivetrainCANJaguar1 = new CANJaguar(11);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            drivetrainCANJaguar2 = new CANJaguar(12);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            drivetrainCANJaguar3 = new CANJaguar(13);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            drivetrainCANJaguar4 = new CANJaguar(14);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        drivetrainRobotDrive = new RobotDrive(drivetrainCANJaguar1, drivetrainCANJaguar2,
              drivetrainCANJaguar3, drivetrainCANJaguar4);
	
        drivetrainRobotDrive.setSafetyEnabled(true);
        drivetrainRobotDrive.setExpiration(0.1);
        drivetrainRobotDrive.setSensitivity(0.5);
        drivetrainRobotDrive.setMaxOutput(1.0);
        drivetrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drivetrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        drivetrainGyro = new Gyro(1, 1);
	LiveWindow.addSensor("Drivetrain", "Gyro", drivetrainGyro);
        drivetrainGyro.setSensitivity(0.0070);
        drivetrainVirtualSpeedController = new Victor(1, 1);
	LiveWindow.addActuator("Drivetrain", "Virtual Speed Controller", (Victor) drivetrainVirtualSpeedController);
        
        drivetrainPanPIDControl = new PIDController(1.0, 0.0, 0.0, 0.0, drivetrainGyro, drivetrainVirtualSpeedController, 0.02);
	LiveWindow.addActuator("Drivetrain", "Pan PID Control", drivetrainPanPIDControl);
        drivetrainPanPIDControl.setContinuous(false); drivetrainPanPIDControl.setAbsoluteTolerance(0.2); 
        drivetrainPanPIDControl.setOutputRange(-1.0, 1.0);        
        try { 
            shooterCANJaguar5 = new CANJaguar(15);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        shooterTiltEncoder = new Encoder(1, 2, 1, 3, false, EncodingType.k4X);
	LiveWindow.addSensor("Shooter", "Tilt Encoder", shooterTiltEncoder);
        shooterTiltEncoder.setDistancePerPulse(1.0);
        shooterTiltEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        shooterTiltEncoder.start();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
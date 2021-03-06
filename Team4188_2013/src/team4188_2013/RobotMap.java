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
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static int panX = 2, panY = 1;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANJaguar drivetrainFrontLeft;
    public static CANJaguar drivetrainRearLeft;
    public static CANJaguar drivetrainFrontRight;
    public static CANJaguar drivetrainRearRight;
    public static Gyro drivetrainGyro;
    public static CorpsRobotDrive drivetrainRobotDrive;
    public static Encoder drivetrainleftEnc;
    public static Encoder drivetrainrightEnc;
    public static AnalogChannel shootertiltPot;
    public static CANJaguar shooterfirstWheel;
    public static CANJaguar shootersecondWheel;
    public static CANJaguar shootertilt;
    public static DoubleSolenoid shooterfeederSol;
    public static DoubleSolenoid shooterpushSol;
    public static Relay retrieverintake;
    public static DigitalInput climberClimbIn;
    public static DoubleSolenoid climberClimbSol;
    public static Compressor climberCompressor;
    public static DigitalInput climberClimbOut;
    public static DigitalInput climberhitLeft;
    public static DigitalInput climberhitRight;
    public static Relay visionLights;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static final double aS=0.25;
    public static final double sS=0.5;
    public static final double turnSpeed=110;
    public static final double
            xNegDeadZone=-1,
            xPosDeadZone=1,
            yNegDeadZone=-1,
            yPosDeadZone=1,
            zNegDeadZone=-1,
            zPosDeadZone=1;        
    public static final int
            xMult=1,
            yMult=1,
            twistMult=2;
    public static boolean 
            fOM=false,
            onTarget=false;
    
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        try { 
            drivetrainFrontLeft = new CANJaguar(12);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            drivetrainRearLeft = new CANJaguar(11);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            drivetrainFrontRight = new CANJaguar(13);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            drivetrainRearRight = new CANJaguar(14);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        drivetrainGyro = new Gyro(1, 1);
	LiveWindow.addSensor("Drivetrain", "Gyro", drivetrainGyro);
        drivetrainGyro.setSensitivity(0.007);
        drivetrainRobotDrive = new CorpsRobotDrive(drivetrainFrontLeft, drivetrainRearLeft,
              drivetrainFrontRight, drivetrainRearRight);
	
        drivetrainRobotDrive.setSafetyEnabled(false);
        drivetrainRobotDrive.setExpiration(0.1);
        drivetrainRobotDrive.setSensitivity(0.5);
        drivetrainRobotDrive.setMaxOutput(0.6);
        drivetrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drivetrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        drivetrainleftEnc = new Encoder(1, 6, 1, 7, false, EncodingType.k1X);
	LiveWindow.addSensor("Drivetrain", "leftEnc", drivetrainleftEnc);
        drivetrainleftEnc.setDistancePerPulse(1.0);
        drivetrainleftEnc.setPIDSourceParameter(PIDSourceParameter.kDistance);
        drivetrainleftEnc.start();
        drivetrainrightEnc = new Encoder(1, 8, 1, 9, false, EncodingType.k1X);
	LiveWindow.addSensor("Drivetrain", "rightEnc", drivetrainrightEnc);
        drivetrainrightEnc.setDistancePerPulse(1.0);
        drivetrainrightEnc.setPIDSourceParameter(PIDSourceParameter.kDistance);
        drivetrainrightEnc.start();
        shootertiltPot = new AnalogChannel(1, 4);
	LiveWindow.addSensor("Shooter", "tiltPot", shootertiltPot);
        
        try { 
            shooterfirstWheel = new CANJaguar(16);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            shootersecondWheel = new CANJaguar(17);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        try { 
            shootertilt = new CANJaguar(15);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
	
        
        shooterfeederSol = new DoubleSolenoid(1, 3, 4);      
	
        
        shooterpushSol = new DoubleSolenoid(1, 5, 6);      
	
        
        retrieverintake = new Relay(1, 2);
	LiveWindow.addActuator("Retriever", "intake", retrieverintake);
        
        climberClimbIn = new DigitalInput(1, 2);
	LiveWindow.addSensor("Climber", "ClimbIn", climberClimbIn);
        
        climberClimbSol = new DoubleSolenoid(1, 1, 2);      
	
        
        climberCompressor = new Compressor(1, 1, 1, 1);
	
        
        climberClimbOut = new DigitalInput(1, 3);
	LiveWindow.addSensor("Climber", "ClimbOut", climberClimbOut);
        
        climberhitLeft = new DigitalInput(1, 4);
	LiveWindow.addSensor("Climber", "hitLeft", climberhitLeft);
        
        climberhitRight = new DigitalInput(1, 5);
	LiveWindow.addSensor("Climber", "hitRight", climberhitRight);
        
        visionLights = new Relay(1, 3);
	LiveWindow.addActuator("Vision", "Lights", visionLights);
        
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
    }
}

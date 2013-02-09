// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package team4188_2013.subsystems;
import team4188_2013.RobotMap;
import team4188_2013.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class Shooter extends Subsystem {
    public boolean isOn = false;
    final static double 
        MAX_VOLTAGE = 12.0,
        FORWARD = 1.0, 
        OFF = 0.0;
    private static final double PNEUMATIC_DELAY_SECONDS = 0.1;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    CANJaguar firstWheel = RobotMap.shooterfirstWheel;
    CANJaguar secondWheel = RobotMap.shootersecondWheel;
    CANJaguar tilt = RobotMap.shootertilt;
    Encoder tiltEncoder = RobotMap.shootertiltEncoder;
    DigitalInput tiltBottomSw = RobotMap.shootertiltBottomSw;
    DigitalInput tiltTopSw = RobotMap.shootertiltTopSw;
    Relay feederSol = RobotMap.shooterfeederSol;
    DigitalInput feederIn = RobotMap.shooterfeederIn;
    DigitalInput feederOut = RobotMap.shooterfeederOut;
    DigitalInput frisbeeLoadedSw = RobotMap.shooterfrisbeeLoadedSw;
    DigitalInput frisbeeOutSw = RobotMap.shooterfrisbeeOutSw;
    Relay frisbeePushSol = RobotMap.shooterfrisbeePushSol;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        
    }
    public void init(){
        try{
            firstWheel.configMaxOutputVoltage(MAX_VOLTAGE);
            secondWheel.configMaxOutputVoltage(MAX_VOLTAGE);
            firstWheel.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            secondWheel.configNeutralMode(CANJaguar.NeutralMode.kCoast);
           } catch (CANTimeoutException ex) {ex.printStackTrace();}
    }
    public void shooterOn(double modifier){
        try{
            firstWheel.setX(FORWARD * modifier);
            secondWheel.setX(FORWARD * modifier);
          } catch (CANTimeoutException ex) {ex.printStackTrace();}  
        isOn = true;
        System.out.println("Shooter On!");
    }
    public void shooterOff(){
        try{
            firstWheel.setX(OFF);
            secondWheel.setX(OFF);
          } catch (CANTimeoutException ex) {ex.printStackTrace();} 
        isOn = false;
        System.out.println("Shooter Off!");
    }
    public boolean isOn(){
        return isOn;
    }    
    public void manualAim(double input){
        try {
            tilt.setX(input);   
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
    }    
    public void adjustSpeed(double throttle){
            try{
            firstWheel.setX(FORWARD * throttle);
            secondWheel.setX(FORWARD * throttle);
          } catch (CANTimeoutException ex) {ex.printStackTrace();} 
        
    }
}

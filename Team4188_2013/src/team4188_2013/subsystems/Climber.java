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
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *@author Tobore Tasker
 */
public class Climber extends Subsystem {
    private static final double PNEUMATIC_DELAY_SECONDS = 0.1;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    DigitalInput climbIn = RobotMap.climberClimbIn;
    DoubleSolenoid climbSol = RobotMap.climberClimbSol;
    Compressor compressor = RobotMap.climberCompressor;
    DigitalInput climbOut = RobotMap.climberClimbOut;
    DigitalInput hitLeft = RobotMap.climberhitLeft;
    DigitalInput hitRight = RobotMap.climberhitRight;
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
        compressor.start();
        doNothing();
    }
    public void extendClimber(){
        climbSol.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        climbSol.set(DoubleSolenoid.Value.kOff);
    }
    public void retractClimber(){
        climbSol.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        climbSol.set(DoubleSolenoid.Value.kOff);
    }
    public boolean isExtended(){
        return climbOut.get();
    }
    public boolean isRetracted(){
        return climbIn.get();
    }
    public boolean hitLeft(){
        return hitLeft.get();
    }
    public boolean hitRight(){
        return hitRight.get();
    }
    public void doNothing(){
        climbSol.set(DoubleSolenoid.Value.kOff);
    }
    /*
    public void extendSmall(){
        smallSol.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        smallSol.set(DoubleSolenoid.Value.kOff);
    }
    public void retractSmall(){
        smallSol.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        smallSol.set(DoubleSolenoid.Value.kOff);
    }
    public void sliderForward(){
        sliderSol.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        sliderSol.set(DoubleSolenoid.Value.kOff);
    }
    public void sliderRetract(){
        sliderSol.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        sliderSol.set(DoubleSolenoid.Value.kOff);
    }    
    public void extendTallBottom(){
        tallBottomSol.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottomSol.set(DoubleSolenoid.Value.kOff);        
    }
    public void retractTallBottom(){
        tallBottomSol.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottomSol.set(DoubleSolenoid.Value.kOff);        
    }   
    public void extendTallTop(){
        tallBottomSol.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottomSol.set(DoubleSolenoid.Value.kOff);        
    } 
    public void retractTallTop(){
        tallBottomSol.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottomSol.set(DoubleSolenoid.Value.kOff);        
    }       
    public void allDoNothing(){
        smallSol.set(DoubleSolenoid.Value.kOff);
        tallBottomSol.set(DoubleSolenoid.Value.kOff);
        tallTopSol.set(DoubleSolenoid.Value.kOff);
        sliderSol.set(DoubleSolenoid.Value.kOff);
    }
    public void smallDoNothing(){
        smallSol.set(DoubleSolenoid.Value.kOff);
    }
    public void tallBottomDoNothing(){
        tallBottomSol.set(DoubleSolenoid.Value.kOff);
    }
    public void tallTopDoNothing(){
        tallTopSol.set(DoubleSolenoid.Value.kOff);
    }
    public void sliderDoNothing(){
        sliderSol.set(DoubleSolenoid.Value.kOff);
    }
    public boolean getPressureSwitch() {
        return compressor.getPressureSwitchValue();
    }*/
    
    public boolean getEnabled() {
        return compressor.enabled();
    }    
}

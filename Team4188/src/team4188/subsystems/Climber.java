/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import team4188.RobotMap;

/**
 *
 * @author toboretasker
 */
public class Climber extends Subsystem {
    private Compressor compressor;
    private DoubleSolenoid 
            small,
            tallBottom,
            tallTop,
            slider;
    private static final double PNEUMATIC_DELAY_SECONDS = 0.1;
    public void init(){
        System.out.println("Climber Initializing...");
        compressor = new Compressor(RobotMap.PRESSURE_SWITCH,RobotMap.COMPRESSOR_RELAY);
        compressor.start();     
        small = new DoubleSolenoid(RobotMap.SMALL_FORWARD, RobotMap.SMALL_REVERSE);
        tallBottom = new DoubleSolenoid(RobotMap.TALL_BOTTOM_FORWARD, RobotMap.TALL_BOTTOM_REVERSE);
        tallTop = new DoubleSolenoid(RobotMap.TALL_TOP_FORWARD, RobotMap.TALL_TOP_REVERSE);
        slider = new DoubleSolenoid(RobotMap.SLIDER_FORWARD, RobotMap.SLIDER_REVERSE);
        System.out.println("Climber Initialized!");
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void extendSmall(){
        small.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        small.set(DoubleSolenoid.Value.kOff);
    }
    public void retractSmall(){
        small.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        small.set(DoubleSolenoid.Value.kOff);
    }
    public void sliderForward(){
        slider.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        slider.set(DoubleSolenoid.Value.kOff);
    }
    public void sliderRetract(){
        slider.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        slider.set(DoubleSolenoid.Value.kOff);
    }    
    public void extendTallBottom(){
        tallBottom.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottom.set(DoubleSolenoid.Value.kOff);        
    }
    public void retractTallBottom(){
        tallBottom.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottom.set(DoubleSolenoid.Value.kOff);        
    }   
    public void extendTallTop(){
        tallBottom.set(DoubleSolenoid.Value.kForward);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottom.set(DoubleSolenoid.Value.kOff);        
    } 
    public void retractTallTop(){
        tallBottom.set(DoubleSolenoid.Value.kReverse);
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        tallBottom.set(DoubleSolenoid.Value.kOff);        
    }       
    public void allDoNothing(){
        small.set(DoubleSolenoid.Value.kOff);
        tallBottom.set(DoubleSolenoid.Value.kOff);
        tallTop.set(DoubleSolenoid.Value.kOff);
        slider.set(DoubleSolenoid.Value.kOff);
    }
    public void smallDoNothing(){
        small.set(DoubleSolenoid.Value.kOff);
    }
    public void tallBottomDoNothing(){
        tallBottom.set(DoubleSolenoid.Value.kOff);
    }
    public void tallTopDoNothing(){
        tallTop.set(DoubleSolenoid.Value.kOff);
    }
    public void sliderDoNothing(){
        slider.set(DoubleSolenoid.Value.kOff);
    }
    public boolean getPressureSwitch() {
        return compressor.getPressureSwitchValue();
    }
    
    public boolean getEnabled() {
        return compressor.enabled();
    }    
}

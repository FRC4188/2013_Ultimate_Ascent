/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;
import team4188.RobotMap;
import team4188.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import team4188.CorpsLog;

/**
 *
 * @author toboretasker
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private CANJaguar firstWheel, secondWheel;
    public boolean isOn = false;
    final static double 
            MAX_VOLTAGE = 12.0,
            FORWARD = 1.0, //maybe negative
            OFF = 0.0;
    private Compressor compressor;
    private DoubleSolenoid flipper;
    //private DigitalInput extended, retracted;
    private static final double PNEUMATIC_DELAY_SECONDS = 0.1;
    public void init(){
        try{
            firstWheel = new CANJaguar(RobotMap.FIRST_WHEEL);
            secondWheel = new CANJaguar(RobotMap.SECOND_WHEEL);
            firstWheel.configMaxOutputVoltage(MAX_VOLTAGE);
            secondWheel.configMaxOutputVoltage(MAX_VOLTAGE);
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
                CorpsLog.log("RampFlipper Status","initializing",false,true);
        compressor = new Compressor(RobotMap.PRESSURE_SWITCH,RobotMap.COMPRESSOR_RELAY);
        compressor.start();
        //solenoid to flip the ramp on solenoid breakout module.
        flipper = new DoubleSolenoid(RobotMap.SHOOTER_PUSH,
                RobotMap.SHOOTER_RETRACT);
        //extended = new DigitalInput(RobotMap.PNEUMATIC_EXTENDED_SENSOR);
        //retracted = new DigitalInput(RobotMap.PNEUMATIC_RETRACTED_SENSOR);
        CorpsLog.log("RampFlipper Status","initialized",false,true);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterOff());
    }
    public void shooterOn(){
        try{
            firstWheel.setX(FORWARD);
            secondWheel.setX(FORWARD);
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
        public void push() {
        flipper.set(DoubleSolenoid.Value.kForward);     // Flip the ramp.
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        flipper.set(DoubleSolenoid.Value.kOff);         // Release power.
    }
    
    public void retract() {
        flipper.set(DoubleSolenoid.Value.kReverse);     // Retract the piston.
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        flipper.set(DoubleSolenoid.Value.kOff);         // Release power.
    }
    
    public void doNothing() {
        flipper.set(DoubleSolenoid.Value.kOff);
    }
    
    public boolean getPressureSwitch() {
        return compressor.getPressureSwitchValue();
    }
    
    public boolean getEnabled() {
        return compressor.enabled();
    }
}

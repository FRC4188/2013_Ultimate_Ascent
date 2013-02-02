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

/**
 *
 * @author toboretasker
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private CANJaguar firstWheel, secondWheel;
    boolean isOn = false;
    final static double 
            MAX_VOLTAGE = 12.0,
            FORWARD = 1.0, //maybe negative
            OFF = 0.0;
    public void init(){
        try{
            firstWheel = new CANJaguar(RobotMap.FIRST_WHEEL);
            secondWheel = new CANJaguar(RobotMap.SECOND_WHEEL);
            firstWheel.configMaxOutputVoltage(MAX_VOLTAGE);
            secondWheel.configMaxOutputVoltage(MAX_VOLTAGE);
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
        

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
}

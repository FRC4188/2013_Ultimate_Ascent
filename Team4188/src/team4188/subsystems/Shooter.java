/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;
import team4188.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author toboretasker
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private Relay firstWheel, secondWheel;
    boolean isOn = false;
    public void init(){
        firstWheel = new Relay(RobotMap.FIRST_WHEEL);
        secondWheel = new Relay(RobotMap.SECOND_WHEEL);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void shooterOn(){
        firstWheel.set(Relay.Value.kForward);
        secondWheel.set(Relay.Value.kForward);
        isOn = true;
        System.out.println("Shooter On!");
    }
    public void shooterOff(){
        firstWheel.set(Relay.Value.kOff);
        secondWheel.set(Relay.Value.kOff);
        isOn = false;
        System.out.println("Shooter Off!");
    }
    public boolean isOn(){
        return isOn;
    }
}

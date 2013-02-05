/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import team4188.RobotMap;
import team4188.commands.TestEncoder;

/**
 *
 * @author toboretasker
 */
public class DigitalEncoder extends Subsystem {
    private DigitalInput input;
    private Counter count;
    
    public void init()
    {
        input = new DigitalInput(RobotMap.lightInputA);
        count = new Counter();
        count.setUpSource(input);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TestEncoder());
    }
    public int getCount(){
        return count.get();
    }
}


package team4188;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team4188.commands.*;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    public CorpsJoystick pilot, copilot;
    public Button aim, shoot;
    public OI(){ 
         pilot = new CorpsJoystick(1,3,11,-1.5,1.5,1,1,-1.5,1.5,1,1,0,0,0,0);
         copilot = new CorpsJoystick(2,3,11,-5,5,2,1,-3,3,4,-1,0,0,0,0);
        //Joystick stick = new Joystick(1);
        Button aim = new JoystickButton(pilot, 1);
        Button pan = new JoystickButton(pilot, 2);
        Button shoot = new JoystickButton(copilot, 1);
        pan.whileHeld(new Manual());
        aim.whileHeld(new Pan());
        shoot.whileHeld(new ShooterOn());
    }
    // Button button = new JoystickButton(stick, buttonNumber);


    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}



package team4188;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team4188.commands.BasicAutonomous;
import team4188.commands.CorpsJoystick;
import team4188.commands.spinDegrees;
import team4188.commands.spinDegreesGyro;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static final int JOYSTICK_PORT=1;
    //private static OI instance = null;
    public CorpsJoystick stick;
    public JoystickButton autonomousButton;
    public JoystickButton aimButton;
    public JoystickButton buttonTester;
    public OI(){
        stick = new CorpsJoystick (1,4,4,RobotMap.xNegDeadZone,RobotMap.xPosDeadZone,1,1.0,RobotMap.yNegDeadZone,RobotMap.yPosDeadZone,
                1,1.0,RobotMap.zNegDeadZone,RobotMap.zPosDeadZone,1,1.0);
        //public CorpsJoystick(int port, int numAxes, int numButtons, double XDZMin,
        //    double XDZMax, int XMult, double XMax, double YDZMin, double YDZMax, int YMult,
        //    double YMax, double twistDZMin, double twistDZMax,
        //    int twistMult, double twistMax)
        autonomousButton = new JoystickButton(stick, 3);
        autonomousButton.whenPressed(new BasicAutonomous());
        aimButton = new JoystickButton(stick, 4);
        aimButton.whenPressed(new spinDegrees(360));
        buttonTester=new JoystickButton(stick,5);
        buttonTester.whenPressed(new spinDegreesGyro(-20));
        
        //trigger=new JoystickButton(stick, Joystick.ButtonType.kTop.value);  
        //trigger.whenPressed(new DriveSquare());   
    }
    public Joystick getJoystick(){
        return stick;
    }
    
    }
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
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

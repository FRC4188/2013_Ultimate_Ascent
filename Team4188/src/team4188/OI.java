
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
    
    public static final int JOYSTICK_PORT=1;
    //private static OI instance = null;
    public CorpsJoystick pilot, copilot;
    //public CorpsJoystick joystick;
    
    public JoystickButton autonomousButton, aimButton, fieldOrient, shoot
            ,resetGyro,fieldOrientOff, launchFrisbee;
    public OI(){
        CorpsLog.log("xNegDZ",RobotMap.xNegDeadZone,true,false); 
        CorpsLog.log("xPosDZ",RobotMap.xPosDeadZone,true,false); 
        CorpsLog.log("yNegDZ",RobotMap.yNegDeadZone,true,false); 
        CorpsLog.log("yPosDZ",RobotMap.yPosDeadZone,true,false); 
        CorpsLog.log("zNegDZ",RobotMap.zNegDeadZone,true,false); 
        CorpsLog.log("zPosDZ",RobotMap.zPosDeadZone,true,false); 
        
        pilot = new CorpsJoystick (1,4,12,RobotMap.xNegDeadZone,RobotMap.xPosDeadZone,RobotMap.xMult,1.0,RobotMap.yNegDeadZone,RobotMap.yPosDeadZone,
                RobotMap.yMult,1.0,RobotMap.zNegDeadZone,RobotMap.zPosDeadZone,RobotMap.twistMult,1.0);
        copilot = new CorpsJoystick(2,3,11,-5,5,2,1,-3,3,4,-1,0,0,0,0);
        //joystick = new CorpsJoystick(2,3,12,0.0,0.0,1,1.0,0.0,0.0,1,1.0,0.0,0.0,1,1.0);
        //public CorpsJoystick(int port, int numAxes, int numButtons, double XDZMin,
        //    double XDZMax, int XMult, double XMax, double YDZMin, double YDZMax, int YMult,
        //    double YMax, double twistDZMin, double twistDZMax,
        //    int twistMult, double twistMax)
        launchFrisbee = new JoystickButton(pilot, 1);
        aimButton = new JoystickButton(pilot, 3);
        resetGyro = new JoystickButton(pilot, 4);
        shoot = new JoystickButton(pilot, 9);
        fieldOrientOff=new JoystickButton(pilot,11);
        fieldOrient =new JoystickButton(pilot,12);
        //aimButton.whenPressed(new AutoAim());
        resetGyro.whenPressed( new ResetGyro());
        fieldOrient.whenPressed(new FieldOrient());
        fieldOrientOff.whenPressed(new FieldOrientOff());
        //launchFrisbee.whenPressed(new FrisbeeLaunch());
        //shoot.whileHeld(new ShooterOn());
        
        
        //trigger=new JoystickButton(stick, Joystick.ButtonType.kTop.value);  
        //trigger.whenPressed(new DriveSquare());   
    }
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
/*    public CorpsJoystick pilot, copilot;
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
    }*/
    
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


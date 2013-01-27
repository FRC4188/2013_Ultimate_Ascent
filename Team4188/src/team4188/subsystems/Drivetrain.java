/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;
import team4188.CorpsServo;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.RobotMap;
import team4188.commands.*;

/**
 *
 * @author toboretasker
 */


public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public CorpsServo panY , panX ;
    double position = 90.0;
    final static double 
            panXMin = 0,
            panXMax = 58,
            panYMin = 0,
            panYMax = 1,
            TOLERANCE = 5.0,
            //BoardDistance = 60.0, //60.0 inches 
            X_RANGE = 116;
 
    
    public void init(){
        panX = new CorpsServo(panXMin, panXMax, RobotMap.panX);
        panY = new CorpsServo(panYMin, panYMax, RobotMap.panY);    
        panX.goToAngle(position);
        
    }
    public void manualControl(double input, int x){
        if(x == 1)panX.goToPosition(input);
        if(x==2)panY.moveToPosition(input);
      
        setPosition(panX.getAngle());
       // System.out.println(panX.getAngle());
    }
    public void setPosition(double angle){
        position = angle;
        //anX.setAngle(angle);
    }
    public double getPosition(){
        return position;
    }
    
    public void initDefaultCommand() {
        //setDefaultCommand(new Manual());
    }
}

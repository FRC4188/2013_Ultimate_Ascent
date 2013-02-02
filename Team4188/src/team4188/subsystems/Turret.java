/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.RobotMap;

/**
 *
 * @author toboretasker
 */
public class Turret extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private CANJaguar tilt;
    public static double 
            MAX_VOLTAGE = 12.0,
            P = 0.035,
            I = 0.0001,          //adjust
            D = 0,
            PID_TIME = .05,
            TOLERANCE = 0;
    private PIDController tiltPID; 
    Encoder tiltPosition;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void init(){
        try{
            tilt = new CANJaguar(RobotMap.TILT);
            tilt.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            tilt.configMaxOutputVoltage(MAX_VOLTAGE);
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
        tiltPID = new PIDController(P,I,D,tiltPosition,tilt,PID_TIME);
        tiltPID.setInputRange(0.0, 90.0);
        tiltPID.setOutputRange(-1.0, 1.0);
        tiltPID.setTolerance(TOLERANCE);
        
    }
    public void autoTilt(double angle){
        if(!tiltPID.isEnable())tiltPID.enable();
        tiltPID.setSetpoint(angle);
        if(tiltPID.getError() < TOLERANCE)disablePID();
    }
    
    public void manualAim(double input){
        try {
            tilt.setX(input);   
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
    }
    private void enablePID() {
        tiltPID.enable();
    }
    
    public void disablePID() {
        tiltPID.disable();
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author toboretasker
 */
public class CorpsServo extends Servo{
   
    double low, high, range;
    
    public CorpsServo(double min, double max, int port)
    {

        super(port);
        high = max;
        low = min;
        range = high - low;
    }
    public CorpsServo(double min, double max, int port, int mid)
    {
        super(port);
        high = max;
        low = min;
        range = high - low;
    }
    public void goToAngle(double input){
        double adjust; 
        adjust = range/180;
        super.setAngle(input*adjust + low);
    }
    public void goToPosition(double input){
        double adjustRange, high, low; 
        adjustRange = (range - (range/2));
        if(input > 0){
            super.setAngle(adjustRange * input + adjustRange);
        }
        if(input < 0){
            super.setAngle(adjustRange * input + adjustRange);
        }
        if(input == 0){
            
            super.setAngle(adjustRange);
        }
            
    }
    public double getRange()
    {
        return range;
    }
}

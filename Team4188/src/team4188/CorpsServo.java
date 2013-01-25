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
   
    double low, high, range, position;
    
    public CorpsServo(double min, double max, int port)
    {

        super(port);
        high = max;
        low = min;
        range = Math.abs(high - low);
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
        double adjustRange; 
        adjustRange = (range - (range/2));
        
       if(input != 0){
        super.setAngle(adjustRange * input + adjustRange);
        
        position = adjustRange * input + adjustRange;
       }
       else{
            
        super.setAngle(adjustRange);
        position = adjustRange;
        
        }
            
    }
    public void moveToPosition(double input){
        double  pos, in;
        
        if(input != 0){
            pos = getPosition();
            in = pos + (input*2);
            super.setAngle(in);
            setPosition(in);
        }
       
 
    }
    public double getPosition(){
        return position;
    }
    public double getAngle(){
        double angle = 0;
        angle = getPosition() * (180/range);
        return angle;
    }
    public double getRange()
    {
        return range;
    }
}

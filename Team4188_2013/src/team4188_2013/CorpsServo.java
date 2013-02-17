/*CorpsServo
 * Designed to extend the capabilities of servos
 * to use acutally degrees, and joystick input
 * unfinished.
 */
package team4188_2013;
import edu.wpi.first.wpilibj.Servo;


/**
 *
 * @author Tobore Tasker
 */



public class CorpsServo extends Servo{
   
    double low, high, range, position;
    
 /*Create instance of CorpsServo with center in between min & max
 * @param min Minimum value of servo
 * @param max Maximum value of servo
 * @param port port number of servo
 * 
 */
    public CorpsServo(double min, double max, int port)
    {

        super(port);
        high = max;
        low = min;
        range = Math.abs(high - low);
    }
/*Create instance of CorpsServo with center at specified value
 * @param min Minimum value of servo
 * @param max Maximum value of servo
 * @param port port number of servo
 * 
 */
    public CorpsServo(double min, double max, int port, int mid)
    {
        
        super(port);
        high = max;
        low = min;
        range = high - low;
    }
    /*Go to actual angle value, between 0 and 180 degrees
     * @param input angle 0 - 180
     */
    public void goToAngle(double input){
        double adjust; 
        adjust = range/180;
        super.setAngle(input*adjust + low);
    }
    /*Move based on a joystick input 0 to 180 degrees
     * &param input -1 to 1
     * 
     */
    public void goToPosition(double input){
        double adjustRange; 
        adjustRange = (range - (range/2));
        
       if(input != 0){
        setAngle(adjustRange * input + adjustRange);
        
        position = adjustRange * input + adjustRange;
       }
       else{
            
        super.setAngle(adjustRange);
        position = adjustRange;
        
        }
            
    }
    public void moveToPosition(double input){
        double adjustRange;
        adjustRange = (range - (range/2));
     
        if(input != 0){
            set(adjustRange * input + adjustRange);
        }
        else{
            set(adjustRange);
        }
 
    }
    /*Get position of servo in degrees
     */
    public double getPosition(){
        return position;
    }
    /*Convert servo position to degrees
     */
    public double getAngle(){
        double angle = 0;
        angle = getPosition() * (180/range);
        return angle;
    }
    //returns the range of the servo
    public double getRange()
    {
        return range;
    }
}

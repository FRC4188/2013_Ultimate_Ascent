package team4188;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int 
            //camera servos
            panX = 1, 
            panY = 2,
            //Spike Relays

            //CAN Jaguars
            TILT = 15,
            FIRST_WHEEL = 16, 
            SECOND_WHEEL = 17;
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}

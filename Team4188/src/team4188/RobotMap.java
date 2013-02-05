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
            COMPRESSOR_RELAY =1,
            //CAN Jaguars
            TILT = 15,
            FIRST_WHEEL = 16, 
            SECOND_WHEEL = 17,
            //digital I/O
            PRESSURE_SWITCH = 1,
            lightInputA = 2,
            lightInputB = 3, 
            //Solenoids
            SMALL_FORWARD = 1,
            SMALL_REVERSE = 2,
            TALL_BOTTOM_FORWARD = 3,
            TALL_BOTTOM_REVERSE = 4,
            TALL_TOP_FORWARD = 5,
            TALL_TOP_REVERSE = 6,
            SLIDER_FORWARD = 7,
            SLIDER_REVERSE = 8;
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}

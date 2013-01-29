package team4188;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int
        FRONT_LEFT_DRIVE_MOTOR = 11,
        FRONT_RIGHT_DRIVE_MOTOR = 12,
        BACK_LEFT_DRIVE_MOTOR=13,
        BACK_RIGHT_DRIVE_MOTOR=14,
        GYRO=1;
    public static final double aS=0.25;
    public static final double sS=0.5;
    public static final double turnSpeed=110;
    public static boolean onTarget=false;
    public static final double
            xNegDeadZone=-0.3,
            xPosDeadZone=0.3,
            yNegDeadZone=-.1,
            yPosDeadZone=.1,
            zNegDeadZone=-.1,
            zPosDeadZone=.1;
    public static boolean fOM=false;
         
            
}
package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int
        
        LEFT_DRIVE_MOTOR = 11,
        RIGHT_DRIVE_MOTOR = 12;
        private static final double NUM_CLICKS = 360, //distance per pulse = 1.23 mm //.0254
            GEAR_RATIO = 12.0/26.0, //PAN_VOLTAGE = 3.5/12.0, BALANCE_VOLTAGE = 2.0/12.0,
            MAX_VOLTAGE = 12.0;
}

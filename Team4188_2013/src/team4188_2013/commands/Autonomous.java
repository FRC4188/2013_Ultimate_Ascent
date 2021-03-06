// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.


package team4188_2013.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;
import team4188_2013.Robot;

/**
 *@author Tobore Tasker
 */
//setpoint 112.5, pot 104 back left`
// set 136, pot 128 front left
//set 122, pot 134 middle
public class Autonomous extends CommandGroup {
    double adjustment = 0;
    public  Autonomous() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
       // System.out.println("IsCompressorEnabled?" + Robot.climber.isCompressorEnabled());
        
//
        //new ShooterOn().start();
        //Robot.shooter.rampUp();
       // addSequential(new ShooterOn());
        //Robot.shooter.isOn = false;
        //addParallel(new AutoShooterOn());
       // System.out.println("Auto shooter on subsystem");
        //Robot.shooter.shooterOn(1.0);
        addSequential(new RampUp());
        addSequential(new EnableCompressor());
        addSequential(new Wait(2.0));
      //  addSequential(new QuarterSpeed());
        addSequential(new Wait(2.0));
        //addSequential(new HalfSpeed());
        addSequential(new Wait(1.0));
        //addSequential(new TurnOnWheels());
        adjustment = Robot.shooter.getTiltValue();
        Robot.shooter.setCalibration(adjustment);
        //addSequential(new TiltYDegrees(Robot.shooter.getBackLeft()));
        addSequential(new TiltYDegrees(Robot.shooter.getMiddle()));
       // addSequential(new ShooterOn());
        addSequential(new Wait(3.0));
        addSequential(new DisableCompressor());
        addSequential(new PushFrisbee());
        addSequential(new Wait(1.0));
        addSequential(new PushFrisbee());
        addSequential(new Wait(1.0));
        addSequential(new PushFrisbee());
        addSequential(new Wait(1.0));
        addSequential(new PushFrisbee());
        addSequential(new Wait(1.0));
        addSequential(new PushFrisbee());
        addSequential(new Wait(1.0));
        //addSequential(new ShooterOff()); //comment out
        addSequential(new TiltYDegrees(0));
        addSequential(new EnableCompressor());
       // System.out.println("IsCompressorEnabled?" + Robot.climber.isCompressorEnabled());
        //addSequential(Robot.climber.enableCompressor());
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
       
    }
}

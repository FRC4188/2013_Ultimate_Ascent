/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Alexander Nordin
 */
public class BasicAutonomous extends CommandGroup {
    
    public BasicAutonomous() {
        //addSequential(new driveStraight(2.0));
        //addSequential(new MoveLeft(2.0));
        //addSequential(new DriveBack(2.0));
        //addSequential(new MoveRight(2.0));
        //addSequential(new spinDegreesGyro(45));
        //addSequential(new Stop(0.1));
        
        
        //addSequential(new DriveBack(1.0));
        //addSequential(new Stop(0.1));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import team4188.RobotMap;

/**
 * @author Erin
 */
public class AutoAim extends CommandBase {
    private boolean aimed, aimedTilt, gyroReset,
            aimedPanIncrement, aimedPan,
            triedToFindTarget, triedTiltAngle;
    private int panIncrement;
    private static final int NUM_PAN_ITERATIONS = 3;
    private double v0;
    private String status;
    // distance in meters to specified positions on the court:
    private static final double HALF_COURT = 8.2296; //KEY = 3.6576;
    private double distanceToTopTarget, targetAngle, panTarget;
    private ParticleAnalysisReport target;
    private double angleDistance;
    public AutoAim(double angle) {
        //requires(turret);
        requires(drivetrain);
        angleDistance=angle;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        aimed = false;
        aimedTilt = false;
        aimedPanIncrement = false;
        aimedPan = false;
        gyroReset = false;
        triedToFindTarget = false;
        triedTiltAngle = false;
        panIncrement = 0;
        status = "aiming";
        distanceToTopTarget = 0.0;
        targetAngle = 0.0;
        panTarget = 0.0;
        target = null;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(distanceToTopTarget == 0.0) getTargetDistance();
        else {
            /*if(targetAngle == 0.0 && !triedTiltAngle) {
                targetAngle = vision.calculateTiltTargetAngle(distanceToTopTarget, v0);
                triedTiltAngle = true;
            } else if(targetAngle == 0.0 && triedTiltAngle) {
                status = "Target Out of Range";
                aimed = true;       // So the scheduler can escape the command
            } else*/
                aimTiltPan();
            //aimed = turret.autoAimTilt(45.0);       // TESTING
        }
        //CorpsLog.log("Aim Status",status,true,true);
    }
    
    private void getTargetDistance() {
        if(target == null && !triedToFindTarget) {
            //target = vision.getTopTarget();
            triedToFindTarget = true;
        }
        else if(target == null && triedToFindTarget) {
            status = "No targets found";
            aimed = true;       // So the scheduler can escape the command
        }
        else {
            //vision.displayTargets();
            //if(distanceToTopTarget == 0.0)
             //   distanceToTopTarget = vision.getDistanceToTarget(target);
            setSpeed(distanceToTopTarget);
        }
    }
    
    private void setSpeed(double R) {
        double w = (R+7.5548)/0.5864;
        //RobotMap.setShooterSpeed(w);
        //if(distanceToTopTarget<HALF_COURT) {
        //    v0 = 17;                                         // ESTIMATE
        //    RobotMap.setShooterSpeed(Shooter.SpeedMode.kLow);
        //} else {
        //    v0 = 25;                                         // CALIBRATE THIS
        //    RobotMap.setShooterSpeed(Shooter.SpeedMode.kHigh);
        //}
    }
    
    private void aimTiltPan() {
        //if(!aimedTilt)
        //    aimedTilt = turret.autoAimTilt(targetAngle);

        if(!aimedPan && panTarget == 0.0){
            //panTarget = vision.calculatePanTargetAngle(target,distanceToTopTarget,null);//vision.getLeftRight());
            
        }
            //else if(!aimedPan && !aimedPanIncrement && panTarget != 0.0) {
        else if(!aimedPan) {
            /*if(!gyroReset) {
                drivetrain.resetGyro();
                gyroReset = true;
            }*/
            aimedPan = drivetrain.autoAimPan(panTarget);    //change to aimedPanIncrement...
        } //else drivetrain.disablePID();
        //else if(aimedPanIncrement && panIncrement < NUM_PAN_ITERATIONS) {
        //    panIncrement++;
        //    panTarget = 0.0;
        //    aimedPanIncrement = false;
        //    gyroReset = false;
        //}
        //else if(panIncrement >= NUM_PAN_ITERATIONS) aimedPan = true;

        if(/*aimedTilt &&*/ aimedPan) {
            aimed = true;
            status = "TARGET SIGHTED";
        }
        //else if (aimedTilt) status = "Tilt Aimed!";
        //else if (aimedPan) status = "Pan Aimed!";
        else
            status = "still aiming";
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return aimed;
    }

    // Called once after isFinished returns true
    protected void end() {
        //if(targetAngle != 0.0) RobotMap.setCurrentAngle(targetAngle);
        //else RobotMap.setCurrentAngle(turret.getAbsoluteAngle());
        //turret.disablePID();
        drivetrain.disablePID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        //if(targetAngle != 0.0) RobotMap.setCurrentAngle(targetAngle);
        //else RobotMap.setCurrentAngle(turret.getAbsoluteAngle());
        //turret.disablePID();
        drivetrain.disablePID();
    }
}

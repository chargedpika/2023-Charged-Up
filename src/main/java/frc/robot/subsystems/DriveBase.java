package frc.robot.subsystems;

import com.frc7153.swervedrive.SwerveBase;
import com.frc7153.swervedrive.wheeltypes.SwerveWheel_FN;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SwerveConstants;
import frc.robot.peripherals.IMU;
import frc.robot.subsystems.AutoCenter;

/**
 * For controlling the swerve drive base
 */
public class DriveBase extends SubsystemBase {
    // Drive Base
    private SwerveWheel_FN fl = new SwerveWheel_FN(9, 5, 13, SwerveConstants.kWHEEL_DISTANCE.getX(), -SwerveConstants.kWHEEL_DISTANCE.getY(), SwerveConstants.kFL_OFFSET);
    private SwerveWheel_FN fr = new SwerveWheel_FN(10, 6, 14, -SwerveConstants.kWHEEL_DISTANCE.getX(), -SwerveConstants.kWHEEL_DISTANCE.getY() , SwerveConstants.kFR_OFFSET);
    private SwerveWheel_FN rl = new SwerveWheel_FN(7, 3, 11, SwerveConstants.kWHEEL_DISTANCE.getX(), SwerveConstants.kWHEEL_DISTANCE.getY(), SwerveConstants.kRL_OFFSET);
    private SwerveWheel_FN rr = new SwerveWheel_FN(8, 4, 12, -SwerveConstants.kWHEEL_DISTANCE.getX(), SwerveConstants.kWHEEL_DISTANCE.getY(), SwerveConstants.kRR_OFFSET);

    private SwerveBase base = new SwerveBase(fl, fr, rl, rr);
    public IMU imu = new IMU();
    //public HolonomicDriveController holonomicDrive = new HolonomicDriveController(null, null, null);

    // Reset odometry on boot
    public DriveBase() {
        setPose(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)));
        base.setMaxSpeed(2.0, 360.0);
    }

    // Get Odometry Position
    public Pose2d getPose() { return base.getOdometryPose(); }

    // Reset Odometry Position
    public void setPose(Pose2d origin) {
        base.startOdometry(imu.getYaw(), origin.getX(), origin.getY(), origin.getRotation().getDegrees());
        //imu.resetPose(origin);
    }

    // Update odometry
    @Override
    public void periodic() {
        base.updateOdometry(imu.getYaw());
    }
    


    // Drive
    public void stop() { base.stop(true); }
    public void driveFieldOriented(double x, double y, double rot) { base.driveFieldOriented(y, x, rot, imu.getYaw()); }
    public void driveRobotOriented(double x, double y, double rot) { base.drive(y, x, rot); }
    //public void driveTankAbsolute(double lSpeed, double rSpeed) { base.tankDriveAbsolute(lSpeed, rSpeed);}
    public void setCoast(boolean coast) { base.toggleCoastMode(coast, true); }
}

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI.Controller0;
import frc.robot.OI.Controller1;
import frc.robot.commandgroups.TestCommand;
import frc.robot.commands.GrabToggleCommand;
import frc.robot.commands.TeleopArmCommand;
import frc.robot.commands.PresetArmCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.peripherals.ArmPI;
import frc.robot.peripherals.ShuffleboardManager;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.DriveBase;

public class RobotContainer {
    // Peripherals
    private final ArmPI armPi = new ArmPI();

    // Subsystems
    private final DriveBase driveBase = new DriveBase();
    private final Arm arm = new Arm();
    private final Claw claw = new Claw();

    // Shuffleboard
    private final ShuffleboardManager shuffleboard;

    // Constructor
    public RobotContainer() {
        // Create command bindings
        configureBindings();

        // Start Shuffleboard
        shuffleboard = new ShuffleboardManager(this, armPi, driveBase.imu, arm, claw);
    }

    // Configure Command Bindings
    private void configureBindings() {
        // Teleop Drive Command
        driveBase.setDefaultCommand(new TeleopDriveCommand(
            driveBase,
            Controller0::getLeftX,
            Controller0::getLeftY,
            Controller0::getRightX
        ));

        // Teleop Arm Command (position setpoint)
        arm.setDefaultCommand(new TeleopArmCommand(arm, Controller1::getY, Controller1::getThrottle, 60.0));

        // Default Claw Command (open position)
        claw.setDefaultCommand(new GrabToggleCommand(claw, Controller0::getRightTrigger));

        // Arm Preset Positions
        Controller1.button7.whileTrue(new PresetArmCommand(arm, new Translation2d(64.0, 57.69))); // Cone high
        Controller1.button8.whileTrue(new PresetArmCommand(arm, new Translation2d(64.0, 47.39))); // Cube high
        Controller1.button9.whileTrue(new PresetArmCommand(arm, new Translation2d(45.41, 41.97))); // Cone mid
        Controller1.button10.whileTrue(new PresetArmCommand(arm, new Translation2d(43.67, 32.31))); // Cube mid
        Controller1.button11.whileTrue(new PresetArmCommand(arm, new Translation2d(35.98, 4.34))); // Ground
        Controller1.button12.whileTrue(new PresetArmCommand(arm, new Translation2d(35.98, 4.34))); // Ground

        // Stow Position (arm 34 degrees, claw stowed)
        /*Controller1.button2.whileTrue(new ParallelCommandGroup(
            new PresetArmCommand(arm, 34.0, 0.0),
            new GrabCommand(claw, GrabPos.STOW)
        ));*/
    }

    // Toggle Brakes (in drive and claw)
    public void toggleBrakes(boolean brake) {
        driveBase.setCoast(!brake);
        claw.setCoastMode(!brake);
        arm.setBrake(brake);
    }

    // Run Shuffleboard (even when disabled)
    public void shuffleboardUpdate() { shuffleboard.periodic(); }

    // Get Auto Command
    public Command getAutonomousCommand() {
        return null;
    }

    // Get Testing Command
    public Command getTestingCommand() {
        return new TestCommand(arm, shuffleboard, Controller1::getThrottle);
    }
}

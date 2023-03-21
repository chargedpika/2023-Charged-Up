// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

/**
 * Command to drive swerve drive base in teleop
 */
public class TeleopDriveCommand extends CommandBase {
  // Subsystems
  private DriveBase base;

  // Suppliers
  private Supplier<Double> xSupply;
  private Supplier<Double> ySupply;
  private Supplier<Double> rSupply;
  private Supplier<Boolean> turboSupply;

  public TeleopDriveCommand(DriveBase swerveSubsystem, Supplier<Double> xSupplier, Supplier<Double> ySupplier, Supplier<Double> rotSupplier, Supplier<Boolean> turboSupplier) {
    base = swerveSubsystem;
    xSupply = xSupplier;
    ySupply = ySupplier;
    rSupply = rotSupplier;
    turboSupply = turboSupplier;

    addRequirements(base);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    base.stop();

    base.setMaxSpeed(5.0, 540.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Turbo (fast) mode
    if (turboSupply.get()) {
      base.setMaxSpeed(5.5, 540.0);
    } else {
      base.setMaxSpeed(5.0, 540.0);
    }

    base.driveRobotOriented(xSupply.get(), ySupply.get(), -rSupply.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    base.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

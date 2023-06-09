

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.GrabPositions;
import frc.robot.subsystems.Claw;


public class GrabCommand extends CommandBase {
    private Claw claw;
    private double lRots;
    private double rRots;

    /** Creates a new GrabCommand. */
    public GrabCommand(Claw clawSubsys, double lPos, double rPos) {
        // Use addRequirements() here to declare subsystem dependencies.
        claw = clawSubsys;
        lRots = lPos;
        rRots = rPos;

        addRequirements(claw);
    }

    public GrabCommand(Claw clawSubsys, GrabPositions pos) { this(clawSubsys, pos.lPos, pos.rPos); }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        claw.setPosition(lRots, rRots);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
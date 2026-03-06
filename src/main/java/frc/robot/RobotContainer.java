// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_DriveTrain = new DriveTrain();
  private final XboxController m_XboxController = new XboxController(0);
  private final VisionSubsystem m_VisionSubsystem = new VisionSubsystem();
  boolean needtoturn = false;
  double offTurn = 0;
  // Replace with CommandPS4Controller or CommandJoystick if needed

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.

  }

  public void teleopPeriodic() {
    SmartDashboard.putNumber("Pigeon Yaw", m_DriveTrain.getHeading());
    m_DriveTrain.periodic();
    if (m_XboxController.getAButton()) {
      m_DriveTrain.trackball(m_VisionSubsystem.getTargetX(), true);
    } else if (m_XboxController.getXButton()) {
      if (m_VisionSubsystem.getTargetArea() < 1.2) {
        m_DriveTrain.setSpeed(-0.4, -0.4);
      } else {
        m_DriveTrain.setSpeed(0, 0);
      }
    } else if (m_XboxController.getYButton()) {
      double turn = m_DriveTrain.trackball(m_VisionSubsystem.getTargetX(), false);
      double leftAdd = 0;
      double rightAdd = 0;
      if (m_VisionSubsystem.targets()) {
        if (m_VisionSubsystem.getTargetArea() < 12) {
          leftAdd = -0.5;
          rightAdd = -0.5;
        }

      }
      leftAdd = leftAdd + turn;
      rightAdd = rightAdd - turn;
      m_DriveTrain.setSpeed(leftAdd, rightAdd);
    } else {
      m_DriveTrain.setSpeed(0, 0);
      m_DriveTrain.setSpeed(m_XboxController.getLeftY(), m_XboxController.getRightY());
    }
    if (m_XboxController.getRightBumper()) {
      m_DriveTrain.setSpeed(m_XboxController.getLeftY(), m_XboxController.getLeftY());
    }

  }

  public void autonomousInit() {
    m_DriveTrain.zeroGyro();
  }

  public void runAutonomous() {
    double targetAngle = 90.0;

    if (!m_DriveTrain.atAngle(targetAngle)) {
      m_DriveTrain.turnToAngle(targetAngle);
    } else {
      m_DriveTrain.stopMotors();
    }
  }

  public void TrackTheBall() {
    double turn = m_DriveTrain.trackball(m_VisionSubsystem.getTargetX(), false);
    double leftAdd = 0;
    double rightAdd = 0;
    if (!needtoturn) {
      if (m_VisionSubsystem.targets()) {
        if (m_VisionSubsystem.getTargetArea() < 6) {
          leftAdd = -0.5;
          rightAdd = -0.5;
        } else {
          needtoturn = true;
          offTurn += 90;
          turn = 0;
        }
      } else {
        needtoturn = true;
        turn = 0;
        leftAdd = 0.15;
        rightAdd = -0.15;
      }
      leftAdd = leftAdd + turn;
      rightAdd = rightAdd - turn;
      m_DriveTrain.setSpeed(leftAdd, rightAdd);
    } else if (m_VisionSubsystem.targets() && m_DriveTrain.atAngle(offTurn)) {
      needtoturn = false;
    } else {
      leftAdd = 0.2;
      rightAdd = -0.2;
    }

  }

}

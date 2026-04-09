// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.sensors.PigeonIMU.GeneralStatus;

public class DriveTrain extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  PIDController angleController = new PIDController(0.014, 0, 0.0001);
  double targetDistance = 1.0;
  GeneralStatus generalStatus = new GeneralStatus();
  private final AnalogInput frontSound = new AnalogInput(4);
  private final AnalogInput rightSound = new AnalogInput(5);
  private final AnalogInput leftSound = new AnalogInput(6);
  private final AnalogInput rearSound = new AnalogInput(7);

  public double getDistance(AnalogInput sensor) {
    double volts = sensor.getAverageVoltage();
    double distanceMM = 0;
    distanceMM = (1023.519 * volts + 0.109) / 25.4 / 12;
    return distanceMM;
  }

  public DriveTrain() {
    // pigeon = new PigeonIMU(30);
    SparkMaxConfig config = new SparkMaxConfig();
    config.openLoopRampRate(.23);
    config.idleMode(IdleMode.kBrake);
    Left.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    Right.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // pigeon.setYaw(0);
    Left.getEncoder().setPosition(0);
    Right.getEncoder().setPosition(0);
  }

  SparkFlex Left = new SparkFlex(1, MotorType.kBrushless);
  double TravelL = Left.getEncoder().getPosition() * 0.05;

  SparkFlex Right = new SparkFlex(2, MotorType.kBrushless);
  double TravelR = Right.getEncoder().getPosition() * 0.05;
  double _target = 0;

  public void zeroMotors() {
    Left.getEncoder().setPosition(0);
    Right.getEncoder().setPosition(0);

  }

  public void setSpeed(double leftSpeed, double rightSpeed) {

    if (leftSpeed < .10 && leftSpeed > -.10) {
      leftSpeed = 0;
    }
    Left.set(-leftSpeed);

    if (rightSpeed < .10 && rightSpeed > -.10) {
      rightSpeed = 0;
    }
    Right.set(rightSpeed);

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Front Distance", getDistance(frontSound));
    SmartDashboard.putNumber("Right Distance", getDistance(rightSound));
    SmartDashboard.putNumber("Left Distance", getDistance(leftSound));
    SmartDashboard.putNumber("Rear Distance", getDistance(rearSound));
    if (_target > 0) {
      if (Left.getEncoder().getPosition() >= _target) {
        _target = 0;
        setSpeed(0, 0);
      } else {
        setSpeed(-.25, -.25);

      }
    }

  }

  public void autoDrive(double target) {
    _target = target;
    Left.getEncoder().setPosition(0);
    Right.getEncoder().setPosition(0);

  }

  public double trackball(double x, boolean setSpeed) {
    double out = angleController.calculate(x, 0);
    out = Math.min(1, Math.max(out, -1));
    if (setSpeed) {
      setSpeed(-out, -out);
    }
    SmartDashboard.putNumber("out", out);
    return out;
  }

  public void lineUp(double y) {
    double out = y;
    SmartDashboard.putNumber("distance", out);

  }

  public double frontdis() {
    double dis = getDistance(frontSound);
    return dis;
  }

  // public double getHeading() {
  // double yaw = pigeon.getYaw();

  // if (yaw > 360)
  // yaw -= 360;
  // if (yaw < -360)
  // yaw += 360;
  // return yaw;
  // }

  // public void zeroGyro() {
  // pigeon.setYaw(0);
  // }

  /*
   * public void turnToAngle(double targetAngle) {
   * double currentHeading = getHeading();
   * double error = targetAngle - currentHeading;
   * 
   * double kP = 0.01; // turning strength (you can tune this)
   * double turnSpeed = kP * error;
   * 
   * // limit speed so it doesn't go crazy
   * turnSpeed = Math.max(-0.5, Math.min(0.5, turnSpeed));
   * 
   * // Tank drive: left = +turn, right = -turn
   * Left.set(turnSpeed);
   * Right.set(turnSpeed);
   * }
   */

  /*
   * public boolean atAngle(double targetAngle) {
   * return Math.abs(targetAngle - getHeading()) < 2.0; // within 2 degrees
   * }
   * 
   * public void stopMotors() {
   * Left.set(0);
   * Right.set(0);
   * }
   * 
   * public void driveStraight(double speed, double targetHeading) {
   * double currentHeading = getHeading();
   * double error = targetHeading - currentHeading;
   * 
   * double kP = 0.02; // correction strength (tune later)
   * double correction = kP * error;
   * 
   * // limit correction
   * correction = Math.max(-0.3, Math.min(0.3, correction));
   * 
   * double leftSpeed = speed + correction;
   * double rightSpeed = speed - correction;
   * 
   * Left.set(leftSpeed);
   * Right.set(rightSpeed);
   * }
   */

  public void resetEncoders() {
    Left.getEncoder().setPosition(0);
    Right.getEncoder().setPosition(0);
  }

  public double meters() {
    TravelL = Left.getEncoder().getPosition() * 0.0554;
    TravelR = Right.getEncoder().getPosition() * 0.0554;
    return (TravelL + -TravelR) / 2;

  }

  public double anticrash() {
    double Lspeed = Left.getEncoder().getVelocity();

    double anticrash = 16 * (Lspeed) - .45;

    return anticrash;

  }
}
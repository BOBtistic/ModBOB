// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.sensors.PigeonIMU;


public class DriveTrain extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  PIDController angleController = new PIDController(0.014, 0, 0.0001);
  private PigeonIMU pigeon;
  double targetDistance = 1.0; 
  public DriveTrain() {
    pigeon = new PigeonIMU(30);
    SparkMaxConfig config = new SparkMaxConfig();
    config.idleMode(IdleMode.kBrake);
    Left.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    Right.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    Left.getEncoder().setPosition(0);
    Right.getEncoder().setPosition(0);
  }

  SparkMax Left = new SparkMax(1, MotorType.kBrushless);
  SparkMax Right = new SparkMax(2, MotorType.kBrushless);
  double _target = 0;
  public void setSpeed(double leftSpeed, double rightSpeed){

    if(leftSpeed < .10 && leftSpeed > -.10){
      leftSpeed = 0;
    }
    Left.set(-leftSpeed);

    if(rightSpeed < .10 && rightSpeed > -.10){
      rightSpeed = 0;
    }
    Right.set(rightSpeed);

  }
  @Override
  public void periodic(){
    SmartDashboard.putNumber("Left Motor Rotations", Left.getEncoder().getPosition());
    SmartDashboard.putNumber("Right Motor Rotations", Right.getEncoder().getPosition());
    if(_target > 0){
      if(Left.getEncoder().getPosition() >= _target){
      _target = 0;
      setSpeed(0, 0);
    }else{
      setSpeed(-.25, -.25);

      }
    } 

  }
  public void autoDrive(double target){
    _target = target;
    Left.getEncoder().setPosition(0);
    Right.getEncoder().setPosition(0);

  }
  public double trackball(double x, boolean setSpeed){
    double out = angleController.calculate(x, 0);
    out = Math.min(1, Math.max(out, -1));
    if(setSpeed){setSpeed(out, -out);}
    SmartDashboard.putNumber("out", out);
    return out;
  }
  public void lineUp(double y){
    double out = y;
    SmartDashboard.putNumber("distance", out);

  }
  public double getYaw() {
    double[] ypr = new double[3];
  pigeon.getYawPitchRoll(ypr);
  return ypr[0];    

  }
  public void zeroGyro() {
    pigeon.setYaw(0);
  } 
}
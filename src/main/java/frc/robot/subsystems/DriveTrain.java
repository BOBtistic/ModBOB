// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class DriveTrain extends SubsystemBase {
  //This is the motor define, Reapeat what i did other than if it is a SparkMax then Replace SparkFlex with SparkMax
  SparkFlex Left = new SparkFlex(1, MotorType.kBrushless);
  SparkFlex Right = new SparkFlex(2, MotorType.kBrushless);
  // Ex: SparkMax Intake = new SparkMax(3, MotorType.Kbrushless)
  private final DifferentialDrive JackDrive = new DifferentialDrive(Left, Right);

  public void setJackSpeed(double Speed, double rotate){
    JackDrive.arcadeDrive(Speed, rotate);
  }

  public void setGavinSpeed(double left, double right){
    if (left > 0.2 && left < -0.2){
      left = 0;
    }
    if (right > 0.2 && right < -0.2){
      right = 0;
    }
    Left.set(left);
    Right.set(right);


  }
  //when you want to operate a motor do this
  /*
   * public void intake(double Speed){
   * Intake.set(Speed);
   * }
   */

  

}
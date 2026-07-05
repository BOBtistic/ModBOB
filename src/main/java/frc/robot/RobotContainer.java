// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */


 
public class RobotContainer {

  private final DriveTrain m_DriveTrain = new DriveTrain();
  private final XboxController m_XboxController = new XboxController(0);

  public RobotContainer() {
    
    configureBindings();
  }

  private void configureBindings() {
    
    
  }

  double driver = 1;

  public void teleopPeriodic(){
    
    if(driver % 2 !=0 ){
      m_DriveTrain.setDefaultCommand(
        new RunCommand(
            () -> m_DriveTrain.setJackSpeed(
                -m_XboxController.getLeftY(), // Forward/Backward
                -m_XboxController.getLeftX()  // Turning
            ),
            m_DriveTrain
        )
    );
    }else{
      m_DriveTrain.setGavinSpeed(m_XboxController.getLeftY(), m_XboxController.getRightY());
    }
    if(m_XboxController.getStartButton()){
      driver += 1;
    }
    /* if(m_XboxController.getAbutton()){
      m_DriveTrain.Intake(.25);
    }else{
    m_DriveTrain.Intake(0);
    }
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */






  }



}

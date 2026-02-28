package frc.robot.subsystems;

import javax.naming.spi.DirStateFactory.Result;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.proto.Photon;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;

public class VisionSubsystem extends SubsystemBase {
    PhotonCamera camera = new PhotonCamera("Cam1");

double targetDistance = 1.0; // meters from tag you want to stop at
    
    PhotonPipelineResult result;

    public void periodic(){
        result = camera.getLatestResult();
        if(result.hasTargets()){
            SmartDashboard.putNumber("ballx", result.getBestTarget().getYaw());
        } 

        


    }

    public double getTargetX(){
        if(result.hasTargets()){
            return result.getBestTarget().getYaw();
        }else {
            return 0;
        }
    }
    public double getTargetArea(){
        if(result.hasTargets()){
            return result.getBestTarget().getArea();
        }else {
            return 0;
        }
    }


    
}
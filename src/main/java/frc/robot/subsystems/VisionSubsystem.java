package frc.robot.subsystems;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


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
    public boolean targets(){
        if(result.hasTargets()){
            return true;
        }else{
            return false;
        }


    } 
    
    
}
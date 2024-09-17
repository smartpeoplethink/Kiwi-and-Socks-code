package org.firstinspires.ftc.teamcode;

import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Gamepad;
@Photon
@Disabled
public class driveControl{ //for handling values associated with driving.
    Vector Movement;
    double front = 0;
    double right = 0;
    double left = 0;
    boolean fastDrive = false;
    double rotateValue = 0;
    driveControl(Vector movementFCN){
        Vector Movement = movementFCN;
    }
    private double maximumValue(){
        if (front >= left && front >= right){
            return front;
        }else if(left >= right){
            return left;
        }else{
            return right;
        }
    }
    public void update(){
        //driving and not rotating
        front = Movement.calculateWheel(Vector.FRONT)*Movement.magnitude;
        left = Movement.calculateWheel(Vector.LEFT)*Movement.magnitude;
        right = Movement.calculateWheel(Vector.RIGHT)*Movement.magnitude;
        double max = maximumValue();
        if (fastDrive && max < Movement.magnitude){ //maximizes speed but makes less smooth
            front = front/max*Movement.magnitude;
            right = right/max*Movement.magnitude;
            left = left/max*Movement.magnitude;
        }

        //rotating
        front+=rotateValue;
        right+=rotateValue;
        left+=rotateValue;
        max = maximumValue();
        //scale all proportionally if one is greater than 1
        if (max>1){
            front = front/max;
            left = left/max;
            right = right/max;
        }
    }
}
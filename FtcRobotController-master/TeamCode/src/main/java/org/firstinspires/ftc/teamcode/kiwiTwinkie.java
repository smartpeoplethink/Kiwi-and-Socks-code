package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;
@TeleOp
@Photon
private class Vector{ //for handling values associated with vectors.
    static boolean POLAR = true, CARTESIAN = false;
    static int FRONT = 0, LEFT = 1, RIGHT = 2;
    double angle = 0, magnitude = 0, i = 0, j = 0;
    public void reset(boolean resetPolar){
        //converting from one type to the other.
        // True resets angle or polar, false resets i+j or cartesian
        if (resetPolar){
            magnitude = Math.sqrt( (i*i+j*j));

            if (i<0){//calculate angle baised on arctan however if condition used to specify special conditions like i ==0;
                angle = Math.atan(j/i)+Math.pi/2;
            }else if(i>0){
                angle = Math.atan(j/i);
            }else{
                if(j >= 0){
                    angle = Math.pi/2;
                }else{
                    angle = Math.pi*3/2;
                }
            }
        }else{
            i = magnitude*Math.cos(angle);
            j = magnitude*Math.sin(angle);
        }


    }
    public double calculateWheel(int wheelNum){
        int calulationAngle = ((wheelNum * Math.pi* 2/3)-angle)
        return Math.cos(calulationAngle);
    }
}
public class kiwiTwinkie extends LinearOpMode{
    private DcMotor leftMotor, rightMotor, frontMotor, flyWheel;
    private double powerLeft, powerRight, powerFront;
    private boolean runFlyWheel = false;
    private Vector Movement;
    @Override
    public void runOpMode(){
        flyWheel = hardwareMap.get(DcMotor.class, "FlyWheel");
        rightMotor = hardwareMap.get(DcMotor.class, "LeftMotor");//Wheel Num 1
        leftMotor = hardwareMap.get(DcMotor.class, "RightMotor");//Wheel Num 2
        frontMotor = hardwareMap.get(DcMotor.class, "FrontMotor");//Wheel Num 0

        waitForStart();
        while (opModeIsActive()){
            //rightBumper toggles flywheel
            if (gamepad1.right_bumper) runFlyWheel = !runFlyWheel; //


            //recieve values in a cartesien manner and convert them to polar
            Movement.i = gamepad1.left_joystick_x;
            Movement.j = gamepad1.left_joystick_y;
            Movement.reset(Vector.POLAR);//Change vector cartesian to polar (from i/j to angle/magnitude)

            //add power to move in line
            powerFront = Movement.calculateWheel(Vector.FRONT);
            powerLeft = Movement.calculateWheel(Vector.LEFT);
            powerRight = Movement.calculateWheel(Vector.RIGHT);

            //addpower to rotate
            powerFront+=gamepad1.right_stick_x; //If rotate all wheels move with same speed.
            powerLeft+=gamepad1.right_stick_x;
            powerRight+=gamepad1.right_stick_x;

            // If power is too high scale all down proportioanally.
            // Therefore if you want to drive forward and rotate half of power will go to each
            if (powerFront>1){
                powerFront = 1;
                powerLeft = powerLeft * (1/powerFront);
                powerRight = powerRight * (1/powerFront);
            }
            if (powerLeft>1){
                powerLeft = 1;
                powerFront = powerFront * (1/powerLeft);
                powerRight = powerRight * (1/powerLeft);
            }
            if (powerRight>1){
                powerRight = 1;
                powerFront = powerFront * (1/powerRight);
                powerRight = powerRight * (1/powerRight);
            }


            //apply values
            if (runFlyWheel){
                flyWheel.setPower(-1.0);
            }
            frontMotor.setPower(powerFront);
            rightMotor.setPower(powerRight);
            leftMotor.setPower(powerLeft);

            telemetry.addData("Status","Self-destructing1");
            telemetry.update();
        }
    }
}

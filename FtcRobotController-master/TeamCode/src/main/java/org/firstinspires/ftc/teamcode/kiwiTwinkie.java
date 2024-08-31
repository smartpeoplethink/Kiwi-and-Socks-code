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

@TeleOp
@Photon

public class kiwiTwinkie extends LinearOpMode{
    private DcMotor leftMotor, rightMotor, frontMotor, flyWheel;
    private double powerLeft = 0, powerRight = 0, powerFront = 0;
    private boolean runFlyWheel = false, flyWheelWait = false, flyWheelDecrementWait = false, flyWheelIncrementWait = false;
    private int flyWheelSpeed = 10;
    private Vector Movement = new Vector();

    @Override
    public void runOpMode(){
        flyWheel = hardwareMap.get(DcMotor.class, "FlyWheel");
        rightMotor = hardwareMap.get(DcMotor.class, "LeftMotor");//Wheel Num 1
        leftMotor = hardwareMap.get(DcMotor.class, "RightMotor");//Wheel Num 2
        frontMotor = hardwareMap.get(DcMotor.class, "FrontMotor");//Wheel Num 0

        waitForStart();
        while (opModeIsActive()){
            //rightBumper toggles flywheel
            if (gamepad1.right_trigger>0.5 && !flyWheelWait){
                runFlyWheel = !runFlyWheel;
                flyWheelWait = true;
            }
            if (!(gamepad1.right_trigger>0.5)){
                flyWheelWait = false;
            }
            if (gamepad1.left_bumper && !flyWheelDecrementWait && flyWheelSpeed > 0){
                flyWheelSpeed -= 1;
                flyWheelDecrementWait = true;
            }
            if (!gamepad1.left_bumper){
                flyWheelDecrementWait = false;
            }
            if (gamepad1.right_bumper && !flyWheelIncrementWait && flyWheelSpeed < 10){
                flyWheelSpeed += 1;
                flyWheelIncrementWait = true;
            }
            if (!gamepad1.right_bumper){
                flyWheelIncrementWait = false;
            }


            //recieve values in a cartesien manner and convert them to polar
            Movement.i = gamepad1.left_stick_x;
            Movement.j = gamepad1.left_stick_y;
            Movement.reset(Vector.POLAR);//Change vector cartesian to polar (from i/j to angle/magnitude)

            //add power to move in line
            powerFront = Movement.calculateWheel(Vector.FRONT)*Movement.magnitude;
            powerLeft = Movement.calculateWheel(Vector.LEFT)*Movement.magnitude;
            powerRight = Movement.calculateWheel(Vector.RIGHT)*Movement.magnitude;

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
                flyWheel.setPower((((double)flyWheelSpeed)/10));
            }else{
                flyWheel.setPower(0);
            }
            frontMotor.setPower(powerFront);
            rightMotor.setPower(powerRight);
            leftMotor.setPower(powerLeft);

            telemetry.addData("Status",flyWheelSpeed);
            telemetry.update();
        }
    }
}

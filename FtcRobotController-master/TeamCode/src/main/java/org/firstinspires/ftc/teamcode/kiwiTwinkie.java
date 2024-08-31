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
    @Override
    public void runOpMode(){
        flyWheel = hardwareMap.get(DcMotor.class, "FlyWheel");
        rightMotor = hardwareMap.get(DcMotor.class, "LeftMotor");
        leftMotor = hardwareMap.get(DcMotor.class, "RightMotor");
        frontMotor = hardwareMap.get(DcMotor.class, "FrontMotor");

        waitForStart();
        while (opModeIsActive()){
            flyWheel.setPower(-1.0);
            rightMotor.setPower(0.5);
            leftMotor.setPower(0.5);


            telemetry.addData("Status","7");
            telemetry.update();
        }
    }
}

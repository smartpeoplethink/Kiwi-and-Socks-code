package org.firstinspires.ftc.teamcode;

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
public class testShootKiwi extends LinearOpMode {
    private DcMotor FlyWheel;
    public void runOpMode(){
        FlyWheel = hardwareMap.get(DcMotor.class, "FlyWheel");
        waitForStart();
        while (opModeIsActive()){
            FlyWheel.setPower(-1.0);
            telemetry.addData("Status","7");
            telemetry.update();
        }
    }
}

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

public class kiwiTwinkieBeta extends LinearOpMode{
    private DcMotor leftMotor, rightMotor, frontMotor, flyWheel;
    private double powerLeft = 0, powerRight = 0, powerFront = 0;
    private final toggleButton flyWheelOn = new toggleButton(false);
    private final toggleButton servoReleaseHandler=new toggleButton(false);
    private final toggleButton decrementHandler = new toggleButton(true);
    private final toggleButton incrementHandler = new toggleButton(true);
    private final toggleButton fastDrive = new toggleButton(false);
    private int flyWheelSpeed = 100;
    private static double RELEASE = 0, CATCH = 1;

    private final Vector Movement = new Vector();
    private driveControl motorControl = new driveControl(Movement);

    @Override
    public void runOpMode(){
        flyWheel = hardwareMap.get(DcMotor.class, "FlyWheel");
        rightMotor = hardwareMap.get(DcMotor.class, "LeftMotor");//Wheel Num 1
        leftMotor = hardwareMap.get(DcMotor.class, "RightMotor");//Wheel Num 2
        frontMotor = hardwareMap.get(DcMotor.class, "FrontMotor");//Wheel Num 0
        Servo servo = hardwareMap.get(Servo.class, "BallController");

        waitForStart();
        while (opModeIsActive()){
            //testing servo values:
            if (gamepad1.dpad_right)RELEASE+=0.01;
            if (gamepad1.dpad_left)RELEASE-=0.01;

            //controlling inputs.
            motorControl.fastDrive = fastDrive.evaluate(gamepad1.y);
            if (servoReleaseHandler.evaluate(gamepad1.a)){
                servo.setPosition(RELEASE);
            }else{
                servo.setPosition(CATCH);
            }

            if (flyWheelOn.evaluate(gamepad1.left_trigger>0.5)){
                flyWheel.setPower((((double)flyWheelSpeed)/100));
            }else{
                flyWheel.setPower(0);
            }
            if (decrementHandler.evaluate(gamepad1.dpad_down)){
                flyWheelSpeed-=1;
                if (flyWheelSpeed< 0)flyWheelSpeed = 0;
            }
            if (incrementHandler.evaluate(gamepad1.dpad_up)){
                flyWheelSpeed+=1;
                if (flyWheelSpeed> 100)flyWheelSpeed = 100;
            }




            //managing drivint
            //recieve values in a cartesien manner and convert them to polar
            Movement.i = gamepad1.left_stick_x;
            Movement.j = gamepad1.left_stick_y;
            Movement.reset(Vector.POLAR);//Change vector cartesian to polar (from i/j to angle/magnitude)

            //handles motors
            motorControl.rotateValue = gamepad1.right_stick_x;
            motorControl.update();




            frontMotor.setPower(motorControl.front);
            rightMotor.setPower(motorControl.right);
            leftMotor.setPower(motorControl.left);
            telemetry.addData("Lead coder: ", "Sam");
            telemetry.addData("Lead builder: ", "Jacob");
            telemetry.addData("Lead 3d designer: ", "Gianluca");
            telemetry.addData("Version of Kiwi: ", 90);
            telemetry.addData("Speed: ",flyWheelSpeed);
            telemetry.addData("Servo: ",RELEASE);
            telemetry.addData("Smooth drive: ", !motorControl.fastDrive);

            telemetry.update();
        }
    }
}

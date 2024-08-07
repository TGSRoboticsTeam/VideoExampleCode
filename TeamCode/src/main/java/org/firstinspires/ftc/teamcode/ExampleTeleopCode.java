package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Example Teleop", group = "Test Code")

public class ExampleTeleopCode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBackDrive");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBackDrive");

        DcMotor slides = hardwareMap.get(DcMotor.class, "linearSlide");

        Servo claw = hardwareMap.get(Servo.class, "claw");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        slides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        claw.setPosition(0);

        while(!isStopRequested() && !isStarted()){

        }

        while(!isStopRequested()){
            double max;

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double z = gamepad1.right_stick_x;

            float slideUp = gamepad2.right_trigger;
            float slideDown = gamepad2.left_trigger;

            boolean clawOpen = gamepad2.right_bumper;
            boolean clawClose = gamepad2.left_bumper;

            if(x <= .05 && x >= -.05){
                x = 0;
            }

            if(y <= .05 && y >= -.05){
                y = 0;
            }

            if(z <= .05 && z >= -.05){
                z = 0;
            }

            double rightFrontPower = y - x - z;
            double rightBackPower  = y + x - z;
            double leftBackPower   = y - x + z;
            double leftFrontPower  = y + x + z;

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }

            leftFront.setPower(leftFrontPower);
            leftBack.setPower(leftBackPower);
            rightBack.setPower(rightBackPower);
            rightFront.setPower(rightFrontPower);

            if(slideUp <= .05){
                slideUp = 0;
            }

            if(slideDown <= .05){
                slideDown = 0;
            }

            slides.setPower(slideUp - slideDown);
            
            if(clawOpen){
                claw.setPosition(0);
            }

            if(clawClose){
                claw.setPosition(.22);
            }
        }
    }
}

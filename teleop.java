package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="teleliv2", group="Pushbot")
public class TELEOP extends LinearOpMode {

    /* Declare OpMode members. */
    HardwarePushbot robot           = new HardwarePushbot();   // Use a Pushbot's hardware
    private boolean maskMoveUp = false;
    private boolean maskMoveDown = false;
    final double BUCKET_SPEED = 0.6;

    @Override
    public void runOpMode() {

        //checking teleop distance if encoders convert to inches correctly
        final double COUNTS_PER_MOTOR_REV = 435;    // eg: TETRIX Motor Encoder
        final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
        final double WHEEL_DIAMETER_INCHES = 3.93701;     // For figuring circumference
        final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.1415);

        double left;
        double right;
        double max;
        double speedControl;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.left_bumper){
                speedControl = 0.3;
            }
            else if(gamepad1.right_bumper){
                speedControl = 0.5;
            }
            else{
                speedControl = 1;
            }

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            double y = -gamepad1.right_stick_y; // Remember, this is reversed!
            double x = gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.left_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.leftFront.setPower(frontLeftPower * speedControl);
            robot.leftBack.setPower(backLeftPower * speedControl);
            robot.rightFront.setPower(frontRightPower * speedControl);
            robot.rightBack.setPower(backRightPower * speedControl);


            if (gamepad1.dpad_left)
                robot.carousel.setPower(robot.CAROUSEL_POWER_LEFT);
            else if (gamepad1.dpad_right)
                robot.carousel.setPower(robot.CAROUSEL_POWER_RIGHT);
            else
                robot.carousel.setPower(0.0);

           

            telemetry.addData("LF Encoder", robot.leftFront.getCurrentPosition());
            telemetry.addData("LB Encoder", robot.leftBack.getCurrentPosition());
            telemetry.addData("RF Encoder", robot.rightFront.getCurrentPosition());
            telemetry.addData("RB Encoder", robot.rightBack.getCurrentPosition());
            telemetry.addData("LF Inches", robot.leftFront.getCurrentPosition()/COUNTS_PER_INCH);
            telemetry.addData("LB Inches", robot.leftBack.getCurrentPosition()/COUNTS_PER_INCH);
            telemetry.addData("RF Inches", robot.rightFront.getCurrentPosition()/COUNTS_PER_INCH);
            telemetry.addData("RB Inches", robot.rightBack.getCurrentPosition()/COUNTS_PER_INCH);
            telemetry.addData("power:", robot.intake);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}

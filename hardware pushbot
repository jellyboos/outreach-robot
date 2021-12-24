package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class HardwarePushbot {
    /* Public OpMode members. */

    public DcMotor leftFront = null;
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    public DcMotor lifty = null;
    public DcMotor carousel = null;
    public Servo claw = null;
    BNO055IMU imu = null;

    public static final double MID_SERVO       =  0.6;
    public static final double ARM_POWER = 0.6;
    public static final double CAROUSEL_POWER = 0.9;

    public static final double CLAW_OPEN_POSITION = 120 / 280;
    public static final double CLAW_CLOSE_POSITION = 185.0 / 280;

    // These constants define the desired driving/control characteristics
    // The can/should be tweaked to suite the specific robot drive train



    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwarePushbot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFront = hwMap.get(DcMotor.class, "LF");
        rightFront = hwMap.get(DcMotor.class, "RF");
        leftBack = hwMap.get(DcMotor.class, "LB");
        rightBack = hwMap.get(DcMotor.class, "RB");
        lifty = hwMap.get(DcMotor.class, "lifty");
        carousel = hwMap.get(DcMotor.class, "C");
        imu = hwMap.get(BNO055IMU.class, "imu");


        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        lifty.setPower(0);
        carousel.setPower(0);

        //reverse wheels
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifty.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        carousel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lifty.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Define and initialize ALL installed servos.
        claw = hwMap.get(Servo.class, "CLAW");
    }
    public void setMotorPowers(double p){
        setMotorPowers(p,p,p,p);
    }

    public void setMotorPowers(double lF, double rF, double lB, double rB) {
        leftFront.setPower(lF);
        leftBack.setPower(lB);
        rightBack.setPower(rB);
        rightFront.setPower(rF);
    }

    public void liftyUp() {
        final int OFFSET = 150; // TODO: tune
        lifty.setTargetPosition(lifty.getCurrentPosition() + OFFSET);
        lifty.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (lifty.isBusy());
        lifty.setPower(0);
        lifty.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void liftyDown() {
        final int OFFSET = 150; // TODO: tune
        lifty.setTargetPosition(lifty.getCurrentPosition() - OFFSET);
        lifty.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (lifty.isBusy());
        lifty.setPower(0);
        lifty.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

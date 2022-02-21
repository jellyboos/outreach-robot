package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
class HardwarePushbot {
    /* Public OpMode members. */

    public DcMotorEx leftFront = null;
    public DcMotorEx rightFront = null;
    public DcMotorEx leftBack = null;
    public DcMotorEx rightBack = null;
    public CRServo carousel = null;
    BNO055IMU imu = null;

    public static final double CAROUSEL_LEFT_POSITION = 0.5;
    public static final double CAROUSEL_RIGHT_POSITION = -0.5;
    public static final double CAROUSEL_STOP = 0;

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
        leftFront = hwMap.get(DcMotorEx.class, "LF");
        rightFront = hwMap.get(DcMotorEx.class, "RF");
        leftBack = hwMap.get(DcMotorEx.class, "LB");
        rightBack = hwMap.get(DcMotorEx.class, "RB");

        carousel = hwMap.get(CRServo.class, "carousel");
        imu = hwMap.get(BNO055IMU.class, "imu");


        // Set all motors to zero power
        setMotorPowers(0);

        //reverse wheels

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
}

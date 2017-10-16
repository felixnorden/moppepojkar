package utils;

public class Config {

    public static final String C_READER = "CircleReader.py";

    public static final String REGEX = ",";
    public static final String STATE = "STATE";

    public static final String THROTTLE = "THROTTLE";
    public static final String STEER = "STEER";

    public static final String CONNECTION = "CONNECTION";
    public static final String OFF = "OFF";
    public static final String ON = "ON";

    public static final String DIST_SENSOR = "DIST_SENSOR";


    public static final String CAM_TGT_OFFSET = "CAM_TGT_OFFSET";
    public static final String CAM_TGT_DIST = "CAM_TGT_DIST";

    public static final int MAX_INTERMISSION_TIME = 250;

    // Values used by the PIDController for the ACC

    // The delay in milliseconds before calling the ACC module. A higher value means slower updating of the motor values based on sensor values.
    public static final int ACC_UPDATE_DELAY = 100; // milliseconds

    // The distance in meters that the moped will aim to hold from objects detected by the ultrasonic sensor.
    public static final double ACC_TGT_DIST = 0.3; // meters

    // The proportional constant of the PID controller for the ACC. A higher value means that the moped will return higher values to the motor
    // which will result in a greater speed.
    public static final double ACC_P = 70;

    // The relation between the proportional constant and the integral constant of the PID controller for the ACC. A higher value means that
    // the integral sum will grow faster resulting in the target distance being reached more quickly but with a greater risk off overshoot.
    public static final double ACC_I = 0;

    // The relation between the proportional constant and the derivative constant of the PID controller for the ACC. A higher value means that
    // the moped will approach the target more slowly resulting in dampened resonance.
    public static final double ACC_D = 1.5;


    // Values used by the PIDController for the lateral navigation

    // The delay in milliseconds before calling the ACC module. A higher value means slower updating of the motor values based on sensor values.
    public static final int LAT_UPDATE_DELAY = 100; // milliseconds

    // The offset in pixels from the center off the cameras perspective to the target that the moped aims to keep. A Positive value means that
    // the moped aims to keep the target to the right in the cameras perspective. There is no reason for this value to ever be changed from 0
    // unless something unexpected happens.
    public static final double LAT_TGT_POS = 0; // pixels

    // The proportional constant of the PID controller for the lateral navigation. A higher value means that the moped will return higher
    // values to the servo which will result in a greater turning speed.
    public static final double LAT_P = 1;

    // The relation between the proportional constant and the integral constant of the PID controller for the lateral navigation. A higher
    // value means that the integral sum will grow faster resulting in the target position being reached more quickly but with a greater
    // risk off overshoot.
    public static final double LAT_I = 0;

    // The relation between the proportional constant and the derivative constant of the PID controller for the lateral navigation. A higher
    // value means that the moped will approach the target more slowly resulting in dampened resonance.
    public static final double LAT_D = 0;


    // An array of integers that represent linear motor values
    public static int[] motorValues = {0, 7, 11, 15, 19, 23, 27, 37};
}

package utils;

public class Config {

    public static final String QR_PATH = "QReader.py";

    public static final int ACC_UPDATE_DELAY = 100;
    public static final double ACC_TGT_DIST = 0.3;
    public static final double ACC_P = 70;
    public static final double ACC_I = 0;
    public static final double ACC_D = 1.5;

    public static final int LAT_UPDATE_DELAY = 100;
    public static final double LAT_TGT_POS = 0;
    public static final double LAT_P = 10;
    public static final double LAT_I = 0;
    public static final double LAT_D = 0;


    public static int[] motorValues = {0, 7, 11, 15, 19, 23, 27, 37};
}

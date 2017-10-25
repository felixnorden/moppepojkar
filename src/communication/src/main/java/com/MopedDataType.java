package com;

public enum MopedDataType {

    MOPED_STATE,
    VELOCITY,
    SENSOR_DISTANCE,
    ACC_TARGET_VALUE,
    ACC_P_CONSTANT,
    ACC_I_CONSTANT,
    ACC_D_CONSTANT,
    ACC_INTEGRAL_SUM,
	LAT_TARGET_VALUE,
	LAT_P_CONSTANT,
    LAT_I_CONSTANT,
    LAT_D_CONSTANT,
    LAT_INTEGRAL_SUM,
    THROTTLE,
    STEERING,
    CUSTOM_1,
    CUSTOM_2,
    CUSTOM_3;

    //We save the array of existing enums because calling values() is expensive.
    private static MopedDataType[] values = MopedDataType.values();

    /**
     * Convert enum to int
     *
     * @return int
     */
    public int toInt() {
        return this.ordinal();
    }


    /**
     * Converts an integer to a MopedDataType.
     * The integer converts to the corresponding Enum, where the top one equals 1.
     * I.e. 1 = MOPED_STATE, 2 = VELOCITY.
     *
     * @param mopedDataType The integer corresponding to the Enum.
     * @return Null if the list of enums doesn't contain the index (=mopedDataType), the matching MopedDataType otherwise.
     */
    public static MopedDataType parseInt(int mopedDataType) {
        if (mopedDataType <= values.length && mopedDataType >= 0) {
            return values[mopedDataType];
        }
        return null;
    }
}

package com;

public enum MopedDataType {

    MopedState,
    Velocity;

    private static MopedDataType[] values = MopedDataType.values();

    /**
     * Convert enum to int
     *
     * @return int
     */
    public int toInt() {
        return this.ordinal();
    }


    public static MopedDataType parseInt(int mopedDataType) {
        if (mopedDataType <= values.length && mopedDataType >= 0) {
            return values[mopedDataType];
        }
        return null;
    }
}

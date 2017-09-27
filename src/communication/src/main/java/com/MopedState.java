package com;

/**
 * Created by konglobemeralt and isZumpo on 2017-09-14.
 * <p>
 * Basic states for operation of MOPED
 */
public enum MopedState {
    ACC,
    MANUAL;

    private static MopedState[] values = MopedState.values();

    /**
     * Convert enum to int
     *
     * @return int
     */
    public int toInt() {
        return this.ordinal();
    }

    /**
     * Convert int to enum
     *
     * @param mopedState int
     * @return MOPED_STATE enum
     */
    public static MopedState parseInt(int mopedState) {
        if (mopedState <= values.length && mopedState >= 0) {
            return values[mopedState];
        }
        return null;
    }
}
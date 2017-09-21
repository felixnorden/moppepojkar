package com;

/**
 * Created by konglobemeralt and isZumpo on 2017-09-14.
 * <p>
 * Basic states for operation of MOPED
 */
public enum MopedState {
    ACC(MopedStateType.DRIVING_STATE),
    MANUAL(MopedStateType.DRIVING_STATE);

    private static MopedState[] values = MopedState.values();
    private final MopedStateType stateType;

    MopedState(final MopedStateType stateType) {
        this.stateType = stateType;
    }

    /**
     * Get MopedStateType of MopedState enum
     *
     * @return MopedStateType enum
     */
    public MopedStateType getStateType() {
        return this.stateType;
    }

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
     * @return MopedState enum
     */
    public static MopedState parseInt(int mopedState) {
        if (mopedState <= values.length && mopedState >= 0) {
            return values[mopedState];
        }
        return null;
    }
}
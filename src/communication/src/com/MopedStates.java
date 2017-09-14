package com;

/**
 * Created by konglobemeralt and isZumpo on 2017-09-14.
 * <p>
 * Basic states for operation of MOPED
 */
public enum MopedStates {
    ACC(MopedStateType.DRIVING_STATE),
    MANUAL(MopedStateType.DRIVING_STATE);

    private final MopedStateType stateType;

    MopedStates(final MopedStateType stateType) {
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
     * @param mopedState MopedStates enum
     * @return int
     */
    public static int toInt(MopedStates mopedState) {
        return mopedState.ordinal();
    }

    /**
     * Convert int to enum
     *
     * @param mopedState int
     * @return MopedStates enum
     */
    public static MopedStates parseInt(int mopedState) {
        if (mopedState <= MopedStates.values().length && mopedState > 0) {
            return MopedStates.values()[mopedState];
        }
        return null;
    }
}
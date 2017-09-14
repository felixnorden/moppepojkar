package com;

/**
 * Created by konglobemeralt and isZumpo on 2017-09-14.
 */
public enum MopedStates {
    ACC(MopedStateType.DRIVING_STATE),
    MANUAL(MopedStateType.DRIVING_STATE);

    private final MopedStateType stateType;
    private MopedStates(final MopedStateType stateType){
            this.stateType=stateType;
    }

    public MopedStateType getStateType(){
        return this.stateType;
    }
}
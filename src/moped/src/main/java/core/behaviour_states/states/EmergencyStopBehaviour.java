package core.behaviour_states.states;

import core.car_control.CarControl;

public class EmergencyStopBehaviour implements BehaviourState {
    private static final int EMERGENCY_BRAKE = -100;
    private static final int NEUTRAL_STEERING = 0;

    private final CarControl carControl;

    public EmergencyStopBehaviour(CarControl carController) {
        carControl = carController;
    }

    @Override
    public void run() {
        carControl.setSteerValue(NEUTRAL_STEERING);
        carControl.setThrottle(EMERGENCY_BRAKE);
    }
}

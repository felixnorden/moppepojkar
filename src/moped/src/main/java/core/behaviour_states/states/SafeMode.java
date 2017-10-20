package core.behaviour_states.states;

import core.car_control.CarControl;

public class SafeMode implements BehaviourState {
    private static final int NEUTRAL_THROTTLE = 0;
    private static final int NEUTRAL_STEERING = 0;

    private final CarControl carControl;

    public SafeMode(CarControl carController) {
        carControl = carController;
    }

    @Override
    public void run() {
        carControl.setSteerValue(NEUTRAL_STEERING);
        carControl.setSteerValue(NEUTRAL_THROTTLE);
    }
}

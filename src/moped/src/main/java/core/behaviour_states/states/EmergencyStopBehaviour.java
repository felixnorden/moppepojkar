package core.behaviour_states.states;

import core.car_control.CarControl;
import core.sensors.DistanceSensorImpl;

import java.util.function.Consumer;

public class EmergencyStopBehaviour implements BehaviourState {
    private static final int EMERGENCY_BRAKE = -100;
    private static final int NEUTRAL_STEERING = 0;

    private static final double SAFE_BRAKE_MARGIN = 0.2;

    private final CarControl carControl;
    private Consumer<BehaviourState> onCollisionDetected;

    public EmergencyStopBehaviour(CarControl carController) {
        carControl = carController;
        DistanceSensorImpl.getInstance().subscribe(this::calculateCollisionRisk);
    }

    private void calculateCollisionRisk(double distance) {
        int lastThrottle = carControl.getLastThrottle();
        double brakeLength = getBrakeLength(lastThrottle);

        if (brakeLength < distance) {
            onCollisionDetected.accept(this);
        }
    }

    private double getBrakeLength(double speed) {
        // TODO: 06/10/2017 Implement method
        return 0.7;
    }

    @Override
    public void run() {
        carControl.setSteerValue(NEUTRAL_STEERING);
        carControl.setThrottle(EMERGENCY_BRAKE);
    }

    public void setOnCollision(Consumer<BehaviourState> stateConsumer){
        onCollisionDetected = stateConsumer;
    }
}

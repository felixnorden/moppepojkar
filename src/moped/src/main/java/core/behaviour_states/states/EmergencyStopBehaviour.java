package core.behaviour_states.states;

import core.car_control.CarControl;
import core.sensors.SensorRepository;

import java.util.function.Consumer;

public class EmergencyStopBehaviour implements BehaviourState {
    private static final int EMERGENCY_BRAKE = -100;
    private static final int NEUTRAL_STEERING = 0;

    private static final double SAFE_BRAKE_MARGIN = 0.2;

    private final CarControl carControl;
    private Consumer<BehaviourState> onCollisionDetected;

    public EmergencyStopBehaviour(CarControl carController) {
        carControl = carController;
        SensorRepository.getDistanceSensor().subscribe(this::determineCollision);
    }

    private void determineCollision(double distance) {
        int lastThrottle = carControl.getLastThrottle();
        double brakeLength = getBrakeLength(lastThrottle);

        if (brakeLength > distance + SAFE_BRAKE_MARGIN) {
            //onCollisionDetected.accept(this);
        }
    }

    //Equation provided by the team at https://github.com/petrosdeb/Group-Stierna
    private double getBrakeLength(double speed) {
        return 0.06 * Math.pow(speed,1.6);
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

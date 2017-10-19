package core.behaviour_states.states;

import core.car_control.CarControl;
import core.sensors.SensorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EmergencyStop implements BehaviourState {
    private static final int EMERGENCY_BRAKE = -100;
    private static final int NEUTRAL_STEERING = 0;

    private static final double SAFE_BRAKE_MARGIN = 0.2;

    private final CarControl carControl;
    private Consumer<BehaviourState> onEmergencyEvent;
    private List<Double> sensorBuffer = new ArrayList<>();

    public EmergencyStop(CarControl carController) {
        carControl = carController;
        SensorRepository.getDistanceSensor().subscribe(this::determineCollision);
    }

    public void setOnCollision(Consumer<BehaviourState> stateConsumer){
        onEmergencyEvent = stateConsumer;
    }

    @Override
    public void run() {
        carControl.setSteerValue(NEUTRAL_STEERING);
        carControl.setThrottle(EMERGENCY_BRAKE);
    }

    private void determineCollision(double distance) {
    }

}

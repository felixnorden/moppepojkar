package core.action_strategies;

import core.sensors.DistanceSensor;
import core.sensors.DistanceSensorImpl;
import pid.DistancePIDController;

/**
 * An {@link ActionStrategy} which parses the sensor data
 * of a dedicated {@link core.sensors.Sensor}
 */
public class PIDParser implements ActionStrategy {

    private DistancePIDController pidController;
    private DistanceSensor distanceSensor;
    private long lastActionTime;
    private double action;

    public PIDParser() {
        action = 0;
        lastActionTime = System.currentTimeMillis();
        distanceSensor = new DistanceSensorImpl();
        pidController = new DistancePIDController(0.3,10,0,0);
    }


    @Override
    public double takeAction() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActionTime > 50) {
            double distance = distanceSensor.getDistance();
            action = -1 * pidController.evaluation(distance,currentTime- lastActionTime);
            System.out.println("Action: " + action);
            lastActionTime = currentTime;
        }
        return action;
    }
}


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
        distanceSensor = DistanceSensorImpl.getInstance();
        pidController = new DistancePIDController(0.3,30,0,2);
    }

    @Override
    public double takeAction() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActionTime > 100) {
            double distance = distanceSensor.getDistance();

            // TODO: 05/10/2017 Verify that the input should be in seconds
            action = pidController.evaluation(distance,(double) (currentTime- lastActionTime) * 1000);
            lastActionTime = currentTime;
        }
        return action;
    }
}


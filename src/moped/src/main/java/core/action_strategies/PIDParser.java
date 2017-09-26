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
    private long lastAction;

    public PIDParser() {
        lastAction = System.currentTimeMillis();
        distanceSensor = new DistanceSensorImpl();
        pidController = new DistancePIDController(0.3,1,1,1);
    }


    @Override
    public double takeAction() {
        long currentTime = System.currentTimeMillis();
        double actionValue = pidController.evaluation(distanceSensor.getDistance(),currentTime-lastAction);
        lastAction = currentTime;
        return actionValue;
    }
}


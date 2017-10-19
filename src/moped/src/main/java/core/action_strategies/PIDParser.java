package core.action_strategies;

import core.sensors.DistanceSensor;
import core.sensors.SensorRepository;
import core.pid.DistancePIDController;

import static utils.Config.*;

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
        distanceSensor = SensorRepository.getDistanceSensor();
        pidController = new DistancePIDController(ACC_TGT_DIST,ACC_P,ACC_I,ACC_D);
    }

    @Override
    public double takeAction() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActionTime > ACC_UPDATE_DELAY) {
            double distance = distanceSensor.getDistance();

            action = pidController.evaluation(distance,(double) (currentTime- lastActionTime) * 1000);
            lastActionTime = currentTime;
        }
        return action;
    }
}


package core.action_strategies;

/**
 * An {@link ActionStrategy} which parses the sensor data
 * of a dedicated {@link core.sensors.Sensor}
 */
public class PIDParser implements ActionStrategy {

    public PIDParser() {
        // Add instance variable for PID
    }


    @Override
    public double takeAction() {
        return 0;
    }
}


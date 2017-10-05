package core.sensors;

/**
 * Interface for distance sensors.
 */
public interface DistanceSensor extends Sensor{

    /**
     * @return The distance in meters.
     */
    double getDistance();

}

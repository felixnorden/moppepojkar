package core.sensors;

/**
 * Interface for distancesensors.
 */
public interface DistanceSensor extends Sensor{

    /**
     * @return The distance in meters.
     */
    double getDistance();

}

package distancekeeper;

import sensordataconversion.SensorDataConverter;

/**
 * Created by Elias and Marcus
 * This class is used as a facade for the distanceKeeper module. Other parts of the program can use this class to calculate
 * the effect that should be sent to the engine when ACC is activated.
 */
public class DistanceKeeperFacade {


    //PreliminaryDistancKeeper distancKeeper = new PreliminaryDistanceKeeper();
    SensorDataConverter sensorDataConverter = new SensorDataConverter();

    /**
     *
     * This method uses the different classes in the distanceKeeper module in order to calculate the
     * desired engine effect.
     * @param distance the current distance to the object in front of the moped, given in cm.
     * @param velocity the current velocity of the moped given in cm/s.
     * @return the appropriate engine value needed to keep the desired distance.
     */
    public int calculateEngineEffect(String distance, String velocity){


        //return distanceKeeper.enginePower(sensorDataConverter.convertDistance(distance),sensorDataConverter.convertVelocity(velocity));

        return 0;
    }
}

package distancekeeper;

public class DistanceKeeperFacade {

    //PreliminaryDistancKeeper distancKeeper = new PreliminaryDistanceKeeper;
    SensorDataConverter sensorDataConverter = new SensorDataConverter();

    public int calculateEngineEffect(String distance, String velocity){


        //return distanceKeeper.enginePower(sensorDataConverter.convertDistance(distance),sensorDataConverter.convertVelocity(velocity));

        return 0;
    }
}

package under_construction;

import pid.DistancePIDController;

/**
 * Created by Elias and Marcus
 * This class is used as a facade for the distanceKeeper module. Other parts of the program can use this class to calculate
 * the effect that should be sent to the engine when ACC is activated.
 */
public class DistanceKeeperFacade {
    private DistancePIDController distancePIDController;

    public DistanceKeeperFacade(double targetValue, double constantP, double constantIRelation, double constantDRelation){
        distancePIDController = new DistancePIDController(targetValue, constantP, constantIRelation, constantDRelation);
    }
    /**
     *
     * This method uses the different classes in the distanceKeeper module in order to calculate the
     * desired engine effect.
     * @param targetDistance the current distance to the object in front of the moped, given in cm.
     * @param deltaTime delta Time since last call.
     * @return the pid-value for distance keeping.
     */
    public double calculatePIDValue(double targetDistance, double deltaTime){
        return distancePIDController.evaluation(targetDistance,deltaTime);
    }
}

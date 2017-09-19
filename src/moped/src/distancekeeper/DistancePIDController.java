package distancekeeper;

public class DistancePIDController extends PIDController {

    //An extension of PIDController, but with specific functionality for distancekepping, such as controlling that inputs are valid
    public DistancePIDController(double targetValue, double constantP, double constantIRelation, double constantDRelation){
        super(targetValue,constantP,constantIRelation,constantDRelation);
    }

    @Override
    public double evaluation(double currentValue, double deltaTime){

        //Checking if the sensor data is valid (invalid sensor data is represented with -1)
        if (currentValue != -1) {
            return super.evaluation(currentValue,deltaTime);
        } else {
            return 0;
        }
    }

}

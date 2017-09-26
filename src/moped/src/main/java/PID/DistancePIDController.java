
package moped.src.main.java.PID;


public class DistancePIDController extends PIDController {

    /**
     * An extension of PIDController, but with specific functionality for keeping distance, such as controlling that inputs are valid.
     */

    public DistancePIDController(double targetValue, double constantP, double constantIRelation, double constantDRelation){
        super(targetValue,constantP,constantIRelation,constantDRelation);
    }

    /**
     * {@inheritDoc}
     * @param currentValue Current value to be compared to the target value.
     * @param deltaTime Elapsed time since the last method call.
     * @return
     */

    @Override
    public double evaluation(double currentValue, double deltaTime){

        //Checking if the sensor data is valid (invalid sensor data is represented with NaN)
        if (currentValue != Double.NaN && currentValue > 0) {
            return super.evaluation(currentValue,deltaTime);
        } else {
            return 0;
        }
    }


}

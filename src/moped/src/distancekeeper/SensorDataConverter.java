package distancekeeper;

/**
 * Created by elias and marcus on 2017-09-12.
 * The class is used to validate and convert the inputs to doubles in order to make them usable for calculations.
 */
public class SensorDataConverter {

    /**
     * Validates and converts the distance input that is given.
     * @param distance The distance to the object in front, in the form of a string
     * @return The distance to the object in front in form of a double
     */
    public double convertDistance(String distance) {
        if (distance.matches("[0-9.]+")) {
            double convertedDistance = Double.valueOf(distance);
            if (validateDistance(convertedDistance)) {
                return convertedDistance;
            } else {
                //TODO add exception handling
                System.out.println("INVALID DISTANCE-VALIDATION");
                return -1;
            }
        } else {
            System.out.println("INVALID DISTANCE-NOT A NUMBER");
            return -1;
        }
    }


    /**
     * Validates and converts the distance input that is given.
     * @param velocity The current velocity of the moped in the form of a string
     * @return The current velocity in the form of a double.
     */
    public double convertVelocity(String velocity) {
        if (velocity.matches("[0-9.-]+")) {
            double convertedVelocity = Double.valueOf(velocity);
            if (validateVelocity(convertedVelocity)) {
                return convertedVelocity;
            } else {
                //TODO add exception handling
                System.out.println("INVALID VELOCITY-VALIDATION");
                return -1;
            }
        } else {
            System.out.println("INVALID VELOCITY-NOT A NUMBER");
            return -1;
        }
    }

    /**
     * Validates the given distance to make sure that it's a value bigger than zero.
     * @param distanceToValidate the given distance to be validated
     * @return true if the distance is valid
     */
    private boolean validateDistance(double distanceToValidate) {
        if (distanceToValidate < 0) {
            //TODO additional validations
            return false;
        } else return true;


    }

    /**
     * Validates the given velocity to make sure that it's valid
     * @param velocityToValidate the given velocity to be validated
     * @return true if the velocity is valid
     */
    private boolean validateVelocity(double velocityToValidate) {
        //TODO validations
        return true;

    }

}

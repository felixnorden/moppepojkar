package distancekeeper;

import static java.lang.Double.NaN;

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
                return NaN;
            }
        } else {
            return NaN;
        }
    }


    /**
     * Validates and converts the distance input that is given.
     * @param velocity The current velocity of the moped in the form of a string
     * @return The current velocity in the form of a double.
     */
    public double convertVelocity(String velocity) {
        if (velocity.matches("[0-9.-]+")) {
            return Double.valueOf(velocity);
        } else {
            return NaN;
        }
    }

    /**
     * Validates the given distance to make sure that it's a value bigger than zero.
     * @param distanceToValidate the given distance to be validated
     * @return true if the distance is valid
     */
    private boolean validateDistance(double distanceToValidate) {
        if (distanceToValidate < 0)  {
            return false;
        } else return true;


    }
}

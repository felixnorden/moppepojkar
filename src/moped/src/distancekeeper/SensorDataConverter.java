package distancekeeper;

/**
 * Created by elias and marcus on 2017-09-12.
 */
public class SensorDataConverter {

    public int convertDistance(String distance) {
        if (distance.matches("[0-9]+")) {
            int convertedDistance = Integer.valueOf(distance);
            if (validateDistance(convertedDistance)) {
                return convertedDistance;
            } else {
                System.out.println("INVALID DISTANCE-VALIDATION");
                return 0;
            }
        } else {
            System.out.println("INVALID DISTANCE-NOT A NUMBER");
            return 0;
        }
    }


    public int convertVelocity(String velocity) {
        if (velocity.matches("[0-9.-]+")) {
            int convertedVelocity = Integer.valueOf(velocity);
            if (validateVelocity(convertedVelocity)) {
                return convertedVelocity;
            } else {
                System.out.println("INVALID VELOCITY-VALIDATION");
                return 0;
            }
        } else {
            System.out.println("INVALID VELOCITY-NOT A NUMBER");
            return 0;
        }
    }

    private boolean validateDistance(int distanceToValidate) {
        if (distanceToValidate < 0) {
            //TODO additional validations
            return false;
        } else return true;


    }

    private boolean validateVelocity(int velocityToValidate) {
        //TODO validations
        return true;

    }

}

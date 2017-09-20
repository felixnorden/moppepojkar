package enginedataconversion;

public class EngineDataConverter {
    private int[] validMotorValues;

    //This class constructor takes in an array of values, one of which we want another value to approximate to.
    public EngineDataConverter(int[] validMotorValues) {
        this.validMotorValues = validMotorValues;
    }

    /**
     * This method takes in a double which it compares to values of the objects array. The value in the array which
     * has the smallest difference to the double is returned.
     * @param throttle is a value which we compare to the values of the array to find the closest approximation
     * @return the value in the array which is closest to the value of throttle
     */
    public int convertToMotorValue( double throttle){
        double smallestDifference = Double.POSITIVE_INFINITY;
        int r = 0;
        for (int i: validMotorValues) {
            System.out.println(i);
            double difference = Math.abs(i - throttle);
            if (smallestDifference > difference){
                smallestDifference = difference;
                r = i;
            }else if (smallestDifference == difference && throttle < i){
                r = i;
            }
        }

        return r;
    }

}

package core.sensors;

import static java.lang.Double.*;

/**
 * Filters raw data values to dampen value spikes and smooth out transitions between values.
 */
public class LowPassFilter {
    private double currentValue;
    private final double weight;

    public LowPassFilter(double weight) {
        this.weight = weight;
    }

    /**
     * Takes in the next raw data value and sends back a filtered value from it.
     * @param nextRawValue The next raw value.
     * @return The filtered value.
     */
    public double filterValue(double nextRawValue) {
        double calibratedValue;

        calibratedValue =   Double.isNaN(nextRawValue) ?
                            currentValue :
                            calibrateValue(nextRawValue);

        currentValue = calibratedValue;
        return calibratedValue;
    }

    private double calibrateValue(double nextRawValue) {

        double difference = nextRawValue - currentValue;
        double weightedDifference = difference * weight;

        return currentValue + weightedDifference;
    }


}
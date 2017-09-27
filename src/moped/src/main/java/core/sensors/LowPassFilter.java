package core.sensors;

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
        // TODO: 27/09/2017 Fix uaf hack
        if (nextRawValue > 0) {
            double difference = nextRawValue - currentValue;
            double weightedDifference = difference * weight;
            currentValue += weightedDifference;
        }
        return currentValue;
    }
}
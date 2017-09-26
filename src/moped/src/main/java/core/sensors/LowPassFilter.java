package core.sensors;


public class LowPassFilter {

    private double currentValue;
    private final double weight;

    public LowPassFilter(double weight) {
        this.weight = weight;
    }

    double filterValue(double updatedValue) {
        double difference = updatedValue - currentValue;
        double weightedDifference = difference * weight;
        currentValue += weightedDifference;
        return currentValue;
    }
}
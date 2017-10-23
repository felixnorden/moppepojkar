package tools;

import core.sensors.LowPassFilter;

import java.util.Random;

/**
 * Class used for creating noisy values and that can pass them through a LowPassFilter.
 */
public class NoiseNumberGenerator {
    private Random rand = new Random();
    private double maxNormalDelta;
    private double randomSpikeRate;
    private double spikeModifier;

    /**
     * Creates a {@link NoiseNumberGenerator} that can be used to create noisy arrays and values.
     * @param maxNoiseDelta The absolute value that noise can become.
     * @param randomSpikeRate The chance of a spike occurring, from 0 to 1.
     * @param spikeModifier How much a value will be multiplied with a spike.
     */
    public NoiseNumberGenerator(double maxNoiseDelta, double randomSpikeRate, double spikeModifier) {
        this.maxNormalDelta = maxNoiseDelta;
        this.randomSpikeRate = randomSpikeRate;
        this.spikeModifier = spikeModifier;
    }

    /**
     * Generates an array of noisy values.
     * @param normalValue Approximated average in the array.
     * @param size Size of array.
     * @return Array filled of noisy values.
     */
    public double[] generateNoisyArray(double normalValue, int size) {
        double[] arr = new double[size];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = noisedNumber(normalValue, maxNormalDelta, randomSpikeRate, spikeModifier);
        }
        return arr;
    }

    /**
     * Gives a number some random noise and random chance of spiked value.
     * @param value The default value.
     * @return The altered number.
     */
    public double generateNoisedValue(double value) {
        return noisedNumber(value, maxNormalDelta, randomSpikeRate, spikeModifier);
    }

    /**
     * Gives a number some random noise and random chance of spiked value.
     * @param value The default value.
     * @param maxDelta Max Noise allowed.
     * @param spikeRate Chance modifier for a spike
     * @param spikeValueModifier Modifier value for a spike chance.
     * @return The altered number.
     */
    private double noisedNumber(double value, double maxDelta, double spikeRate, double spikeValueModifier) {
        double noiseValue;
        if (rand.nextDouble() <= spikeRate) {
            noiseValue = (rand.nextDouble()*spikeValueModifier * maxDelta) - maxDelta;
        } else {
            noiseValue = (rand.nextDouble()*2 * maxDelta) - maxDelta;
        }
        return value + noiseValue;
    }

    /**
     * Creates an array of values where the first half are rising and the lower half is falling,
     * with all values affected of random noise and spikes.
     * @param lowValues Lowest value (without added noise) in the array.
     * @param highValues Highest value (without added noise)
     * @param size Size of the array.
     * @return The array that contains all of the values.
     */
    public double[] generateNormalNoiseCurve(double lowValues, double highValues, int size) {
        double[] noiseCurve = new double[size];

        // Rising half
        for (int i = 0; i < size / 2; i++) {
            double normalValue = lowValues + (highValues - lowValues) * (i * (1/((double) size/2)));
            noiseCurve[i] = noisedNumber(normalValue, maxNormalDelta, randomSpikeRate, spikeModifier);
        }

        // Falling half
        for (int i = 0; i < size / 2; i++) {
            double normalValue = highValues - (highValues - lowValues) * (i * (1/((double) size/2)));
            noiseCurve[size / 2 + i] = noisedNumber(normalValue, maxNormalDelta, randomSpikeRate, spikeModifier);
        }

        return noiseCurve;
    }

    public double[] generateNoisySinusCurve(double lowestValue, double highestValue, int sinusPeriod, int size) {
        double[] noisyCurve = new double[size];

        for (int i = 0; i < size; i++) {
            double normalValue = ((highestValue - lowestValue)/2) * Math.sin((2*Math.PI / sinusPeriod) * i) + (lowestValue + highestValue) / 2;
            double noisyValue = generateNoisedValue(normalValue);
            noisyCurve[i] = noisyValue;
        }

        return noisyCurve;
    }

    /**
     * Uses {@link LowPassFilter} to print out all of the values created by different
     * weight values in a LowPassFilter, to the console. The data is based on the rawValues parameter
     * that the LowPassFilter will use.
     *
     * @param filterWeights The different LowPassFilter weights that will be used.
     * @param rawValues Raw values that will be input to the {@link LowPassFilter}.
     */
    public static void outputFilteredValues(double[] filterWeights, double[] rawValues) {
        System.out.println("Raw values:");
        for (double v : rawValues) {
            System.out.println(v);
        }

        for (double filterWeight : filterWeights) {
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("\n\n");
            System.out.println("WEIGHT = " + filterWeight);
            for (double v : getFilteredValues(filterWeight, rawValues)) {
                System.out.println(v);
            }
        }
    }

    /**
     * Filters an array of values through the {@link LowPassFilter}.
     * @param filterWeight The weight for the {@link LowPassFilter}.
     * @param rawValues The data that the {@link LowPassFilter} will use.
     * @return Array of values that have gone through the {@link LowPassFilter}
     */
    public static double[] getFilteredValues(double filterWeight, double[] rawValues) {
        double[] filteredValues = new double[rawValues.length];
        LowPassFilter filter = new LowPassFilter(filterWeight);
        for (int i = 0; i < rawValues.length; i++) {
            filteredValues[i] = filter.filterValue(rawValues[i]);
        }
        return filteredValues;
    }


    public static void main(String[] args) {
        NoiseNumberGenerator nGen = new NoiseNumberGenerator(0.05, 0.09, 8);
        double[] rawValues = nGen.generateNoisySinusCurve(0.3, 0.8, 5 * 10, 250);
        double[] weightValues = {0.1, 0.2, 0.4};

        outputFilteredValues(weightValues,rawValues);


        System.out.println("\n\n\n");
        for (int i = 0; i < 250; i++) {
            System.out.println((double) i/ (double) 10);
        }
    }
}

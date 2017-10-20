package core.sensors;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Emil Jansson on 2017-10-17.
 *
 * This class serves to remove spiky values in a stream of numbers. It does this by not allowing
 * values that differ to much from previousInputs to pass through the filter.
 */
public class QuickChangeFilter implements Filter{
    private double acceptedChange;
    private double lastReturn;
    private int maxQueueSize;

    private Deque<Double> previousInput;

    public QuickChangeFilter(double acceptedChange, int maxQueueSize) {
        this.acceptedChange = acceptedChange;
        this.lastReturn = Double.NaN;
        this.maxQueueSize = maxQueueSize;
        this.previousInput = new ArrayDeque<Double>();
    }

    /**
     * This function filters a value from a data stream in two steps. The first step is to compare
     * the unfiltered value to the last returned value. The next one is to compare the unfiltered
     * value to the average value of the last few inputs.
     * @param unfilteredValue The value to be filtered.
     * @return The last input value to pass through the parameters of the filter.
     */

    public double filterValue(double unfilteredValue){
        double averageInput = calculateAverageInput();

        //Fills queue with inputs until target size is reached. The filter is not active during this time.
        if (previousInput.size() < maxQueueSize){
            previousInput.addFirst(unfilteredValue);
            lastReturn = unfilteredValue;
            return unfilteredValue;
        }
        previousInput.addFirst(unfilteredValue);
        previousInput.removeLast();

        //If the input value is close enough to the last returned value it will pass the filter.
        if (isAcceptableChange(unfilteredValue, lastReturn)){
            lastReturn = unfilteredValue;
            return unfilteredValue;
        }

        //If the input value is close enough to the average of recent inputs it will pass the filter.
        if (isAcceptableChange(unfilteredValue, averageInput)){
            lastReturn = unfilteredValue;
            return unfilteredValue;
        }

        //Value did not pass the filter --> return last value
        return lastReturn;

    }


    private boolean isAcceptableChange(double valueA, double valueB ){
        return Math.abs(valueA - valueB) < acceptedChange;
    }

    private double calculateAverageInput(){
        double averageInput=0;
        for (double d: previousInput){
            averageInput+= d;
        }
        averageInput= averageInput/(double) previousInput.size();
        return averageInput;
    }

}

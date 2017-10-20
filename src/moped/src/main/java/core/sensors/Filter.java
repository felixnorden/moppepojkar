package core.sensors;

/**
 * Filter interface for {@link DistanceSensor} to use.
 */
public interface Filter {

    /**
     * Filters the passed value input
     * @param unfilteredValue value to filter
     * @return a filtered value of the input
     */
    double filterValue(double unfilteredValue);
}

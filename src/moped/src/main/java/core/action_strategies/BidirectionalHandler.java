package core.action_strategies;

/**
 * Handles the lateral and longitudinal maneuverability of the
 * unit, based on what {@link ActionStrategy} has to handle each axis.
 */
public interface BidirectionalHandler {

    /**
     * Calls the latitude actor's {@link ActionStrategy#takeAction()} method
     * @return the calculated value for the {@link core.car_control.CarControl}
     */
    double takeLatitudeAction();

    /**
     * Calls the latitude actor's {@link ActionStrategy#takeAction()} method
     * @return the calculated value for the {@link core.car_control.CarControl}
     */
    double takeLongitudeAction();

}

package core.action_strategies;

/**
 * Handler class for handling control in X-axis and Y-axis
 */
public class BidirectionalHandlerImpl implements BidirectionalHandler {
    private ActionStrategy latitudeStrategy;
    private ActionStrategy longitudeStrategy;

    public BidirectionalHandlerImpl(ActionStrategy latitudeStrategy, ActionStrategy longitudeStrategy) {
        this.latitudeStrategy = latitudeStrategy;
        this.longitudeStrategy = longitudeStrategy;
    }

    @Override
    public double takeLatitudeAction() {
        return latitudeStrategy.takeAction();
    }

    @Override
    public double takeLongitudeAction() {
        return longitudeStrategy.takeAction();
    }

}

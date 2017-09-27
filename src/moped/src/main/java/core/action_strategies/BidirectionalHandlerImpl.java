package core.action_strategies;

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

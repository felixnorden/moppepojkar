package core.action_strategies;

public interface BidirectionalHandler {

    double takeLatitudeAction();
    double takeLongitudeAction();
    void stratergyDualComposite(ActionStrategy latitudeStrategy, ActionStrategy longitudeStratergy);
}

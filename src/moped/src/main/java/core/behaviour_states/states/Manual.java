package core.behaviour_states.states;

import core.action_strategies.*;
import core.car_control.CarControl;

/**
 * Manual {@link BehaviourState} that is fully dependant of
 * {@link RemoteController} to navigate the moped
 */
class Manual implements BehaviourState {

    private final BidirectionalHandler manualHandler;
    private final CarControl carController;

    /**
     * Constructs a fully manual driving state
     * @param carController the mediator to the VCU
     */
    public Manual(CarControl carController) {
        this.carController = carController;

        ActionStrategyFactory strategyFactory = ActionStrategyFactoryImpl.getInstance();

        ActionStrategy steerController = strategyFactory.createSteerController();
        ActionStrategy velocityController = strategyFactory.createVelocityController();

        this.manualHandler = new BidirectionalHandlerImpl(velocityController, steerController);
    }

    @Override
    public void run() {
        carController.setThrottle((int) manualHandler.takeLatitudeAction());
        carController.setSteerValue((int) manualHandler.takeLongitudeAction());
    }
}

package core.behaviour_states.states;

import core.action_strategies.*;
import core.car_control.CarControl;

/**
 * Manual {@link BehaviourState} that is fully dependant of
 * {@link RemoteController} to navigate the moped
 */
class Manual implements BehaviourState {

    private final BidirectionalHandler handler;
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

        this.handler = new BidirectionalHandlerImpl(velocityController, steerController);
    }

    @Override
    public void run() {
        carController.setThrottle((int) handler.takeLatitudeAction());
        carController.setSteerValue((int) handler.takeLongitudeAction());
    }
}

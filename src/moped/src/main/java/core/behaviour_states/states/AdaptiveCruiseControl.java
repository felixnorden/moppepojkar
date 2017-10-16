package core.behaviour_states.states;

import core.action_strategies.*;
import core.car_control.CarControl;

import static utils.Config.MAX_INTERMISSION_TIME;


/**
 * Adaptive Cruise Control {@link BehaviourState} which has an
 * automated Y-axis control and {@link }
 */
class AdaptiveCruiseControl implements BehaviourState {
    private BidirectionalHandler currentHandler;

    private BidirectionalHandler accHandler;

    private CarControl carController;
    private long lastRunTime;

    /**
     * Constructs a {@link BehaviourState} which follows the
     * object in front of the unit
     *
     * @param carController the mediator to the VCU
     */
    public AdaptiveCruiseControl(CarControl carController) {
        lastRunTime = System.currentTimeMillis();

        ActionStrategyFactory actionFactory = ActionStrategyFactoryImpl.getInstance();

        ActionStrategy pidController = actionFactory.createPIDController();
        ActionStrategy steerController = actionFactory.createSteerController();
        this.accHandler = new BidirectionalHandlerImpl(pidController, steerController);

        this.carController = carController;

        this.currentHandler = this.accHandler;
    }

    @Override
    public void run() {
        // TODO: 12/10/2017 Add reset methods to actions strategies
        if (System.currentTimeMillis() - lastRunTime > MAX_INTERMISSION_TIME) {
            ActionStrategyFactory actionFactory = ActionStrategyFactoryImpl.getInstance();

            ActionStrategy pidController = actionFactory.createPIDController();
            ActionStrategy steerController = actionFactory.createSteerController();
            this.accHandler = new BidirectionalHandlerImpl(pidController, steerController);
            this.currentHandler = this.accHandler;

        }

        carController.setThrottle((int) currentHandler.takeLatitudeAction());
        carController.setSteerValue((int) currentHandler.takeLongitudeAction());
        lastRunTime = System.currentTimeMillis();
    }
}

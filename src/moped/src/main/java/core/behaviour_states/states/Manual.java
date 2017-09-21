package core.behaviour_states.states;

import com_io.CommunicationsMediator;
import com_io.CommunicatorFactory;
import com_io.CommunicatorFactoryImpl;
import core.action_strategies.*;
import core.car_control.CarControl;

class Manual implements BehaviourState {

    private final BidirectionalHandler handler;
    private final CarControl carController;

    public Manual(CarControl carController) {
        this.carController = carController;

        CommunicatorFactory comFactory = CommunicatorFactoryImpl.getFactoryInstance();
        CommunicationsMediator comIO = comFactory.getComInstance();

        ActionStrategyFactory strategyFactory = ActionStrategyFactoryImpl.getInstance();

        ActionStrategy steerController = strategyFactory.createSteerController();
        ActionStrategy velocityController = strategyFactory.createVelocityController();

        this.handler = new BidirectionalHandlerImpl(velocityController, steerController);

    }

    public void init(ActionStrategy controller) {
    }

    @Override
    public void run() {

    }
}

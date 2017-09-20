package core.behaviour_states.states;

import com_io.CommunicationsMediator;
import com_io.CommunicatorFactory;
import core.action_strategies.*;

class AdaptiveCruiseControl implements BehaviourState {

    private BidirectionalHandler currentHandler;

    private final BidirectionalHandler accHandler;
    private final BidirectionalHandler emergencyHandler;
    private final BidirectionalHandler manualHandler;

    public AdaptiveCruiseControl() {

        CommunicationsMediator comIO = CommunicatorFactory.getComInstance();

        ActionStrategyFactory strategyFactory = ActionStrategyFactoryImpl.getInstance();

        ActionStrategy pidController = strategyFactory.createPIDController();
        ActionStrategy steerController = new RemoteController(RemoteController.Axis.X, comIO);

        this.accHandler = new BidirectionalHandlerImpl(pidController, steerController);

        ActionStrategy emergencyController = new EmgyStop()

    }

    @Override
    public void run() {

    }

    // Parameters?
    private ActionStrategy determineStrategy() {
        return null;
    }
}

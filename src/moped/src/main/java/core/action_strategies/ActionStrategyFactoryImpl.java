package core.action_strategies;

import com_io.CommunicatorFactoryImpl;

/**
 * Factory for creating different {@link ActionStrategy} instances
 */
public class ActionStrategyFactoryImpl implements ActionStrategyFactory {
    private static final ActionStrategyFactory INSTANCE = new ActionStrategyFactoryImpl();

    @Override
    public ActionStrategy createPIDController() {
        return new PIDParser();
    }

    @Override
    public ActionStrategy createEmgyController() {
        return null;
    }

    @Override
    public ActionStrategy createSteerController() {
        return new RemoteController(RemoteController.Axis.X, CommunicatorFactoryImpl.getFactoryInstance().getComInstance());
    }

    @Override
    public ActionStrategy createVelocityController() {
        return new RemoteController(RemoteController.Axis.Y, CommunicatorFactoryImpl.getFactoryInstance().getComInstance());
    }

    @Override
    public ActionStrategy createRemoteController() {
        return null;
    }

    @Override
    public ActionStrategy createLateralController() {
        return new LateralController("QReader.py");
    }

    public static ActionStrategyFactory getInstance() {
        return INSTANCE;
    }

    private ActionStrategyFactoryImpl() {}
}

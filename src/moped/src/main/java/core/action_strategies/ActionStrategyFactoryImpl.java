package core.action_strategies;

import com_io.CommunicatorFactory;

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
        return new RemoteController(RemoteController.Axis.X, CommunicatorFactory.getComInstance());
    }

    @Override
    public ActionStrategy createVelocityController() {
        return new RemoteController(RemoteController.Axis.Y, CommunicatorFactory.getComInstance());
    }

    @Override
    public ActionStrategy createLateralController() {
        return new LateralController(CommunicatorFactory.getComInstance());
    }

    public static ActionStrategyFactory getInstance() {
        return INSTANCE;
    }

    private ActionStrategyFactoryImpl() {}
}

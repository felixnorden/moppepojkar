package core.action_strategies;

import com_io.CommunicationsMediator;
import com_io.CommunicatorFactoryImpl;
import sun.management.Sensor;

public class ActionStrategyFactoryImpl implements ActionStrategyFactory {
    private static final ActionStrategyFactory INSTANCE = new ActionStrategyFactoryImpl();

    private CommunicationsMediator appSocket;
    private Sensor DistanceSensorImpt;


    @Override
    public ActionStrategy createPIDController() {
        return null;
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
    public ActionStrategy createRemoteController() {
        return null;
    }

    public static ActionStrategyFactory getInstance() {
        return INSTANCE;
    }

    private ActionStrategyFactoryImpl() {}
}

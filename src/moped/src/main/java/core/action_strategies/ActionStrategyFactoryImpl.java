package core.action_strategies;

import com_io.CommunicationsMediator;
import sun.management.Sensor;

public class ActionStrategyFactoryImpl implements ActionStrategyFactory {
    private static final ActionStrategyFactory INSTANCE = new ActionStrategyFactoryImpl();

    private CommunicationsMediator appSocket;
    private Sensor DistanceSensorImpt;


    @Override
    public ActionStrategy createPidParser() {
        return null;
    }

    @Override
    public ActionStrategy createEmgyStop() {
        return null;
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

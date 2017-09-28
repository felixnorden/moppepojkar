package core.action_strategies;

import com_io.CommunicatorFactoryImpl;
import core.sensors.Sensor;

public class ActionStrategyFactoryImpl implements ActionStrategyFactory {
    private static final ActionStrategyFactory INSTANCE = new ActionStrategyFactoryImpl();

    private Sensor DistanceSensorImpt;


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

    public static ActionStrategyFactory getInstance() {
        return INSTANCE;
    }

    private ActionStrategyFactoryImpl() {}
}

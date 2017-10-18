package core.behaviour_states.states;

import core.action_strategies.*;
import core.car_control.CarControl;
import core.sensors.SensorRepository;

import static utils.Config.MIN_INTERMISSION_TIME;

/**
 * State for handling platooningHandler,
 * i.e both lateral and longitudinal automation.
 */
public class Platooning implements BehaviourState {
    private CarControl carControl;
    private BidirectionalHandler platooningHandler;
    private long lastRunTime;

    public Platooning(CarControl carController) {
        lastRunTime = System.currentTimeMillis();
        this.carControl = carController;

        ActionStrategyFactory actionFactory = ActionStrategyFactoryImpl.getInstance();


        ActionStrategy acc = actionFactory.createPIDController();
        ActionStrategy lateral = actionFactory.createLateralController();

        platooningHandler = new BidirectionalHandlerImpl(lateral,acc);
    }

    @Override
    public void run() {
        // TODO: 12/10/2017 Add reset methods to actions strategies
        if (System.currentTimeMillis() - lastRunTime > MIN_INTERMISSION_TIME) {
            // WARNING!!! Memory leak is created when this is run
            ActionStrategyFactory actionFactory = ActionStrategyFactoryImpl.getInstance();

            ActionStrategy acc = actionFactory.createPIDController();
            ActionStrategy lateral = actionFactory.createLateralController();

            platooningHandler = new BidirectionalHandlerImpl(lateral,acc);
        }

        carControl.setSteerValue((int) platooningHandler.takeLatitudeAction());
        carControl.setThrottle((int) platooningHandler.takeLongitudeAction());
        lastRunTime = System.currentTimeMillis();


    }
}

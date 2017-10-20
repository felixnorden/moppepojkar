package core.behaviour_states.states;

import com_io.CommunicationsMediator;
import com_io.Direction;
import core.action_strategies.*;
import core.car_control.CarControl;

import static utils.Config.*;

/**
 * State for handling platooning,
 * i.e both lateral and longitudinal automation.
 */
public class Platooning implements BehaviourState {
    private CarControl carControl;
    private BidirectionalHandler platooningHandler;
    private long lastRunTime;
    private CommunicationsMediator communicationsMediator;

    public Platooning(CarControl carController, CommunicationsMediator communicationsMediator) {
        this.communicationsMediator = communicationsMediator;
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
        if (System.currentTimeMillis() - lastRunTime > MAX_INTERMISSION_TIME) {
            // WARNING!!! Memory leak is created when this is run
            ActionStrategyFactory actionFactory = ActionStrategyFactoryImpl.getInstance();

            ActionStrategy acc = actionFactory.createPIDController();
            ActionStrategy lateral = actionFactory.createLateralController();

            platooningHandler = new BidirectionalHandlerImpl(lateral,acc);
        }

        int steerValue = (int) platooningHandler.takeLatitudeAction();
        int throttleValue = (int) platooningHandler.takeLongitudeAction();

        carControl.setSteerValue(steerValue);
        carControl.setThrottle(throttleValue);

        communicationsMediator.transmitData(STEER + SEPARATOR + steerValue, Direction.EXTERNAL);
        communicationsMediator.transmitData(THROTTLE + SEPARATOR + throttleValue, Direction.EXTERNAL);


        lastRunTime = System.currentTimeMillis();


    }
}

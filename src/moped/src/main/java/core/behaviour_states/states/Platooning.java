package core.behaviour_states.states;

import core.action_strategies.*;
import core.behaviour_states.states.BehaviourState;
import core.car_control.CarControl;

/**
 * State for handling platooning,
 * i.e both lateral and longitudinal automation.
 */
public class Platooning implements BehaviourState {

    private CarControl carControl;

    private BidirectionalHandler platooning;

    public Platooning(CarControl carController) {
        this.carControl = carController;

        ActionStrategyFactory actionFactory = ActionStrategyFactoryImpl.getInstance();


        ActionStrategy acc = actionFactory.createPIDController();
        ActionStrategy lateral = actionFactory.createLateralController();

        platooning = new BidirectionalHandlerImpl(lateral,acc);
    }

    @Override
    public void run() {
        carControl.setSteerValue((int) platooning.takeLatitudeAction());
        carControl.setThrottle((int) platooning.takeLongitudeAction());
    }
}

package core.car_control;

import core.action_strategies.*;
import core.behaviour_states.states.BehaviourState;

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

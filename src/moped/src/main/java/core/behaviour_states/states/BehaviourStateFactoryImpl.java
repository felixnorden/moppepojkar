package core.behaviour_states.states;

import core.car_control.CarControl;
import core.car_control.CarControlImpl;

public class BehaviourStateFactoryImpl implements BehaviourStateFactory{

    private static final BehaviourStateFactory INSTANCE = new BehaviourStateFactoryImpl();
    private static final CarControl carController = new CarControlImpl("run.py");

    /**
     *
     * @return the singleton instance of the behaviour factory
     */
    public static BehaviourStateFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public BehaviourState createManualBehaviour() {
        return new Manual();
    }

    @Override
    public BehaviourState createAdaptiveCruiseControlBehaviour() {
        return new AdaptiveCruiseControl(carController);
    }

    private BehaviourStateFactoryImpl() {}

}

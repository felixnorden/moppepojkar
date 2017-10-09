package core.behaviour_states.states;

import core.car_control.CarControl;
import core.car_control.CarControlImpl;

import java.util.function.Consumer;

/**
 * Factory for instantiating the different {@link BehaviourState} that
 * the system supports
 */
public class BehaviourStateFactoryImpl implements BehaviourStateFactory{

    private static final BehaviourStateFactory INSTANCE = new BehaviourStateFactoryImpl();
    private static final CarControl carController = new CarControlImpl();

    /**
     *
     * @return the singleton instance of the behaviour factory
     */
    public static BehaviourStateFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public BehaviourState createManualBehaviour() {
        return new Manual(carController);
    }

    @Override
    public BehaviourState createAdaptiveCruiseControlBehaviour() {
        return new AdaptiveCruiseControl(carController);
    }

    @Override
    public BehaviourState createPlatooningBehaviour() {
        return new Platooning(carController);
    }

    @Override
    public BehaviourState createSafeModeBehaviour() {
        return new SafeModeBehaviour(carController);
    }

    @Override
    public BehaviourState createEmergencyStop(Consumer<BehaviourState> stateConsumer) {
        EmergencyStopBehaviour emergencyStopBehaviour = new EmergencyStopBehaviour(carController);
        emergencyStopBehaviour.setOnCollision(stateConsumer);
        return emergencyStopBehaviour;
    }


    private BehaviourStateFactoryImpl() {}

}

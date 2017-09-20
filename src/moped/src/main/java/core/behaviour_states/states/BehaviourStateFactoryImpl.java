package core.behaviour_states.states;

public class BehaviourStateFactoryImpl implements BehaviourStateFactory{

    private static final BehaviourStateFactory INSTANCE = new BehaviourStateFactoryImpl();

    public static BehaviourStateFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public BehaviourState createManualBehaviour() {
        return new Manual();
    }

    @Override
    public BehaviourState createAdaptiveCruiseControlBehaviour() {
        return new AdaptiveCruiseControl();
    }

    private BehaviourStateFactoryImpl() {}

}

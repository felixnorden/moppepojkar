package core.behaviour_states.states;

public class BehaviourStateFactoryImpl implements BehaviourStateFactory{
    @Override
    public BehaviourState createManualBehaviour() {
        return new Manual();
    }

    @Override
    public BehaviourState createAdaptiveCruiseControlBehaviour() {
        return new AdaptiveCruiseControl();
    }
}

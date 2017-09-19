package core.behaviour_states.states;

public interface BehaviourStateFactory {
    BehaviourState createManualBehaviour();
    BehaviourState createAdaptiveCruiseControlBehaviour();
    
}

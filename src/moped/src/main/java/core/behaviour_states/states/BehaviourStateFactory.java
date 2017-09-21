package core.behaviour_states.states;

public interface BehaviourStateFactory {

    /**
     * Instantiates and configures a {@link Manual} state
     * @return  a configured {@link Manual}
     */
    BehaviourState createManualBehaviour();

    /**
     * Instantiates and configures a {@link AdaptiveCruiseControl} state
     * @return  a configured {@link AdaptiveCruiseControl}
     */
    BehaviourState createAdaptiveCruiseControlBehaviour();


}

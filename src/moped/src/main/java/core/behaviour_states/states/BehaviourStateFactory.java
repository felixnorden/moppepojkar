package core.behaviour_states.states;

/**
 * Factory interface for instantiating the different {@link BehaviourState}
 * that the implementing system
 */
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

    BehaviourState createPlatooningBehaviour();


}

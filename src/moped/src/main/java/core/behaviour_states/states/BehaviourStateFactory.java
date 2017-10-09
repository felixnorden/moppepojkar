package core.behaviour_states.states;

import java.util.function.Consumer;

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


    BehaviourState createSafeModeBehaviour();

    BehaviourState createEmergencyStop(Consumer<BehaviourState> consumer);
}

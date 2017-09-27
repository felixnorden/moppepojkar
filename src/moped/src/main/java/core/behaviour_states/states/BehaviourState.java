package core.behaviour_states.states;

import core.behaviour_states.StateController;

public interface BehaviourState {

    /**
     * The method that is called by the
     * managing state in {@link core.behaviour_states.StateController}
     * when it calls {@link StateController#run()}
     */
    void run();

}

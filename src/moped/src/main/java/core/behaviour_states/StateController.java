package core.behaviour_states;

import core.behaviour_states.states.BehaviourState;

public class StateController {
    private BehaviourState currentState;

    public StateController() {

    }

    // Inject anything?
    public void init() {

    }

    public BehaviourState getCurrentState() {
        return this.currentState;
    }

    public void setNewState(BehaviourState newState) {
        this.currentState = newState;
    }
}

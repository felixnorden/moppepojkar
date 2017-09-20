package core.behaviour_states;

import core.behaviour_states.states.BehaviourState;
import core.behaviour_states.states.BehaviourStateFactory;
import core.behaviour_states.states.BehaviourStateFactoryImpl;

public class StateController implements Runnable {
    private BehaviourStateFactory stateFactory;
    private BehaviourState currentState;

    public StateController() {
        this.stateFactory = BehaviourStateFactoryImpl.getInstance();

        // Possibly change to default safe mode when implemented
        this.currentState = stateFactory.createManualBehaviour();
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

    @Override
    public void run() {
        currentState.run();
    }
}

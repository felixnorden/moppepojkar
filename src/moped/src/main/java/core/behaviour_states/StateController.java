

package core.behaviour_states;

import core.behaviour_states.states.BehaviourState;
import core.behaviour_states.states.BehaviourStateFactory;
import core.behaviour_states.states.BehaviourStateFactoryImpl;

/**
 * Controller which controls the different states of the system
 */
public class StateController implements Runnable {
    private BehaviourStateFactory stateFactory;
    private BehaviourState currentState;

    public StateController() {
        this.stateFactory = BehaviourStateFactoryImpl.getInstance();

        // Possibly change to default safe mode when implemented
        this.currentState = stateFactory.createAdaptiveCruiseControlBehaviour();
    }

    /**
     *
     * @return current behaviour of the MOPED
     */
    public BehaviourState getCurrentState() {
        return this.currentState;
    }

    /**
     *
     * @param newState the new current state for the MOPED
     */
    public void setNewState(BehaviourState newState) {
        this.currentState = newState;
    }

    @Override
    public void run() {
        currentState.run();
    }
}

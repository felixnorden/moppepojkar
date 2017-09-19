package core.behaviour_states.states;

import core.action_strategies.ActionStrategy;

public class AdaptiveCruiseControl implements BehaviourState {

    private ActionStrategy currentStrategy;

    // Remove?
    private ActionStrategy emgyStopStrategy;

    @Override
    public void run() {

    }

    // Parameters?
    private ActionStrategy determineStrategy() {
        return null;
    }
}

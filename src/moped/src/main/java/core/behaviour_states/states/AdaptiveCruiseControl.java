package core.behaviour_states.states;

import core.action_strategies.ActionStrategy;

class AdaptiveCruiseControl implements BehaviourState {

    private ActionStrategy currentStrategy;

    // May be removed in the near future
    private ActionStrategy emgyStopStrategy;

    public AdaptiveCruiseControl() {

    }

    @Override
    public void run() {

    }

    // Parameters?
    private ActionStrategy determineStrategy() {
        return null;
    }
}

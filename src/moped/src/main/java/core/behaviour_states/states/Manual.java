package core.behaviour_states.states;

import core.action_strategies.ActionStrategy;
import core.action_strategies.RemoteController;

public class Manual implements BehaviourState {

    private ActionStrategy controller;

    public Manual() {

    }

    public void init(ActionStrategy controller) {
        this.controller = controller;
    }

    @Override
    public void run() {

    }
}

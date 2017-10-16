package core.action_strategies;

/**
 * Interface for different strategies which that
 * allows for handling {@link core.behaviour_states.states.BehaviourState} to
 * call for the actual action
 */
public interface ActionStrategy {
    double takeAction();
}

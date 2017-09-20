package core.action_strategies;

public interface ActionStrategyFactory {

    ActionStrategy createPidParser();
    ActionStrategy createEmgyStop();
    ActionStrategy createRemoteController();
}

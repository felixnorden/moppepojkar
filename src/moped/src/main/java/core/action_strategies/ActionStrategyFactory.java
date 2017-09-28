package core.action_strategies;

public interface ActionStrategyFactory {

    ActionStrategy createPIDController();
    ActionStrategy createEmgyController();
    ActionStrategy createSteerController();
    ActionStrategy createVelocityController();
    ActionStrategy createRemoteController();
}

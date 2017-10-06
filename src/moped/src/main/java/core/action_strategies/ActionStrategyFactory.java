package core.action_strategies;

/**
 * Factory interface for creating different
 * {@link ActionStrategy} implementations
 */
public interface ActionStrategyFactory {

    /**
     * Creates a configured {@link PIDParser}
     * instance
     * @return a PIDParser {@link ActionStrategy}
     */
    ActionStrategy createPIDController();

    /**
     * Creates a configured {@link EmgyStop}
     * instance
     * @return an Emergency Stop {@link ActionStrategy}
     */
    ActionStrategy createEmgyController();

    /**
     * Creates a configured {@link RemoteController}
     * instance for lateral remote control
     * @return a Remote controller {@link ActionStrategy}
     */
    ActionStrategy createSteerController();

    /**
     * Creates a configured {@link RemoteController}
     * instance for longitudinal remote control
     * @return a Remote controller {@link ActionStrategy}
     */
    ActionStrategy createVelocityController();

    // TODO: 2017-10-05 Remove this, think its unused?
    /**
     * Creates a generic {@link RemoteController}
     * instance for remote control
     * @return a Remote controller {@link ActionStrategy}
     */
    ActionStrategy createRemoteController();

    // TODO: 2017-10-05 Should this really be here as a specific method? Thinking about PIDParser method above ^
    /**
     * Creates a configured {@link LateralController}
     * instance for lateral automation
     * @return a Lateral controller {@link ActionStrategy}
     */
    ActionStrategy createLateralController();
}

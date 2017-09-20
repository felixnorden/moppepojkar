package core.action_strategies;

public class ActionStrategyFactoryImpl implements ActionStrategyFactory {
    private static final ActionStrategyFactory INSTANCE = new ActionStrategyFactoryImpl();

    public static ActionStrategyFactory getInstance() {
        return INSTANCE;
    }

    private ActionStrategyFactoryImpl() {}
}

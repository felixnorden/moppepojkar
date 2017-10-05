package core.action_strategies;

/**
 * {@link ActionStrategy} for handling emergency stops
 * when immediate action needs to be taken
 */
public class EmgyStop implements ActionStrategy {
    @Override
    public double takeAction() {
        return 0;
    }
}

package core.pid;

/**
 * Subclass of the {@link PIDController} class
 * for handling lateral control.
 */
public class LateralPIDController extends PIDController {

    public LateralPIDController(double targetValue, double constantP, double constantIRelation, double constantDRelation) {
        super(targetValue, constantP, constantIRelation, constantDRelation);
    }

    // Does nothing different from the general core.pid except changing the name of the arguments.
    // Ads the possibility of future lateral specific changes to the PID behavior.
    @Override
    public double evaluation(double currentRightOffset, double deltaTime){
        return super.evaluation(currentRightOffset, deltaTime);
    }
}

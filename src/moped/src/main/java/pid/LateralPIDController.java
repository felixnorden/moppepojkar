package pid;


/**
 * Created by Emil Jansson on 2017-10-03.
 */
public class LateralPIDController extends PIDController {

    public LateralPIDController(double targetValue, double constantP, double constantIRelation, double constantDRelation) {
        super(targetValue, constantP, constantIRelation, constantDRelation);
    }

    // Does nothing different from the general pid except changing the name of the arguments.
    // Ads the possibility of future lateral specific changes to the PID behavior.
    @Override
    public double evaluation(double currentRightOffset, double deltaTime){
        return super.evaluation(currentRightOffset, deltaTime);
    }
}

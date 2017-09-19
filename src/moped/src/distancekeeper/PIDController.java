package distancekeeper;

/**
 * Created by Emil Jansson on 2017-09-16.
 */
public class PIDController {
    private double targetValue;
    private double constantP;
    private double constantIRelation;
    private double constantI;
    private double constantDRelation;
    private double constantD;

    private double integralSum = 0;
    private Double lastError = Double.NaN;

    /**
     * This is the PIDController constructor. It creates a new PIDController with custom constants which affect the controllers behavior.
     * @param targetValue The target value the PID strives to minimize error for.
     * @param constantP The proportional constant. A bigger constant means greater reaction effect.
     * @param constantIRelation The relation between the proportional and integral constant where: constantP * constantIRelation = constantI.
     *                          A higher value gives the integral part of the controller more weight which creates a more rapid climb to the target value but might cause overshoot.
     * @param constantDRelation The relation between the proportional and derivative constant where: constantP * constantDRelation = constantD.
     *                          A higher value gives the derivative part of the controller more weight which creates cushioning effect when the error is minimised to fast to mitigate overshoot.
     */

    public PIDController(double targetValue, double constantP, double constantIRelation, double constantDRelation) {
        this.targetValue = targetValue;
        this.constantIRelation = constantIRelation;
        this.constantDRelation = constantDRelation;

        setConstants(constantP);
    }

    public double getConstantP() {
        return constantP;
    }

    /**
     * This function sets the constant P to a new value and updates dependant constants (I and D) accordingly.
     * @param constantP The new value for the proportional constant.
     */

    public void setConstants(double constantP) {
        this.constantP = constantP;
        this.constantI = constantP * constantIRelation;
        this.constantD = constantP * constantDRelation;
    }

    public double getConstantI() {
        return constantI;
    }

    public void setConstantIRelation(double constantIRelation) {
        this.constantIRelation = constantIRelation;
    }

    public double getConstantIRelation(){return constantIRelation;}

    public double getConstantD() {
        return constantD;
    }

    public void setConstantDRelation(double constantDRelation) {
        this.constantDRelation = constantDRelation;
    }

    public double getConstantDRelation(){return constantDRelation;}

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    /**
     * This method evaluates the proper acceleration value to minimize error, where error is the difference between the current value and the target value.
     * The method is dependant on the P-, I- and D-constants and these variables have to be calibrated to maximize the evaluations accuracy.
     * @param currentValue Current value to be compared to the target value.
     * @param deltaTime Elapsed time since the last method call.
     * @return Acceleration value to be fed back into the loop. The first time this method is called it will always return 0.
     */

    public double evaluation(double currentValue, double deltaTime){
        double currentError = (targetValue - currentValue);
        if (!lastError.isNaN()) {
            double P = decideP(currentError);
            double I = decideI(currentError, deltaTime);
            double D = decideD(currentError, deltaTime);
            lastError = currentError;

            return P + I + D;
        }else{
            lastError = currentError;
            return 0;
        }
    }

    private double decideP(double currentError) {
        return currentError * constantP;
    }

    private double decideI(double currentError, double deltaTime) {
        integralSum += currentError * constantI * deltaTime;
        return integralSum;
    }

    private double decideD(double currentError, double deltaTime) {
        return (currentError - lastError) * constantD / deltaTime;
    }
}

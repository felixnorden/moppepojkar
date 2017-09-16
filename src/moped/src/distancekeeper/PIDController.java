package distancekeeper;

/**
 * Created by Emil Jansson on 2017-09-16.
 */
public class PIDController {
    private double targetValue;
    private double constantP;
    private double constantI;
    private double constantD;

    private double integralSum = 0;
    private Double lastError = Double.NaN;

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

    private double decideD(double currentError, double deltaTime) {
        return (currentError - lastError) * constantD / deltaTime;
    }


    private double decideI(double currentError, double deltaTime) {
        integralSum += currentError * constantI * deltaTime;
        return integralSum;
    }

    private double decideP(double currentError) {
        return currentError * constantP;
    }

    public PIDController(double targetValue, double constantP, double constantIRelation, double constantDRelation) {
        this.constantP = constantP;
        this.constantI = this.constantP * constantIRelation;
        this.constantD = this.constantP * constantDRelation;
        this.targetValue = targetValue;
    }

    public double getConstantP() {
        return constantP;
    }

    public void setConstantP(double constantP) {
        this.constantP = constantP;
    }

    public double getConstantI() {
        return constantI;
    }

    public void setConstantI(double constantI) {
        this.constantI = constantI;
    }

    public double getConstantD() {
        return constantD;
    }

    public void setConstantD(double constantD) {
        this.constantD = constantD;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }
}

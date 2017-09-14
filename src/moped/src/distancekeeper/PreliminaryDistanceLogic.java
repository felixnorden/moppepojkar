/**
 * Created by Emil Jansson and Hugo Ekelund on 2017-09-12.
 */

package distancekeeper;

public class PreliminaryDistanceLogic {

    private float targetRelation; //The relation between distance offset and target relative speed.

    private int targetDistance; //Value in cm
    private static int distanceErrorMargin;

    private int lastEnginePower; //Value between -100 and 100

    private int lastDistanceToTarget; //Value in cm

    private long lastSystemTime; //Value in nanoseconds

    public PreliminaryDistanceLogic(float targetRelation, int targetDistance) {
        this.targetRelation = targetRelation;
        this.targetDistance = targetDistance;
    }

    public int enginePower(int distanceToTarget, int currentVelocity){
        int currentEnginePower; //Value in cm
        int currentDistanceToTarget; //Value between -100 and 100
        long currentSystemTime; //Value in cm
        long deltaSystemTime; //Value in nanoseconds
        double relativeSpeed; //Value in cm/s          (cm/nanosecond * 10^-7 = m/s)

        determineTargetSpeed();
        determineDeltaTime();
        determineRelativeSpeed();
        determineNewEnginePower();
        updateValues();

        return 0;   //TODO currentEnginePower;
    }

    private void determineTargetSpeed(){

    }

    private  void determineDeltaTime(){

    }

    private  void determineRelativeSpeed(){

    }

    private void updateValues(){

    }

    private void determineNewEnginePower(){

    }


}

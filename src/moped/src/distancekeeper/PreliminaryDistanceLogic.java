/**
 * Created by Emil Jansson and Hugo Ekelund on 2017-09-12.
 */

package distancekeeper;

public class PreliminaryDistanceLogic {

    private float offsetToSpeedRelation; //The relation between distance offset and target relative speed.

    private float optimalDistance; //Value in cm
    private float velocityErrorMargin; //Value in cm/s

    private int powerIndex; //Index for powerList

    private float lastDistanceToTarget; //Value in cm, value created with sensor data the first time the loop executes

    private long lastSystemTime; //Value in nanoseconds

    private boolean isFirstTime = true;

    public PreliminaryDistanceLogic(float offsetToSpeedRelation, float optimalDistance, float velocityErrorMargin, int startingPowerIndex) {
        this.offsetToSpeedRelation = offsetToSpeedRelation;
        this.optimalDistance = optimalDistance;
        this.velocityErrorMargin = velocityErrorMargin;
        this.powerIndex = startingPowerIndex;

    }

    public int enginePower(float distanceToTarget) {

        if (isFirstTime) {
            this.lastDistanceToTarget = distanceToTarget;
            this.lastSystemTime = System.currentTimeMillis();
            this.isFirstTime = false;
        } else {

            float targetSpeed = determineTargetSpeed(distanceToTarget);
            float deltaTime = determineDeltaTime();
            float relativeSpeed = determineRelativeSpeed(deltaTime, distanceToTarget);
            setPowerIndex(relativeSpeed, targetSpeed);
            updateValues(distanceToTarget);
        }

        return powerIndex;

    }



    private float determineTargetSpeed(float distanceToTarget){

        return ( distanceToTarget - optimalDistance) * offsetToSpeedRelation;
    }

    private  float determineDeltaTime(){
            long currentSystemTime = System.currentTimeMillis();
            float deltaTime = ((float)(currentSystemTime-lastSystemTime)/1000); //milliseconds to seconds
            lastSystemTime = currentSystemTime;

            return deltaTime;
    }

    private  float determineRelativeSpeed(float deltaTime, float distanceToTarget){

        return (lastDistanceToTarget - distanceToTarget)/deltaTime;
    }

    private void updateValues(float distanceToTarget){
        lastDistanceToTarget=distanceToTarget;

    }

    private void setPowerIndex(float relativeSpeed, float targetSpeed){
        if(relativeSpeed>(targetSpeed+velocityErrorMargin)){// TODO limit to size of list
            powerIndex--;
        }else if(relativeSpeed<(targetSpeed-velocityErrorMargin)){// TODO limit to size of list
            powerIndex++;
        }
    }


}

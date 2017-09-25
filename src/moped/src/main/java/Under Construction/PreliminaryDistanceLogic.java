/**
 * Created by Emil Jansson and Hugo Ekelund on 2017-09-12.
 */

public class PreliminaryDistanceLogic {

    private float offsetToSpeedRelation; //The relation between distance offset and target relative speed.

    private float optimalDistance; //Value in cm
    private float velocityErrorMargin; //Value in cm/s

    private int powerIndex; //Index for powerList

    private float lastDistanceToTarget; //Value in cm, value created with sensor data the first time the loop executes

    private long lastSystemTime; //Value in milliseconds

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

            float targetSpeed = determineDesiredSpeed(distanceToTarget);
            float deltaTime = determineDeltaTime();
            float relativeSpeed = determineRelativeSpeed(deltaTime, distanceToTarget);
            setPowerIndex(relativeSpeed, targetSpeed);
            updateValues(distanceToTarget);
        }

        return powerIndex;

    }


    //Determines desired speed by comparing actual distance to target with the optimal distance

    private float determineDesiredSpeed(float distanceToTarget){

        return ( distanceToTarget - optimalDistance) * offsetToSpeedRelation;
    }

    //Determines the change in time since the last loop and converts it to seconds

    private  float determineDeltaTime(){
            long currentSystemTime = System.currentTimeMillis();
            float deltaTime = ((float)(currentSystemTime-lastSystemTime)/1000); //milliseconds to seconds
            lastSystemTime = currentSystemTime;

            return deltaTime;
    }

    //Determines which relative speed the target has had compared to the car since the last loop

    private  float determineRelativeSpeed(float deltaTime, float distanceToTarget){
        return (lastDistanceToTarget - distanceToTarget)/deltaTime;
    }

    //Updates values which is needed for the next loop
    private void updateValues(float distanceToTarget){
        lastDistanceToTarget=distanceToTarget;

    }

    //This determines which speed of the array is to be used by changing the current powerIndex to the index of the desired speed
    private void setPowerIndex(float relativeSpeed, float desiredSpeed){
        if(relativeSpeed>(desiredSpeed+velocityErrorMargin)){// TODO limit to size of list
            powerIndex--;
        }else if(relativeSpeed<(desiredSpeed-velocityErrorMargin)){// TODO limit to size of list
            powerIndex++;
        }
    }


}

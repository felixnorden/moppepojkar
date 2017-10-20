package core.pid;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Emil Jansson on 2017-09-16.
 */
class PIDControllerTest{

    //TODO Would like to create a graph showing the core.pid in action.
    //Checks if the value of error steadily sinks when approaching a stationary target using the PIDController
    //Additionally the overshoot can't exceed 2
    @Test
    void evaluation() {
        double targetValue = 50;
        PIDController pid = new PIDController(targetValue, 0.3, 1f/1000f, 2);
        double currentValue = 0;
        double currentSpeedOfChange = 0;
        double lastError = 50;

        for (int i=0; i<150; i++){
            double throttle = pid.evaluation(currentValue,1);
            currentSpeedOfChange =  -throttle - currentSpeedOfChange * 0.1;
            currentValue+= currentSpeedOfChange;
            double error = targetValue-currentValue;
            assertTrue(Math.abs(error) <= lastError || Math.abs(error) < 2);
            lastError = Math.abs(error);

        }
        assertTrue(lastError<=2);

    }
    //Checks if the value of error steadily sinks when approaching a moving target using the PIDController
    //Additionally the overshoot can't exceed 2

    //Checks if evaluation returns 0 if deltaTime is 0.
    @Test
    void evaluation2() {
        double targetValue = 50;
        PIDController pid = new PIDController(targetValue, 0.3, 1/10, 2);
        double currentValue = 0;
        double currentSpeedOfChange = 0;
        double lastError = 50;

        for (int i=0; i<100; i++){
            double throttle = pid.evaluation(currentValue,0);
            currentSpeedOfChange = currentSpeedOfChange + throttle - currentSpeedOfChange * 0.1;
            currentValue+= currentSpeedOfChange;
            double error = currentValue-targetValue;
            assertTrue(Math.abs(error) == lastError);
            assertTrue(throttle==0);
            lastError = Math.abs(error);

        }

    }

}
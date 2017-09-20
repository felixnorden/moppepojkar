package distancekeeper.distancekeepertest;

import distancekeeper.PIDController;
import org.junit.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Emil Jansson on 2017-09-16.
 */
class PIDControllerTest{

    //TODO Would like to create a graph showing the pid in action.
    //Checks if the value of error steadily sinks when approaching a stationary target using the PIDController
    //Additionally the overshoot can't exceed 2
    @Test
    void evaluation() {
        double targetValue = 50;
        PIDController pid = new PIDController(targetValue, 0.3, 1/10, 2);
        double currentValue = 0;
        double currentSpeedOfChange = 1;
        double lastError = 51;

        for (int i=0; i<100; i++){
            double throttle = pid.evaluation(currentValue,1);
            currentSpeedOfChange = currentSpeedOfChange + throttle - currentSpeedOfChange * 0.1;
            currentValue+= currentSpeedOfChange;
            double error = currentValue-targetValue;
            System.out.println("Error 0: " + error);
            System.out.println("throttle 0: " + throttle);
            assertTrue(Math.abs(error) < lastError || Math.abs(error) < 2);
            lastError = Math.abs(error);

        }

    }
    //Checks if the value of error steadily sinks when approaching a moving target using the PIDController
    //Additionally the overshoot can't exceed 2
    @Test
    void evaluation1() {
        System.out.println("\n\n\n\n");
        double targetValue = 50;
        PIDController pid = new PIDController(targetValue, 0.5, 1.0/10, 1.5);
        double currentValue = 0;
        double currentSpeedOfChange = 1;
        double lastError = 51;
        double targetSpeedOfChange = 10;

        for (int i=0; i<100; i++){
            double throttle = pid.evaluation(currentValue,1);
            currentSpeedOfChange = currentSpeedOfChange + throttle - currentSpeedOfChange * 0.1;
            currentValue+= currentSpeedOfChange - targetSpeedOfChange;
            double error = currentValue-targetValue;
            System.out.println("Error 1: " + error);
            System.out.println("throttle 1: " + throttle);
            assertTrue(Math.abs(error) < lastError || Math.abs(error) < 2 || i < 10);
            lastError = Math.abs(error);

        }

    }
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
            System.out.println("Error 0: " + error);
            System.out.println("throttle 0: " + throttle);
            assertTrue(Math.abs(error) == lastError);
            assertTrue(throttle==0);
            lastError = Math.abs(error);

        }

    }

}
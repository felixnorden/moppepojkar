import distancekeeper.DistancePIDController;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DistancePIDControllerTest {
    //Tests if DistancePIDController is dormant if the sensordata reads -1 (invalid sensor data)
    @Test
    void evaluation2() {
        System.out.println("\n\n\n\n");
        double targetValue = 50;
        DistancePIDController pid2 = new DistancePIDController(targetValue, 0.5, 1.0/10, 1.5);
        double currentValue = -1;
        double deltaTime = 1;
        for (int i=0;i<20;i++) {
            assertEquals(0, pid2.evaluation(currentValue, deltaTime));
        }

    }

}


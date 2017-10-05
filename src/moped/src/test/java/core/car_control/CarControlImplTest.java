package core.car_control;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarControlImplTest {

    private static final int STEER_VALUE = 1;
    private static final int THROTTLE_VALUE = 2;

    private CarControl carControl;

    @BeforeEach
    void setUp() {
        carControl = new CarControlImpl();

        carControl.setSteerValue(STEER_VALUE);
        carControl.setThrottle(THROTTLE_VALUE);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLastThrottle() {
        assert carControl.getLastThrottle() == THROTTLE_VALUE;
    }

    @Test
    void getLastSteer() {
        assert carControl.getLastSteer() == STEER_VALUE;
    }

    @Test
    void setThrottle() {
        carControl.setThrottle(THROTTLE_VALUE + 1);

        assert carControl.getLastThrottle() == THROTTLE_VALUE + 1;
    }

    @Test
    void setSteerValue() {
        carControl.setSteerValue(STEER_VALUE + 1);

        assert carControl.getLastSteer() == STEER_VALUE + 1;
    }

    @Test
    void addThrottle() {
        carControl.addThrottle(1);

        assert carControl.getLastThrottle() == THROTTLE_VALUE + 1;
    }

    @Test
    void addSteer() {
        carControl.addSteer(1);

        assert carControl.getLastSteer() == STEER_VALUE + 1;
    }

}
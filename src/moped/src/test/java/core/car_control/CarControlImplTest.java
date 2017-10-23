package core.car_control;

import arduino.ArduinoCommunicator;
import arduino.ArduinoCommunicatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarControlImplTest {

    private static final int STEER_VALUE = 1;
    private static final int THROTTLE_VALUE = 2;

    private CarControl carControl;

    @BeforeEach
    void setUp() {
        ArduinoCommunicator arduinoMock = Mockito.mock(ArduinoCommunicator.class);
        carControl = new CarControlImpl(arduinoMock);

        carControl.setSteerValue(STEER_VALUE);
        carControl.setThrottle(THROTTLE_VALUE);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLastThrottle() {
        assertEquals(carControl.getLastThrottle(), THROTTLE_VALUE);
    }

    @Test
    void getLastSteer() {
        assertEquals(carControl.getLastSteer(), STEER_VALUE);
    }

    @Test
    void setThrottle() {
        carControl.setThrottle(THROTTLE_VALUE + 1);

        assertEquals(carControl.getLastThrottle(), THROTTLE_VALUE + 1);
    }

    @Test
    void setSteerValue() {
        carControl.setSteerValue(STEER_VALUE + 1);

        assertEquals(carControl.getLastSteer(), STEER_VALUE + 1);
    }

    @Test
    void addThrottle() {
        carControl.addThrottle(1);

        assertEquals(carControl.getLastThrottle(), THROTTLE_VALUE + 1);
    }

    @Test
    void addSteer() {
        carControl.addSteer(1);

        assertEquals(carControl.getLastSteer(), STEER_VALUE + 1);
    }

}
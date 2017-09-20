package core.sensors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistanceSensorImplTest {
    private DistanceSensorImpl sensorInstance;

    @BeforeEach
    void setUp() {
        sensorInstance = DistanceSensorImpl.getInstance();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
        assert sensorInstance != null;
    }

    @Test
    void outputString() {
        sensorInstance.outputString("100");
        assert sensorInstance.getDistance() == 100;
    }

}
package core.sensors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistanceSensorImplTest {
    private DistanceSensor sensorInstance;

    @BeforeEach
    void setUp() {
        sensorInstance = SensorRepository.getDistanceSensor();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
        assert sensorInstance != null;
    }

}
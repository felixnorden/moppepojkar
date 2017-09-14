package distancekeeper.distancekeepertest;

        import org.junit.*;
        import static org.junit.Assert.assertEquals;
      import distancekeeper.SensorDataConverter;

/**
 * Created by elias on 2017-09-12.
 */
public class SensorDataConverterTest {
    /**
     * Tests that the convertDistance() method only accepts valid inputs and converts them to doubles
     */
    @Test
    public void convertDistanceTest(){
        SensorDataConverter sensorDataConverter = new SensorDataConverter();
        assertEquals(-1.0,sensorDataConverter.convertDistance("ASD"),0.01);
        assertEquals(-1.0,sensorDataConverter.convertDistance("-1"), 0.01);
        assertEquals(23.3,sensorDataConverter.convertDistance("23.3"),0.01);
        assertEquals(23.3,sensorDataConverter.convertDistance("23.31"),0.01);


    }

    /**
     * Tests that the convertVelocity() method only accepts valid inputs and converts them to doubles
     */
    @Test
    public void convertVelocityTest(){
        SensorDataConverter sensorDataConverter = new SensorDataConverter();
        assertEquals(-1,sensorDataConverter.convertVelocity("ASD"), 0.01);
        //assertEquals(0,sensorDataConverter.convertVelocity("-1"), 0.01);
        assertEquals(23,sensorDataConverter.convertVelocity("23"), 0.01);

    }
}

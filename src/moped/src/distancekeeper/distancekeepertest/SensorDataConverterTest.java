package distancekeeper.distancekeepertest;

        import org.junit.*;
        import static org.junit.Assert.assertEquals;
      import distancekeeper.SensorDataConverter;

/**
 * Created by elias on 2017-09-12.
 */
public class SensorDataConverterTest {
    @Test
    public void convertDistanceTest(){
        SensorDataConverter sensorDataConverter = new SensorDataConverter();
        assertEquals(0,sensorDataConverter.convertDistance("ASD"));
        assertEquals(0,sensorDataConverter.convertDistance("-1"));
        assertEquals(23,sensorDataConverter.convertDistance("23"));

    }

    @Test
    public void convertVelocityTest(){
        SensorDataConverter sensorDataConverter = new SensorDataConverter();
        assertEquals(0,sensorDataConverter.convertVelocity("ASD"));
        //assertEquals(0,sensorDataConverter.convertVelocity("-1"));
        assertEquals(23,sensorDataConverter.convertVelocity("23"));

    }
}

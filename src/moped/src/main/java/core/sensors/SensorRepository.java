package core.sensors;

import arduino.ArduinoCommunicator;
import arduino.ArduinoCommunicatorImpl;
import com_io.CommunicationsMediator;
import com_io.CommunicatorFactory;

public class SensorRepository {

    private static final CommunicationsMediator COM_INSTANCE = CommunicatorFactory.getComInstance();
    private static final ArduinoCommunicator ARDUINO_COMMUNICATOR = ArduinoCommunicatorImpl.getInstance();

    private static final DistanceSensor DISTANCE_SENSOR = new DistanceSensorImpl(COM_INSTANCE, ARDUINO_COMMUNICATOR);

    public static DistanceSensor getDistanceSensor() {
        return DISTANCE_SENSOR;
    }
}

package core.sensors;

import arduino.ArduinoCommunicator;
import arduino.ArduinoCommunicatorImpl;
import com_io.CommunicationsMediator;
import com_io.CommunicatorFactory;

import static utils.Config.QC_DEQUE_SIZE;
import static utils.Config.QC_MAX_VALUE_OFFSET;

public class SensorRepository {

    private static final CommunicationsMediator COM_INSTANCE = CommunicatorFactory.getComInstance();
    private static final ArduinoCommunicator ARDUINO_COMMUNICATOR = ArduinoCommunicatorImpl.getInstance();

    private static final DistanceSensor DISTANCE_SENSOR = new DistanceSensorImpl(COM_INSTANCE, ARDUINO_COMMUNICATOR, new QuickChangeFilter(QC_MAX_VALUE_OFFSET, QC_DEQUE_SIZE));

    public static DistanceSensor getDistanceSensor() {
        return DISTANCE_SENSOR;
    }
}

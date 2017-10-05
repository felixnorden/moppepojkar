package core.sensors;

import arduino.ArduinoCommunicator;
import core.process_runner.InputSubscriber;
import sensor_data_conversion.SensorDataConverter;

/**
 * Used for reading from the on-board distance sensor.
 */
public class DistanceSensorImpl implements DistanceSensor, InputSubscriber {

    private static final double FILTER_WEIGHT = 0.7;
    private static final DistanceSensorImpl INSTANCE = new DistanceSensorImpl();

    private ArduinoCommunicator arduinoCommunicator;
    private LowPassFilter filter;
    private double currentSensorValue;
    private StringBuilder arduinoInput;

    public static DistanceSensorImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public double getDistance() {
        return currentSensorValue;
    }

    @Override
    public double getValue() {
        return getDistance();
    }

    @Override
    public void kill() {

    }

    @Override
    public synchronized void outputString(String s) {
        if (s.contains("\n")) {
            double temp = new SensorDataConverter().convertDistance(arduinoInput.toString());
            if (!Double.isNaN(temp)) {
                normaliseValue(temp);
            }
            arduinoInput = new StringBuilder();
        } else {
            arduinoInput.append(s);
        }
    }

    private void normaliseValue(double value) {
        currentSensorValue = filter.filterValue(value);
    }

    private DistanceSensorImpl() {
        filter = new LowPassFilter(FILTER_WEIGHT);
        arduinoInput = new StringBuilder();
        currentSensorValue = 0.3;

        arduinoCommunicator = ArduinoCommunicator.getInstance();
        arduinoCommunicator.addArduinoListener(this::outputString);
    }
}

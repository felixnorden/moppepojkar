package core.sensors;

import arduino.ArduinoCommunicator;
import core.process_runner.InputSubscriber;
import sensor_data_conversion.SensorDataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    private List<Consumer<Double>> dataConsumers;

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

    public void subscribe(Consumer<Double> dataConsumer) {
        dataConsumers.add(dataConsumer);
    }

    public void unsubscribe(Consumer<Double> dataConsumer) {
        dataConsumers.remove(dataConsumer);
    }

    @Override
    public void kill() {

    }

    @Override
    public synchronized void outputString(String s) {
        for (char c : s.toCharArray()) {
            if (c != 10 && c != 13) {
                arduinoInput.append(c);
            } else {
                setAsDistanceValue(arduinoInput.toString());
                arduinoInput = new StringBuilder();
            }
        }
    }

    private void setAsDistanceValue(String text) {
        double value = new SensorDataConverter().convertDistance(text);
        if (!Double.isNaN(value)) {
            currentSensorValue = filter.filterValue(value);
            dataConsumers.forEach(doubleConsumer -> doubleConsumer.accept(currentSensorValue));
        }
    }

    private DistanceSensorImpl() {
        dataConsumers = new ArrayList<>();
        filter = new LowPassFilter(FILTER_WEIGHT);
        arduinoInput = new StringBuilder();
        currentSensorValue = 0.3;

        arduinoCommunicator = ArduinoCommunicator.getInstance();
        arduinoCommunicator.addArduinoListener(this::outputString);
    }
}

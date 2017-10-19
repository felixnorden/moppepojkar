package core.sensors;

import arduino.ArduinoCommunicator;
import com_io.CommunicationsMediator;
import com_io.Direction;
import utils.Config;
import utils.StrToDoubleConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static utils.Config.CAM_TGT_DIST;
import static utils.Config.DIST_SENSOR;
import static utils.Config.REGEX;

/**
 * Used for reading from the on-board distance sensor.
 */
public class DistanceSensorImpl implements DistanceSensor {


    private final ArduinoCommunicator arduinoCommunicator;


    private Filter filter;
    private double currentSensorValue;
    private StringBuilder arduinoInput;
    private StringBuilder cvInput;

    private List<Consumer<Double>> dataConsumers;

    @Override
    public double getDistance() {
        return currentSensorValue;
    }

    @Override
    public double getValue() {
        return getDistance();
    }

    @Override
    public void subscribe(Consumer<Double> dataConsumer) {
        dataConsumers.add(dataConsumer);
    }

    @Override
    public void unsubscribe(Consumer<Double> dataConsumer) {
        dataConsumers.remove(dataConsumer);
    }

    synchronized void receivedString(String string, StringBuilder sb) {
        for (char c : string.toCharArray()) {
            if (c != 10 && c != 13) {
                sb.append(c);
            } else {
                setCurrentSensorValue(sb.toString());
                sb.delete(0, sb.length());
            }
        }
    }

    private void setCurrentSensorValue(String text) {
        double value = new StrToDoubleConverter().convertStringToDouble(text);
        if (!Double.isNaN(value)) {
            currentSensorValue = filter.filterValue(value);
            dataConsumers.forEach(doubleConsumer -> doubleConsumer.accept(currentSensorValue));
        }
    }

    DistanceSensorImpl(CommunicationsMediator communicationsMediator,
                       ArduinoCommunicator arduinoCommunicator,
                       Filter filter) {
        dataConsumers = new ArrayList<>();
        this.filter = filter;

        arduinoInput = new StringBuilder();
        cvInput = new StringBuilder();
        currentSensorValue = 0.3;

        dataConsumers.add(sensorValue -> communicationsMediator.transmitData(DIST_SENSOR + REGEX + sensorValue.toString(), Direction.EXTERNAL));

        communicationsMediator.subscribe(Direction.INTERNAL, data -> {
            String[] formattedData = data.split(REGEX);
            if (formattedData.length == 2 && formattedData[0].equals(CAM_TGT_DIST)) {
                receivedString(formattedData[1], cvInput);
            }
        });
        this.arduinoCommunicator = arduinoCommunicator;
        this.arduinoCommunicator.addArduinoListener(string -> receivedString(string, arduinoInput));
    }
}

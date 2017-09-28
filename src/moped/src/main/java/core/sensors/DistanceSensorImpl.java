package core.sensors;

import core.process_runner.InputSubscriber;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;
import sensor_data_conversion.SensorDataConverter;

import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Double.NaN;

/**
 * Used for reading from the on-board distance sensor.
 */
public class DistanceSensorImpl implements DistanceSensor, InputSubscriber {

    private static final double FILTER_WEIGHT = 0.7;
    private static final DistanceSensorImpl INSTANCE = new DistanceSensorImpl();

    private LowPassFilter filter;
    private double currentSensorValue;
    private StringBuilder pythonInput;
    private Thread valueLoop;

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
        valueLoop.interrupt();
    }

    @Override
    public synchronized void outputString(String s) {
        if (s.contains("\n")) {
            double temp = new SensorDataConverter().convertDistance(pythonInput.toString());
            if (temp != NaN) {
                normaliseValue(temp);
            }
            pythonInput = new StringBuilder();
        } else {
            pythonInput.append(s);
        }
    }

    private void normaliseValue(double value) {
        currentSensorValue = filter.filterValue(value);
    }

    private DistanceSensorImpl() {
        filter = new LowPassFilter(FILTER_WEIGHT);
        pythonInput = new StringBuilder();
        currentSensorValue = 0.3;

        valueLoop = new Thread(() -> {
            ProcessRunner sensorData = null;
            try {
                sensorData = ProcessFactory.createPythonProcess("run.py");
                sensorData.start();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Couldn't start sensor script");
            }
            
            sensorData.subscribeToInput(this);

            while (!Thread.interrupted()) {
                try {
                    sensorData.outputToScript("g.can_ultra\n");
                    sensorData.flushOutput();
                } catch (IOException io) {
                    System.out.println("WRITE ERROR");
                    System.out.println("\t" + io.getMessage());
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
            }
        });
        valueLoop.start();
    }
}

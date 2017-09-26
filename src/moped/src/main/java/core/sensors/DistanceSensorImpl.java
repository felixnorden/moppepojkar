package core.sensors;

import core.process_runner.InputSubscriber;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;
import sensordataconversion.SensorDataConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Double.NaN;

/**
 * Used for reading from the on-board distance sensor.
 */
public class DistanceSensorImpl implements DistanceSensor, InputSubscriber {
    private StringBuilder dynamicPythonInput;

    private double lastValue;
    private Thread valueLoop;
    private boolean valueLoopIsKilled;

    private static DistanceSensorImpl ourInstance = new DistanceSensorImpl();
    public static DistanceSensorImpl getInstance() {
        return ourInstance;
    }
    @Override
    public double getDistance() {
        return lastValue;
    }

    @Override
    public double getValue() {
        return getDistance();
    }

    @Override
    public void kill() {
        valueLoopIsKilled = true;
    }

    @Override
    public synchronized void outputString(String s) {
        String[] lineNewSplit = s.split("\n");
        dynamicPythonInput.append(lineNewSplit[0]);

        if (s.contains("\n")) {
            double temp = new SensorDataConverter().convertDistance(dynamicPythonInput.toString());
            if (temp != NaN) {
                lastValue = temp;
            }

            String[] leftOverLines = Arrays.copyOfRange(lineNewSplit,1, lineNewSplit.length);
            for (String leftOverLine : leftOverLines) {
                outputString(leftOverLine);
            }
        }
    }

    private void processPythonInput(String s) {

    }

    private DistanceSensorImpl() {
        valueLoopIsKilled = false;
        dynamicPythonInput = new StringBuilder();
        lastValue = 100;

        valueLoop = new Thread(() -> {
            ProcessRunner sensorData = null;
            try {
                sensorData = ProcessFactory.createPythonProcess("run.py");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Couldn't start sensor script");
            }

            sensorData.subscribeToInput(this);

            while (!valueLoopIsKilled) {
                try {
                    sensorData.outputToScript("g.can_ultra\n");
                    sensorData.flushOutput();
                } catch (IOException ignored) {
                    break;
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });
        valueLoop.start();
    }
}

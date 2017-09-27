package core.sensors;

import core.process_runner.InputSubscriber;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Used for reading from the on-board distance sensor.
 */
public class DistanceSensorImpl implements DistanceSensor, InputSubscriber {
    private double lastValue;
    private Thread valueLoop;

    private static DistanceSensorImpl ourInstance = new DistanceSensorImpl();
    public static DistanceSensorImpl getInstance() {
        return ourInstance;
    }
    private DistanceSensorImpl() {
        lastValue = 100;

        valueLoop = new Thread(() -> {
            ProcessRunner sensorData = null;
            try {
                // TODO: 20/09/2017 Call the right pythonscript to access distance data
                sensorData = ProcessFactory.createPythonProcess("");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Couldn't start sensor script");
            }

            sensorData.subscribeToInput(this);

            while (true) {
                try {
                    // TODO: 20/09/2017 Use right command to receive data
                    sensorData.outputToScript("");
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
        //valueLoop.start();
    }

    @Override
    public void outputString(String s) {
        if (s.length() == 3) {
            lastValue = Integer.valueOf(s);
        }
    }

    @Override
    public double getDistance() {
        return lastValue;
    }
}

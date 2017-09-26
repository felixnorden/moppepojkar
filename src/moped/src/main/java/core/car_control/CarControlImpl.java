package core.car_control;

import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Used for controlling a MOPED through a python script found on the device.
 * <p>
 * Python commands used are:
 * drive(value)
 * steer(value)
 */
public class CarControlImpl implements CarControl {
    private int currentThrottleValue;
    private int currentSteerValue;

    private ProcessRunner carControl;

    /**
     * @param pathToPythonScript Absolute or relative path to the python script used for controlling the car.
     */
    public CarControlImpl(String pathToPythonScript) {
        try {
            carControl = ProcessFactory.createPythonProcess(pathToPythonScript);
            carControl.start();
            Thread.sleep(3000);     //Waits for process to start
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

        currentThrottleValue = 0;
        currentSteerValue = 0;

        Thread vcuLimiter = new Thread(() -> {
            double lastWrittenThrottleValue = currentThrottleValue;
            double lastWrittenSteerValue = currentSteerValue;

            while (true) {
                if (currentThrottleValue != lastWrittenThrottleValue ||
                        currentSteerValue != lastWrittenSteerValue) {
                    sendValuesToCar();
                    lastWrittenThrottleValue = currentThrottleValue;
                    lastWrittenSteerValue = currentSteerValue;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        vcuLimiter.start();

        writeToPythonScript("g.limitspeed=None");
    }

    @Override
    public int getLastThrottle() {
        return currentThrottleValue;
    }

    @Override
    public int getLastSteer() {
        return currentSteerValue;
    }

    @Override
    public synchronized void setThrottle(int value) {
        if (value != currentThrottleValue) {
            currentThrottleValue = constrainInVCURange(value);
        }
    }

    @Override
    public synchronized void setSteerValue(int value) {
        if (value != currentSteerValue) {
            currentSteerValue = constrainInVCURange(value);
        }
    }

    @Override
    public synchronized void addThrottle(int value) {
        setThrottle(currentThrottleValue + value);
    }

    @Override
    public synchronized void addSteer(int value) {
        setSteerValue(currentSteerValue + value);
    }

    /**
     * Sends the steering and throttle values to the python script.
     */
    private void sendValuesToCar() {
        writeToPythonScript("drive(" + currentThrottleValue + ")\n" +
                                 "steer(" + currentSteerValue + ")");
    }

    /**
     * Writes the String text to the python script.
     *
     * @param text text to be sent.
     */
    private void writeToPythonScript(String text) {
        if (carControl != null) {
            try {
                carControl.outputToScript(text + "\n");
                carControl.flushOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Constrains a value to the range used by the VCU. [-100,100]
     *
     * @param value value to be constrained
     * @return the constrained value.
     */
    private int constrainInVCURange(int value) {
        return constrainInRange(value, -100, 100);
    }

    /**
     * Constrains a value to the selected range.
     *
     * @param value Value to be constrained.
     * @param min   Minimum allowed value.
     * @param max   Maximum allowed value.
     * @return The constrained value.
     */
    private int constrainInRange(int value, int min, int max) {
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }

        return value;
    }
}

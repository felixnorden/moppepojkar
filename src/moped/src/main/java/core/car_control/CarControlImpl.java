package core.car_control;

import arduino.ArduinoCommunicator;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
    private ArduinoCommunicator arduinoCommunicator;

    /**
     * @param pathToPythonScript Absolute or relative path to the python script used for controlling the car.
     */
    public CarControlImpl(String pathToPythonScript) {
        arduinoCommunicator = new ArduinoCommunicator();

        List<String> portNames = arduinoCommunicator.getAllPortNames();

        try {
            arduinoCommunicator.connect(portNames.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentThrottleValue = 0;
        currentSteerValue = 100;

        Thread vcuLimiter = new Thread(() -> {
            double lastWrittenThrottleValue = currentThrottleValue;
            double lastWrittenSteerValue = currentSteerValue;

            while (true) {
/*
                if (currentThrottleValue != lastWrittenThrottleValue ||
                        currentSteerValue != lastWrittenSteerValue) {
                    sendValuesToCar();
                    lastWrittenThrottleValue = currentThrottleValue;
                    lastWrittenSteerValue = currentSteerValue;
                }
*/

                sendValuesToCar();


                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        vcuLimiter.start();
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
        arduinoCommunicator.write((byte) -128);
        arduinoCommunicator.write((byte) (128 + currentSteerValue));
        arduinoCommunicator.write((byte) -127);
        arduinoCommunicator.write((byte) (128 + currentThrottleValue));
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

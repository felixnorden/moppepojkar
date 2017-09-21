package core.car_control;

import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Used for controlling a MOPED through a python script found on the device.
 *
 * Python commands used are:
 *      drive(value)
 *      steer(value)
 */
public class CarControlImpl implements CarControl {

    private int lastThrottleValue;
    private int lastSteerValue;

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


        writeToPythonScript("g.limitspeed=None");
    }

    @Override
    public int getLastThrottle() {
        return lastThrottleValue;
    }

    @Override
    public int getLastSteer() {
        return lastSteerValue;
    }

    @Override
    public void setThrottle(int value) {
        lastThrottleValue = constrainInVCURange(value);
        sendValuesToCar();
    }

    @Override
    public void setSteerValue(int value) {
        lastSteerValue = constrainInVCURange(value);
        sendValuesToCar();
    }

    @Override
    public void addThrottle(int value) {
        setThrottle(lastThrottleValue + value);
    }

    @Override
    public void addSteer(int value) {
        setSteerValue(lastSteerValue + value);
    }

    /**
     * Sends the steering and throttle values to the python script.
     */
    private void sendValuesToCar() {
        writeToPythonScript("drive(" + lastThrottleValue + ")");
        writeToPythonScript("steer(" + lastSteerValue + ")");
    }

    /**
     * Writes the String text to the python script.
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
     * @param value value to be constrained
     * @return the constrained value.
     */
    private int constrainInVCURange(int value) {
        return constrainInRange(value, -100, 100);
    }

    /**
     * Constrains a value to the selected range.
     * @param value Value to be constrained.
     * @param min Minimum allowed value.
     * @param max Maximum allowed value.
     * @return The constrained value.
     */
    private int constrainInRange(int value, int min, int max) {
        if (value < min) {
            value = min;
        }
        else if (value > max) {
            value = max;
        }

        return value;
    }
}

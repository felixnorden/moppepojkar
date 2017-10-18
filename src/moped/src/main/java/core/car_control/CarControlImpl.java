package core.car_control;

import arduino.ArduinoCommunicatorImpl;
import com_io.CommunicationsMediator;
import com_io.Direction;

import static utils.Config.*;

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
    private final CommunicationsMediator communicationsMediator;

    private ArduinoCommunicatorImpl arduinoCommunicatorImpl;

    public CarControlImpl(CommunicationsMediator communicationsMediator) {
        // TODO: 10/10/2017 Inject interface for ArduinoCommunicatorImpl
        arduinoCommunicatorImpl = ArduinoCommunicatorImpl.getInstance();

        this.communicationsMediator = communicationsMediator;

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
                sendValuesToCar();
                transmitValuesToSubscribers();
                try {
                    Thread.sleep(150);
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
            currentThrottleValue = constrainInRange(value, MIN_SPEED, MAX_SPEED);
        }
    }

    @Override
    public synchronized void setSteerValue(int value) {
        if (value != currentSteerValue) {
            currentSteerValue = constrainInRange(value,MIN_STEER,MAX_STEER);
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
        arduinoCommunicatorImpl.write((byte) 0);
        arduinoCommunicatorImpl.write((byte) (128 + currentSteerValue));
        arduinoCommunicatorImpl.write((byte) 1);
        arduinoCommunicatorImpl.write((byte) (128 + currentThrottleValue));
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

    /**
     * Transmits the throttle and steering values
     * to all subscribers in {@link CommunicationsMediator}
     */
    private void transmitValuesToSubscribers() {
        String throttle = THROTTLE + REGEX + currentThrottleValue;
        String steer = STEER + REGEX + currentSteerValue;
        communicationsMediator.transmitData(throttle, Direction.EXTERNAL);
        communicationsMediator.transmitData(steer, Direction.EXTERNAL);
    }
}

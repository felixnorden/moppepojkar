package core.car_control;

public class CarControlImpl implements CarControl {

    private int lastThrottleValue;
    private int lastSteerValue;

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
        // TODO: 20/09/2017 set throttle value to python process
    }

    @Override
    public void setSteerValue(int value) {
        lastSteerValue = constrainInVCURange(value);
        // TODO: 20/09/2017 set steer value to python process
    }

    @Override
    public void addThrottle(int value) {
        setThrottle(lastThrottleValue + value);
    }

    @Override
    public void addSteer(int value) {
        setSteerValue(lastSteerValue + value);
    }

    private int constrainInVCURange(int value) {
        return constrainInRange(value, -100, 100);
    }
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

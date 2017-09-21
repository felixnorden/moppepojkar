package core.car_control;

public interface CarControl {

    int getLastThrottle();
    int getSpeedVaue();

    void setThrottle(int value);
    void setSteerValue(int value);
}

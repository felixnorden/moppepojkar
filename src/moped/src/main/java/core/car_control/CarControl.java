package core.car_control;

public interface CarControl {

    int getLastThrottle();
    int getLastSteer();

    void setThrottle(int value);
    void setSteerValue(int value);

    void addThrottle(int value);
    void addSteer(int value);
}

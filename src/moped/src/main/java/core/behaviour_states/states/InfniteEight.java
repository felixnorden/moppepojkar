package core.behaviour_states.states;

import core.car_control.CarControl;
import core.car_control.CarControlImpl;

/**
 * Created by Eddie and Anton 2017-09-21.
 */
public class InfniteEight implements BehaviourState {

    private CarControl cc = new CarControlImpl("run.py");

    public InfniteEight(){
        cc.setSteerValue(100);
        cc.setThrottle(10);
    }

    @Override
    public void run() {
        System.out.println("Running in circles!");
    }
}

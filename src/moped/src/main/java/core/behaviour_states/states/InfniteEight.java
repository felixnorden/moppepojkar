package core.behaviour_states.states;

import com_io.CommunicatorFactory;
import core.car_control.CarControl;
import core.car_control.CarControlImpl;

/**
 * Created by Eddie and Anton 2017-09-21.
 */
public class InfniteEight implements BehaviourState {

    private CarControl cc = new CarControlImpl(CommunicatorFactory.getComInstance());

    private int steer = 100;

    public InfniteEight(){
        System.out.println("Running in circles!");
    }

    @Override
    public void run() {
        cc.setSteerValue(steer *= -1);
        cc.setThrottle(25);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

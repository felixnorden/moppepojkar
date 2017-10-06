

package core.behaviour_states;

import com_io.CommunicatorFactoryImpl;
import com_io.DataReceiver;
import com_io.Direction;
import core.behaviour_states.states.BehaviourState;
import core.behaviour_states.states.BehaviourStateFactory;
import core.behaviour_states.states.BehaviourStateFactoryImpl;

/**
 * Controller which controls the different states of the system
 */
public class StateController implements Runnable, DataReceiver{
    private BehaviourStateFactory stateFactory;
    private BehaviourState currentState;

    private BehaviourState acc;
    private final BehaviourState manual;

    public StateController() {
        this.stateFactory = BehaviourStateFactoryImpl.getInstance();

        CommunicatorFactoryImpl.getFactoryInstance().getComInstance().subscribe(Direction.INTERNAL, this);
        // Possibly change to default safe mode when implemented
        this.acc = stateFactory.createPlatooningBehaviour();
        this.manual = stateFactory.createManualBehaviour();

        this.currentState = manual;
    }

//    /**
//     *
//     * @return current behaviour of the MOPED
//     */
//    public BehaviourState getCurrentState() {
//        return this.currentState;
//    }
//
//    /**
//     *
//     * @param newState the new current state for the MOPED
//     */
//    public void setNewState(BehaviourState newState) {
//        this.currentState = newState;
//    }

    @Override
    public void run() {
        currentState.run();
    }

    @Override
    public void dataReceived(String unformattedData) {
        String[] data = unformattedData.split(",");



        if (data[0].equals("STATE")) {
            if (data[1].equals("ACC")) {
                System.out.println("ACC ENABLED");
                currentState = acc;
            } else if (data[1].equals("MANUAL")) {
                currentState = manual;
            }
        }
    }
}

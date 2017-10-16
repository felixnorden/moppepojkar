

package core.behaviour_states;

import com_io.CommunicatorFactory;
import com_io.DataReceiver;
import com_io.Direction;
import core.behaviour_states.states.BehaviourState;
import core.behaviour_states.states.BehaviourStateFactory;
import core.behaviour_states.states.BehaviourStateFactoryImpl;

import static utils.Config.*;

/**
 * Controller which controls the different states of the system
 */
public class StateController implements Runnable, DataReceiver {
    private BehaviourStateFactory stateFactory;
    private BehaviourState currentState;

    private final BehaviourState manual;
    private final BehaviourState acc;
    private final BehaviourState platooning;
    private final BehaviourState safeMode;

    public StateController() {
        this.stateFactory = BehaviourStateFactoryImpl.getInstance();
        CommunicatorFactory.getComInstance().subscribe(Direction.INTERNAL, this);

        this.manual = stateFactory.createManualBehaviour();
        this.acc = stateFactory.createAdaptiveCruiseControlBehaviour();
        this.platooning = stateFactory.createPlatooningBehaviour();

        this.safeMode = stateFactory.createSafeModeBehaviour();
        stateFactory.createEmergencyStop(this::setState);

        this.currentState = safeMode;
    }

    @Override
    public synchronized void run() {
        currentState.run();
    }

    private synchronized void setState(BehaviourState state) {
        currentState = state;
    }

    @Override
    public synchronized void dataReceived(String unformattedData) {
        String[] data = unformattedData.split(REGEX);

        if (data[0].equals(STATE)) {
            switch (data[1]) {
                case "ACC":
                    currentState = acc;
                    break;
                case "MANUAL":
                    currentState = manual;
                    break;
                case "PLATOONING":
                    currentState = platooning;
                    break;
                default:
            }
        } else if (data[0].equals(CONNECTION)) {
            if (data[1].equals(OFF)) {
                currentState = safeMode;
            }
        }
    }
}

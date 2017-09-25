package core.action_strategies;

import com_io.CommunicationsMediator;
import com_io.DataReceiver;
import com_io.Direction;

/**
 * An {@link ActionStrategy} that handles the incoming navigational
 * requests from a remote controller in a specific axis.
 */
public class RemoteController implements ActionStrategy, DataReceiver {

    private final Axis axis;

    private double lastValue;

    /**
     * Constructs a remote controller with the given axis filter
     * @param axis the preferred axis of X and Y for navigation
     * @param comIO the communications socket of the system
     */
    public RemoteController(Axis axis, CommunicationsMediator comIO) {
        this.axis = axis;
        comIO.subscribe(this, Direction.INTERNAL);
    }

    @Override
    public synchronized double takeAction() {
        return 0;
    }

    @Override
    public synchronized void dataReceived(int[] data, Direction direction) {
        
    }


    /**
     * The filter used to filter out unwanted data
     */
    public enum Axis {
        X,
        Y
    }

}


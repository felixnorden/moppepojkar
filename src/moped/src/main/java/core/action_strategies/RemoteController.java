package core.action_strategies;

import com_io.CommunicationsMediator;
import com_io.DataReceiver;
import com_io.Direction;

import static core.action_strategies.RemoteController.Axis.*;

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
        comIO.subscribe(Direction.INTERNAL, this);
    }

    @Override
    public synchronized double takeAction() {
        return lastValue;
    }

    @Override
    public void dataReceived(String unformattedData) {
        String[] data = unformattedData.split("|");

        if (data[0].equals("THROTTLE")) {
            if (axis == Y) {
                lastValue = Integer.valueOf(data[1]);
            }
        } else if (data[0].equals("STEER")) {
            if (axis == X) {
                lastValue = Integer.valueOf(data[1]);
            }
        }
    }


    /**
     * The filter used to filter out unwanted data
     */
    public enum Axis {
        X,
        Y
    }

}


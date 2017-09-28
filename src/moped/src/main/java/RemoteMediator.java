import com.*;
import com_io.CommunicationsMediator;
import com_io.DataReceiver;
import com_io.Direction;

public class RemoteMediator implements DataReceiver, CommunicationListener {

    private final Communicator server;
    private final CommunicationsMediator communicationsMediator;

    public RemoteMediator(int port, CommunicationsMediator communicationsMediator) {
        server = new ServerCommunicator(port);
        server.addListener(this);
        server.start();

        this.communicationsMediator = communicationsMediator;
        communicationsMediator.subscribe(Direction.EXTERNAL, this);
    }

    @Override
    public void onConnection() {
        // TODO: 2017-09-27 UPDATE APP WITH CURRENT VALUES PLS DO
    }

    @Override
    public void onStateChange(MopedState mopedState) {
        communicationsMediator.transmitData("STATE," + mopedState.toString(), Direction.INTERNAL);
    }

    @Override
    public void onDisconnection() {
        // TODO: 2017-09-27 STOPCAR STOPCAR
    }

    @Override
    public void onValueChanged(MopedDataType mopedDataType, int i) {
        //Nothing more will most likely be added here as these values are meant to be sent to the app for display
        //and not the other way around
        switch (mopedDataType) {
            case VELOCITY:
                break;
            case SENSOR_DISTANCE:
                break;
            case PID_TARGET_VALUE:
                break;
            case PID_P_CONSTANT:
                break;
            case PID_Y_CONSTANT:
                break;
            case PID_D_CONSTANT:
                break;
            case PID_INTEGRAL_SUM:
                break;
            case THROTTLE:
                communicationsMediator.transmitData("THROTTLE," + i, Direction.INTERNAL);
                break;
            case STEERING:
                communicationsMediator.transmitData("STEER," + i, Direction.INTERNAL);
                break;
            case CUSTOM_1:
                break;
            case CUSTOM_2:
                break;
            case CUSTOM_3:
                break;
        }
    }

    /**
     * Sets logging to true or false on the server communicator.
     *
     * @param enable
     */
    public void setDebugLogging(boolean enable) {
        if (enable) {
            server.enableLogging();
        } else {
            server.disableLogging();
        }
    }

    @Override
    public void dataReceived(String unformattedDataString) {
        String[] data = unformattedDataString.split(",");

        if (data.length == 2) {
            if (data[0].equals("STATE")) {
                try {
                    MopedState mopedState = MopedState.valueOf(data[1]);
                    server.setState(mopedState);
                } catch (IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                }

            } else {
                MopedDataType mopedDataType = MopedDataType.valueOf(data[1]);
                // TODO: 27/09/2017 Do stuff with the data type
            }
        }
    }
}

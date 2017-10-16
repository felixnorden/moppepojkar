import com.*;
import com_io.CommunicationsMediator;
import com_io.DataReceiver;

import static com_io.Direction.EXTERNAL;
import static com_io.Direction.INTERNAL;
import static utils.Config.*;

public class RemoteMediator implements DataReceiver, CommunicationListener {

    private final Communicator server;
    private final CommunicationsMediator communicationsMediator;

    public RemoteMediator(int port, CommunicationsMediator communicationsMediator) {
        server = new ServerCommunicator(port);
        server.addListener(this);
        server.start();

        this.communicationsMediator = communicationsMediator;
        communicationsMediator.subscribe(EXTERNAL, this);
    }

    @Override
    public void onConnection() {
        System.out.println("Phone connected!");
        communicationsMediator.transmitData(CONNECTION + REGEX + ON, INTERNAL);
    }

    @Override
    public void onStateChange(MopedState mopedState) {
        communicationsMediator.transmitData(STATE + REGEX + mopedState.toString(), INTERNAL);
    }

    @Override
    public void onDisconnection() {
        System.out.println("Connection lost!");
        communicationsMediator.transmitData(CONNECTION + REGEX + OFF, INTERNAL);
    }

    @Override
    public void onValueChanged(MopedDataType mopedDataType, int i) {
        //Nothing more will most likely be added here as these values are meant to be sent to the app for display
        //and not the other way around
        switch (mopedDataType) {
            case THROTTLE:
                communicationsMediator.transmitData(THROTTLE + REGEX + i, INTERNAL);
                break;
            case STEERING:
                communicationsMediator.transmitData(STEER + REGEX + i, INTERNAL);
                break;
            default:
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
        // TODO: 12/10/2017 Fix string to enum issue
        if (true) {
            return;
        }

        String[] data = unformattedDataString.split(REGEX);

        if (data.length == 2) {
            if (data[0].equals(STATE)) {
                try {
                    MopedState mopedState = MopedState.valueOf(data[1]);
                    server.setState(mopedState);
                } catch (IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                }

            } else {
                MopedDataType mopedDataType = MopedDataType.valueOf(data[0]);
                int value = Integer.valueOf(data[1]);
                server.setValue(mopedDataType, value);
            }
        }
    }
}

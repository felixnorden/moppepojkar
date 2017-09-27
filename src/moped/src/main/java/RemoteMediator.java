import com.*;
import com_io.DataReceiver;
import com_io.Direction;

public class RemoteMediator implements DataReceiver, CommunicationListener {

    private final Communicator server;

    public RemoteMediator(int port) {
        server = new ServerCommunicator(port);
        server.addListener(this);
        server.start();
    }

    @Override
    public void onConnection() {
        // TODO: 2017-09-27 UPDATE APP WITH CURRENT VALUES PLS DO
    }

    @Override
    public void onStateChange(MopedState mopedState) {
        // TODO: 2017-09-27 DO YOUR SHIT HERE PID-SQUAD
        switch (mopedState) {
            case ACC:
                break;
            case MANUAL:
                break;
        }
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
            case CUSTOM_1:
                break;
            case CUSTOM_2:
                break;
            case CUSTOM_3:
                break;
        }
    }

    /**
     * Sets logging to true or false on the servercommunicator.
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
    public void dataReceived(int[] data, Direction direction) {
        
    }
}

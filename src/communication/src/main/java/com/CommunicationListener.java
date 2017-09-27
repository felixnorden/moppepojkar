package com;

/**
 * Interface for receiving events from a Communicator
 */
public interface CommunicationListener {

    /**
     * This method gets called as soon as a communicator connects.
     */
    void onConnection();

    /**
     * This method gets called as soon as a state change is received through the communicator.
     *
     * @param stateChange New state which communicator received.
     */
    void onStateChange(MopedState stateChange);

    /**
     * This method get called as soon as a current connection is lost.
     * This method doesn't get called when stop() is called on the same unit.
     */
    void onDisconnection();

    /**
     * This method gets called as soon as a new value is received through the communicator.
     * @param type The type of data received (i.e. VELOCITY)
     * @param value The value of the data.
     */
    void onValueChanged(MopedDataType type, int value);
}
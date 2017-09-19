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
}

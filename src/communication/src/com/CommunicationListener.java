package com;

/**
 * Interface for receiving events from a Communicator
 */
public interface CommunicationListener {

    /**
     * This method gets called as soon as a communicator connects.
     */
    void onConnection();
}

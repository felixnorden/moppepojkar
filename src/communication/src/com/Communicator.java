package com;


/**
 * Interface for interacting with a Communicator.
 */
public interface Communicator extends Runnable {
    /**
     * Adds a CommunicationListener to the list of listeners.
     *
     * @param cl CommunicationListener to be added.
     */
    void addListener(CommunicationListener cl);

    /**
     * Starts a Communicator.
     */
    void start();

    void setState(MopedStates state);
}
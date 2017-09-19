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
     * Starts a Communicator in a new thread. Must be called for communicator to start
     * communicating.
     * If the queue contains items, the queue will be cleared.
     */
    void start();

    /**
     * Stops the Communicator from communicating. Use start() to start communicating once again.
     * DOESN'T CLEAR THE QUEUE.
     */
    void stop();

    void setState(MopedState state);
}
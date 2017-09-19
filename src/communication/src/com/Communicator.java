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
     * If data is queued to be sent and stop is called, the queue doesn't get cleared.
     * Queue does get cleared in start() though.
     */
    void stop();

    void setState(MopedState state);
}
package main.java;


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
     * A communicator may only be started/stopped once.
     */
    void start();

    /**
     * Stops the Communicator from communicating. Use start() to start communicating once again.
     * If data is queued to be sent and stop is called, the queue doesn't get cleared.
     * Queue does get cleared in start() though.
     * A communicator may only be started/stopped once.
     */
    void stop();

    /**
     * Adds the new state to the queue of data to be sent to another communicator.
     * Will call onStateChange(MopedState state) on the CommunicationListeners registered to the other communicator.
     *
     * @param state The state to add to the queue.
     */
    void setState(MopedState state);
}
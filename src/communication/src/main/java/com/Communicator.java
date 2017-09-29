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

    /**
     * Adds a new value of given type to the queue of data to be sent to another communicator.
     * Will call onValueChanged(MopedDataType type, int Value) on the CommunicationListeners registered to the
     * other communicator
     *
     * @param type  The type of data to be added
     * @param value The value of the data
     */
    void setValue(MopedDataType type, int value);

    /**
     * @return True if logging is enabled, false otherwise.
     */
    boolean isLoggingEnabled();

    /**
     * Enables logging for the communicator
     */
    void enableLogging();

    /**
     * Disables logging for the communicator
     */
    void disableLogging();
}
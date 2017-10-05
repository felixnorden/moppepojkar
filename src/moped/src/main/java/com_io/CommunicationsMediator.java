package com_io;

/**
 * Interface for sending data both internally and externally of
 * the core module
 */
public interface CommunicationsMediator {

    /**
     * Sends a data string in the in the specific direction,
     * internally or externally
     * @param data the data to send
     * @param direction the direction the data will be sent to
     */
    void transmitData(String data, Direction direction);
    void subscribe(Direction direction, DataReceiver receiver);
    void unsubscribe(Direction direction, DataReceiver receiver);

}

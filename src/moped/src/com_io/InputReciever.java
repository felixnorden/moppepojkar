package com_io;

/**
 * Interface for an InputReceiver that listens for data input.
 */
public interface InputReciever {
    /**
     * Sends data to the InputReciever
     * @param data Integer data to be sent
     */
    void inputData(int data);
}

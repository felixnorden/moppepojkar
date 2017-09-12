package com_protocols;

/**
 * Interface for an InputReceiver that listen to data inputted.
 */
public interface InputReciever {
    /**
     * Sends data to the InputReciever
     * @param data Integer data to be sent
     */
    void inputData(int data);
}

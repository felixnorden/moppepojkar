package com_io;

/**
 * Interface for observers that listen for data outputs.
 */
public interface OutputObserver {
    /**
     * Data that has been outputted to the OutputObserver.
     * @param data Outputted data
     */
    void dataOutputted(int data);
}

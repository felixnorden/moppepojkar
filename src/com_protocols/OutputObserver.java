package com_protocols;

/**
 * Interface for obervers that listen to data outputted.
 */
public interface OutputObserver {
    /**
     * Data that has been outputted to the OutputObserver.
     * @param data Outputted data
     */
    void dataOutputted(int data);
}

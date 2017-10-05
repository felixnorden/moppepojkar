package com_io;

/**
 * Interface for fetching the
 * {@link CommunicationsMediator} instance
 */
public interface CommunicatorFactory {

    /**
     * @return the {@link CommunicationsMediatorImpl}
     * instance
     */
    CommunicationsMediator getComInstance();

}

package com_io;

/**
 * Factory class for creating communication-related objects
 */
public class CommunicatorFactory {

    private static final CommunicationsMediator comInstance = new CommunicationsMediatorImpl();

    public static CommunicationsMediator getComInstance() {
         return comInstance;
     }
}

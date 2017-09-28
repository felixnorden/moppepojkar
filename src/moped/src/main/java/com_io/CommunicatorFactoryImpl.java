package com_io;

public class CommunicatorFactoryImpl implements CommunicatorFactory {

    private static final CommunicatorFactory INSTANCE = new CommunicatorFactoryImpl();
    private CommunicationsMediator comInstance;

    public static CommunicatorFactory getFactoryInstance() {
        return INSTANCE;
    }

     @Override
     public CommunicationsMediator getComInstance() {
         return comInstance;
     }

    private CommunicatorFactoryImpl() {
        comInstance = new CommunicationsMediatorImpl();
    }
}

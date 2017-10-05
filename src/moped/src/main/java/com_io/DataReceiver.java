package com_io;

/**
 * Interface which subscribers implement for receiving data
 * from the {@link CommunicationsMediator}
 */
public interface DataReceiver {

    public void dataReceived(String data);
}

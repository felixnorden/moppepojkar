package com;

import java.net.Socket;
import java.util.ArrayList;


/**
 * An abstracting class for the two different Communicators.
 * Contains basic functionality and utility.
 */
public abstract class AbstractCommunicator implements Communicator {

    protected final long UPDATE_INTERVAL = 10;
    protected final int port;
    protected Socket socket;
    protected Thread mainThread;
    private final ArrayList<CommunicationListener> listeners;

    /**
     * @param port Port for the socket to use.
     */
    protected AbstractCommunicator(int port) {
        this.port = port;
        listeners = new ArrayList<>();
    }

    @Override
    public void addListener(CommunicationListener cl) {
        listeners.add(cl);
    }

    @Override
    public void start() {
        mainThread.start();
    }

    /**
     * Notifies all listeners that a connection has been established.
     */
    protected void notifyConnected() {
        for (CommunicationListener cl : listeners) {
            cl.onConnection();
        }
    }
}

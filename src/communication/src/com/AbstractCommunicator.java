package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * An abstracting class for the two different Communicators.
 * Contains basic functionality and utility.
 */
public abstract class AbstractCommunicator implements Communicator {

    protected final long UPDATE_INTERVAL = 10;
    protected final int port;
    protected Socket socket;
    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;
    protected Thread mainThread;
    private Queue<MopedStates> queuedMopedStates;
    private final ArrayList<CommunicationListener> listeners;

    /**
     * @param port Port for the socket to use.
     */
    protected AbstractCommunicator(int port) {
        this.port = port;
        listeners = new ArrayList<>();
        queuedMopedStates = new LinkedList<MopedStates>();
    }

	@Override
	public void setState(MopedStates state) {
		queuedMopedStates.add(state);
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
	 * Fetches and sends new information from the connected socket.
	 */
	protected void update() {
		try {
			//Send all queued state changes through socket link
			while (queuedMopedStates.size() > 0) {
				outputStream.write(MopedStates.toInt(queuedMopedStates.poll()));
			}

			// Get all state changes from other sender in socket link
			int stateChange = inputStream.read();
			while (stateChange != -1) {
				notifyStateChange(MopedStates.parseInt(stateChange));
				stateChange = inputStream.read();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Notifies all listeners that a connection has been established.
     */
    protected void notifyConnected() {
        for (CommunicationListener cl : listeners) {
            cl.onConnection();
        }
    }

	/**
	 * Notifies all listeners that a state change from the sender has been received.
	 */
	protected void notifyStateChange(MopedStates mopedState) {
		for (CommunicationListener cl : listeners) {
			cl.onStateChange(mopedState);
		}
	}
}

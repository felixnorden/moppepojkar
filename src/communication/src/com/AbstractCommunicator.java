package com;

import javafx.util.Pair;

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
    private Queue<Pair<MopedDataType, Integer>> queue;
    private final ArrayList<CommunicationListener> listeners;

    /**
     * @param port Port for the socket to use.
     */
    protected AbstractCommunicator(int port) {
        this.port = port;
        listeners = new ArrayList<>();
        queue = new LinkedList<>();
    }

    @Override
    public void setState(MopedState state) {
        Pair<MopedDataType, Integer> p = new Pair(MopedDataType.MopedState, state.toInt());
        queue.add(p);
    }

    @Override
    public void addListener(CommunicationListener cl) {
        listeners.add(cl);
    }

    @Override
    public void start() {
        queue.clear();
        mainThread.start();
    }

    @Override
    public void stop() {
        mainThread.interrupt();
    }

    /**
     * Fetches and sends new information from the connected socket.
     */
    protected void update() {
        try {
            sendQueuedData();
            receiveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends all queued data through socket link
     *
     * @throws IOException
     */
    private void sendQueuedData() throws IOException {
        //Send all queued data
        while (queue.size() > 0) {
            Pair<MopedDataType, Integer> pair = queue.poll();

            String dataType = String.valueOf(pair.getKey().toInt());
            String value = String.valueOf(pair.getValue());

            //Format is 'x,y' where
            //  x = MopedDataType integer
            //  y = integer value of specified MopedDataType
            String output = dataType + "," + value;

            outputStream.writeUTF(output);
        }
    }

    /**
     * Read and interpret data from socket link.
     *
     * @throws IOException
     */
    private void receiveData() throws IOException {
        while (inputStream.available() > 0) {
            // Input string is formatted as "xxxxx,yyyy" where x is MopedDataType and y is a int value
            String input = inputStream.readUTF();
            String[] args = input.split(",");

            //Extract data from input.
            MopedDataType type = MopedDataType.parseInt(Integer.parseInt(args[0]));
            int value = Integer.parseInt(args[1]);

            handleInput(type, value);
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
    protected void notifyStateChange(MopedState mopedState) {
        for (CommunicationListener cl : listeners) {
            cl.onStateChange(mopedState);
        }
    }

    /**
     * Closes the socket, nullifies it and nullifies the in/output-streams.
     */
    protected void clearConnection() {
        try {
            socket.close();
            socket = null;
            inputStream = null;
            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleInput(MopedDataType type, int value) {
        switch (type) {
            case MopedState:
                notifyStateChange(MopedState.parseInt(value));
            default:
                // TODO: 2017-09-18 Notify value change
        }
    }
}

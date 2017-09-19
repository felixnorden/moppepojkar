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

    //Constants
    private static final String EXIT_CODE = "#EXIT";
    private static final String SEPARATOR = ",";
    protected final long UPDATE_INTERVAL = 10;//This essentially controls the input lag between app and MOPED

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

    /**
     * Mainloop which tries to maintain a connection to another communicator.
     * If a communicator is lost, it looks for another one till found.
     * <p>
     * When a connection is established, all listeners will be notified.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            // If there is no connection or if connection is broken.
            if (socket == null || !socket.isConnected()) {
                connectSocket();
            } else {
                update();
            }
            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                //Do nothing.
            }
        }

        //This section only runs when the thread is interrupted aka on stop().
        clearConnection();
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
        try {
            //Ignore queue and send exit code to other communicator, then exit.
            outputStream.writeUTF(EXIT_CODE);
            mainThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches and sends new information from the connected socket.
     */
    private void update() {
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
            String output = dataType + SEPARATOR + value;

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

            if (input.equals(EXIT_CODE)) {
                onDisconnect();
                break;
            }

            String[] args = input.split(SEPARATOR);

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
     * Notifies all listeners that a disconnection has occurred.
     */
    protected void notifyDisconnected() {
        for (CommunicationListener cl : listeners) {
            cl.onDisconnection();
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

    /**
     * Decides what happens to the received data once it has been converted into the objects it was converted from.
     * @param type Type of data received.
     * @param value The value of the type.
     */
    private void handleInput(MopedDataType type, int value) {
        switch (type) {
            case MopedState:
                //This means a state was changed. Extract new state from value and send to listeners.
                notifyStateChange(MopedState.parseInt(value));
            default:
                // TODO: 2017-09-18 Notify value change
        }
    }

    /**
     * Runs when other side has requested a disconnect by
     * sending the EXIT_CODE
     */
    private void onDisconnect() {
        mainThread.interrupt();
        notifyDisconnected();
    }

    /**
     * Does the necessary setup for the sockets to establish a connection.
     * This is necessary because one communicator needs to act as a server and the other one as a client.
     */
    protected abstract void connectSocket();
}

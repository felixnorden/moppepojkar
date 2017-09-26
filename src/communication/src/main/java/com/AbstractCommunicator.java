package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
    private static final long UPDATE_INTERVAL = 10;//This essentially controls the input lag between app and MOPED

    private boolean loggingEnabled = true;
    protected final int port;
    protected Socket socket;
    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;
    protected Thread mainThread;
    private Queue<MopedDataPair> queue;
    private final ArrayList<CommunicationListener> listeners;
    //This variable is true when a disconnect just happened and
    //it needs to be taken care of in the main loop. The main loop
    //will set this back to false when it has been handled.
    private volatile boolean hasDisconnected = false;


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
     * When a connection is established, all listeners will be notified.
     * If a connection is established, data will be sent/received.
     * If a communicator is lost, it looks for another one till found.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (hasDisconnected) {
                handleDisconnect();
                hasDisconnected = false;
                continue;
            }

            // If there is no connection or if connection is broken.
            if (socket == null || !socket.isConnected()) {
                //Try to connect to socket on port. If timed out, clear old connection and retry.
                try {
                    connectSocket();
                } catch (SocketTimeoutException e) {
                    clearConnection();
                    continue;
                }
            } else {
                update();
            }

            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                //If thread was interrupted while sleeping, break
                break;
            }
        }

        //Only runs after running has been set to false (aka onDisconnect and stop())
        sendExitCode();
        clearConnection();
        log(this.getClass().getName() + ": STOPPED");
    }

    /**
     * If connected to a communicator, send exit code to it.
     */
    private void sendExitCode() {
        try {
            if (outputStream != null) {
                //Send that communicator is exiting
                outputStream.writeUTF(EXIT_CODE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setState(MopedState state) {
        MopedDataPair p = new MopedDataPair(MopedDataType.MopedState, state.toInt());
        queue.add(p);
    }

    @Override
    public void addListener(CommunicationListener cl) {
        listeners.add(cl);
    }

    @Override
    public void start() {
        queue.clear();
        try {
            mainThread.start();
        } catch (IllegalThreadStateException e) {
            //If this is thrown, thread was already started once before.
            //First block is if thread is dead, aka it was killed.
            //Second block is if a start was tried while thread was alive
            if (!mainThread.isAlive()) {
                mainThread = new Thread(this);
                mainThread.start();
            } else {
                log(this.getClass().getName() + ": is already running.");
            }
        }
    }

    @Override
    public void stop() {
        log(getClass().getName() + ": Stopping (This can take up to 4 seconds)");
        mainThread.interrupt();
    }

    protected abstract void handleDisconnect();

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
            MopedDataPair mopedDataPair = queue.poll();

            String dataType = String.valueOf(mopedDataPair.getType().toInt());
            String value = String.valueOf(mopedDataPair.getValue());

            //Format is 'x,y' where
            //  x = MopedDataType integer
            //  y = integer value of specified MopedDataType
            String output = dataType + SEPARATOR + value;

            outputStream.writeUTF(output);
        }
    }

    public boolean isAlive() {
        return mainThread != null && mainThread.isAlive();
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
    void notifyConnected() {
        for (CommunicationListener cl : listeners) {
            cl.onConnection();
        }
    }

    /**
     * Notifies all listeners that a state change from the sender has been received.
     */
    private void notifyStateChange(MopedState mopedState) {
        for (CommunicationListener cl : listeners) {
            cl.onStateChange(mopedState);
        }
    }

    /**
     * Notifies all listeners that a disconnection has occurred.
     */
    private void notifyDisconnected() {
        for (CommunicationListener cl : listeners) {
            cl.onDisconnection();
        }
    }

    /**
     * Closes the socket, nullifies it and nullifies the in/output-streams.
     * This is necessary because one communicator acts as a server and the other one as a client.
     */
    protected abstract void clearConnection();

    /**
     * Decides what happens to the received data once it has been converted into the objects it was converted from.
     *
     * @param type  Type of data received.
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
        notifyDisconnected();
        hasDisconnected = true;
    }

    /**
     * Does the necessary setup for the sockets to establish a connection.
     * This is necessary because one communicator needs to act as a server and the other one as a client.
     */
    protected abstract void connectSocket() throws SocketTimeoutException;

    /**
     * Prints the given object in out.println and adds which thread printed it, but only if logging is enabled;
     *
     * @param object
     */
    protected void log(Object object) {
        if (loggingEnabled) {
            log("[" + Thread.currentThread().getName() + "] " + object.toString());
        }
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void enableLogging() {
        this.loggingEnabled = true;
    }

    public void disableLogging(){
        this.loggingEnabled = false;
    }
}

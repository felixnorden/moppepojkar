package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * An abstracting class for the two different Communicators.
 * Contains basic functionality and utility.
 */
public abstract class AbstractCommunicator implements Communicator {

    //Constants
    private static final String PING = "#P";
    private static final String EXIT_CODE = "#E";
    private static final String SEPARATOR = ",";
    private static final long UPDATE_INTERVAL = 10;//This essentially controls the input lag between app and MOPED

    private boolean loggingEnabled = true;
    protected final int port;
    protected Socket socket;
    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;
    protected Thread mainThread;
    private Queue<MopedDataPair> queue;
    private final List<CommunicationListener> listeners;
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
        queue = new ConcurrentLinkedQueue<>();

        mainThread = new Thread(this, getClass().getSimpleName());
    }

    /**
     * Mainloop which tries to maintain a connection to another communicator.
     * When a connection is established, all listeners will be notified.
     * If a connection is established, data will be sent/received.
     * If a communicator is lost, it looks for another one till found.
     */
    @Override
    public void run() {
        log("Started");
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
        log("Stopped");
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
            //Will be thrown if other communicator can't be reached. (Might happen on wifi-loss)
        }
    }

    @Override
    public void setState(MopedState state) {
        MopedDataPair p = new MopedDataPair(MopedDataType.MOPED_STATE, state.toInt());
        queue.add(p);
    }


    @Override
    public void setValue(MopedDataType type, int value) {
        MopedDataPair p = new MopedDataPair(type, value);
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
                mainThread = new Thread(this, getClass().getSimpleName());
                mainThread.start();
            } else {
                log("Thread " + mainThread.getName() + " is already running.");
            }
        }
    }

    @Override
    public void stop() {
        log("Stopping " + mainThread.getName() + " (This may take up to 4 seconds)");
        mainThread.interrupt();
    }

    protected abstract void handleDisconnect();

    /**
     * Fetches and sends new information from the connected socket.
     */
    private void update() {
        sendQueuedData();
        receiveData();
    }

    /**
     * Sends all queued data through socket link
     *
     * @throws IOException
     */
    private void sendQueuedData() {
        try {
            //Before sending (eventually) queued data, send a ping to see if the outputstream doesn't give an error
            outputStream.writeUTF(PING);
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
        } catch (IOException e) {
            onDisconnect();
        }
    }

    /**
     * Read and interpret data from socket link.
     *
     * @throws IOException
     */
    private void receiveData() {
        try {
            while (inputStream.available() > 0) {
                // Input string is formatted as "xxxxx,yyyy" where x is MopedDataType and y is a int value
                String input = inputStream.readUTF();

                if (input.equals(EXIT_CODE)) {
                    onDisconnect();
                    break;
                } else if (input.equals(PING)) {
                    //Do nothing if it was a ping received.
                    continue;
                }

                String[] args = input.split(SEPARATOR);

                //Extract data from input.
                MopedDataType type = MopedDataType.parseInt(Integer.parseInt(args[0]));
                int value = Integer.parseInt(args[1]);

                handleInput(type, value);
            }
        } catch (IOException e) {
            onDisconnect();
        }
    }

    /**
     * Notifies all listeners that a connection has been established.
     */
    protected void notifyConnected() {
        log("Connected");
        for (CommunicationListener cl : listeners) {
            cl.onConnection();
        }
    }

    public boolean isRunning() {
        return mainThread.isAlive();
    }

    private void notifyValueChanged(MopedDataType type, int value) {
        for (CommunicationListener cl : listeners) {
            cl.onValueChanged(type, value);
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
        log("Disconnected");
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
            case MOPED_STATE:
                //This means a state was changed. Extract new state from value and send to listeners.
                notifyStateChange(MopedState.parseInt(value));
                break;
            default:
                notifyValueChanged(type, value);
                break;
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
     *
     * @throws SocketTimeoutException thrown if the socket is not connected in a certain time span
     */
    protected abstract void connectSocket() throws SocketTimeoutException;

    /**
     * Prints the given object in out.println and adds which
     * thread printed it, but only if logging is enabled;
     *
     * @param object the object to log
     */
    protected void log(Object object) {
        if (loggingEnabled) {
            System.out.println("[" + Thread.currentThread().getName() + " Thread] " + object.toString());
        }
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void enableLogging() {
        this.loggingEnabled = true;
    }

    public void disableLogging() {
        this.loggingEnabled = false;
    }
}

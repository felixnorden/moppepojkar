package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerCommunicator extends AbstractCommunicator {

    private ServerSocket serverSocket;

    /**
     * Creates a ServerCommunicator acting on the specified port.
     *
     * @param port
     */
    public ServerCommunicator(int port) {
        super(port);

        mainThread = new Thread(this);
    }


    /**
     * We do want the server to keep looking for a connection when a disconnect happens.
     * Therefore, clear previous connection and keep connecting in mainloop.
     */
    @Override
    protected void handleDisconnect() {
        clearConnection();
    }

    @Override
    protected void clearConnection() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }

            if (socket != null) {
                socket.close();
            }

            serverSocket = null;
            socket = null;
            inputStream = null;
            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void connectSocket() {
        try {
            System.out.println("[SERVER] Looking for connection on port " + port + "...");
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            notifyConnected();
        } catch (IOException e) {
            //Runs if port was already bound by another socket (possibly ours)
            e.printStackTrace();
        }
    }
}

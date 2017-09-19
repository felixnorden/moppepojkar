package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientCommunicator extends AbstractCommunicator {

    private final String ip;

    /**
     * @param ip   The ip of the server.
     * @param port The port of the server.
     */
    public ClientCommunicator(String ip, int port) {
        super(port);
        this.ip = ip;

        mainThread = new Thread(this);
    }

    /**
     * Loop which tries to maintain a connection to the server.
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
                //Just catch, do nothing.
                System.out.println("Interrupted sleep");
            }
        }

        clearConnection();
    }
    private void connectSocket() {
        try {
            socket = new Socket(ip, this.port);
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            notifyConnected();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

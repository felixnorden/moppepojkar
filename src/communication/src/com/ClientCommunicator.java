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

    protected void connectSocket() {
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

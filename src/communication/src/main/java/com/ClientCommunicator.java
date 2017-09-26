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
     * We do not want the client to try to reconnect automagically when disconnected.
     * Therefore, stop mainthread.
     */
    @Override
    protected void handleDisconnect() {
        this.stop();
    }

    @Override
    protected void clearConnection() {
        try {
            if (socket != null) {
                socket.close();
            }

            socket = null;
            inputStream = null;
            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void connectSocket() {
        try {
            System.out.println(getClass().getName() + ": Looking for server on port " + ip + ":" + port + "...");
            socket = new Socket(ip, this.port);
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            notifyConnected();
        } catch (IOException e) {
            //This block runs if socket cannot connect.
        }
    }
}

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
        while (true) {
            // TODO: 2017-09-13 Is this kind of loop optimal?
            if (socket == null || !socket.isConnected()) {
                try {
                    socket = new Socket(ip, this.port);
                    notifyConnected();
                    this.inputStream = new DataInputStream(socket.getInputStream());
                    this.outputStream = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                //Run update if connected to socket
                this.update();
            }

            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

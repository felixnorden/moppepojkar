package com;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerCommunicator extends AbstractCommunicator {

    private ServerSocket serverSocket;

    public ServerCommunicator(int port) {
        super(port);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainThread = new Thread(this);
    }

    /**
     * Loop which tries to maintain a connection to a client.
     * If a client is lost, it looks for another one till found.
     * <p>
     * When a connection is established, all listeners will be notified.
     */
    @Override
    public void run() {
        while (true) {
            // TODO: 2017-09-13 Is this kind of loop optimal? 
            if (socket == null || !socket.isConnected()) {
                try {
                    socket = serverSocket.accept();
                    notifyConnected();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

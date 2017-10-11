package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ServerCommunicator extends AbstractCommunicator {

    private ServerSocket serverSocket;

    /**
     * Creates a ServerCommunicator acting on the specified port.
     *
     * @param port
     */
    public ServerCommunicator(int port) {
        super(port);
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
            log("IOException occurred when trying to clear connection." +
                    "This should literally never happen and if it does next round of beer is on me :)");
            e.printStackTrace();
        }
    }

    protected void connectSocket() throws SocketTimeoutException {
        try {
            log("Looking for connection on port " + port + "...");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(4000);    //4 second timeout
            socket = serverSocket.accept();
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            notifyConnected();
        } catch (SocketTimeoutException e) {
            //We actually do need to catch this even though the method signature declares it will be thrown
            //This is because SocketTimeOutException is a IOException, which means it will get caught in the last
            //catch block.
            //We need to throw it again because we want the caller to be notified if timeout occurs.
            throw new SocketTimeoutException(e.getMessage());
        } catch (IOException e) {
            //Runs if port was already bound by another socket (possibly ours)
            log("It looks like port " + this.port + " is already bound," +
                    " possibly caused by two identical instances of this or another program");
            e.printStackTrace();
            stop();
        }
    }
}

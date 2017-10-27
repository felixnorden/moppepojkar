package com;


import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientCommunicatorTest {

    @Test
    public void testOnConnection() {
        ArrayList<Boolean> vars = new ArrayList<>();
        Communicator client = new ClientCommunicator("0.0.0.0", 9501);
        client.addListener(new ClientListener(vars));
        client.start();

        try {
            ServerSocket serverSocket = new ServerSocket(9501);
            serverSocket.setSoTimeout(5000);
            serverSocket.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //Wait up to 5 seconds before failing
            for (int i = 0; i < 100; i++) {
                Thread.sleep(50);
                if (vars.size() == 1) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(vars.size() == 1);
        assertTrue(vars.get(0));
    }

    @Test
    public void testClientConnectWithNoServer() {
        try {
            List<Boolean> list = new ArrayList<>();
            Communicator client = new ClientCommunicator("0.0.0.0", 1111);
            client.addListener(new ClientListener(list));
            client.start();
            Thread.sleep(500);
            client.stop();

            assertTrue(list.isEmpty());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class ClientListener implements CommunicationListener {

        private List<Boolean> list;

        ClientListener(List<Boolean> list) {
            this.list = list;
        }

        @Override
        public void onConnection() {
            list.add(true);
        }

        @Override
        public void onStateChange(MopedState stateChange) {

        }

        @Override
        public void onDisconnection() {
        }

        @Override
        public void onValueChanged(MopedDataType type, int value) {
        }
    }
}
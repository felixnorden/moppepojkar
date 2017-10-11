package com;


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
        final ArrayList<Boolean> vars = new ArrayList<>();

        CommunicationListener cl = new CommunicationListener() {
            @Override
            public void onConnection() {
                vars.add(true);
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
        };

        Communicator client = new ClientCommunicator("localhost", 9501);
        client.addListener(cl);
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
            final List<Object> list = new ArrayList();
            CommunicationListener cl = new CommunicationListener() {
                @Override
                public void onConnection() {
                    list.add(new Object());
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
            };

            Communicator client = new ClientCommunicator("localhost", 1111);
            client.start();
            Thread.sleep(500);
            client.stop();

            assertTrue(list.isEmpty());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package com;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServerCommunicationTest {

    @Test
    public void testPortAlreadyBound() {
        final Communicator server = new ServerCommunicator(55546);
        final Communicator server2 = new ServerCommunicator(55546);
        final Communicator client = new ClientCommunicator("0.0.0.0", 55546);

        CommunicationListener cl = new CommunicationListener() {
            @Override
            public void onConnection() {
                server2.start();
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

        server.addListener(cl);

        server.start();
        client.start();

        try {
            //Wait up to 5 seconds before failing
            for (int i = 0; i < 100; i++) {
                Thread.sleep(50);
                if (!server2.isRunning()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(false, server2.isRunning());
    }

    @Test
    public void testOnConnection() {
        final ArrayList<Boolean> vars = new ArrayList();

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

        Communicator server = new ServerCommunicator(9508);
        Communicator client = new ClientCommunicator("0.0.0.0", 9508);
        server.addListener(cl);
        server.start();
        client.start();

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

        assertEquals(1, vars.size());
    }

    @Test
    public void testConnectSocket() {
        final ArrayList<Boolean> passed = new ArrayList();

        Communicator server = new ServerCommunicator(12346);
        Communicator client = new ClientCommunicator("0.0.0.0", 12346);
        server.addListener(new CommunicationListener() {
            @Override
            public void onConnection() {
                passed.add(true);
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
        });
        server.start();

        //Sleep for 2 timeout-cycles.
        try {
            Thread.sleep(8500);
            client.start();
            //Wait up to 5 seconds before failing
            for (int i = 0; i < 100; i++) {
                Thread.sleep(50);
                if (passed.size() == 1) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(1, passed.size());
        assertEquals(true, passed.get(0));
    }
}
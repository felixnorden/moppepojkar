package com;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServerCommunicationTest {

    @Test
    public void testPortAlreadyBound() {
        Communicator server = new ServerCommunicator(55546);
        Communicator server2 = new ServerCommunicator(55546);
        Communicator client = new ClientCommunicator("0.0.0.0", 55546);
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
        ArrayList<Boolean> vars = new ArrayList();

        Communicator server = new ServerCommunicator(9500);
        Communicator client = new ClientCommunicator("0.0.0.0", 9500);
        server.addListener(new ServerListenerBound(server, vars));
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

        assertTrue(vars.size() == 1);
        assertTrue(vars.get(0));
    }

    @Test
    public void testConnectSocket() {
        final ArrayList<Boolean> passed = new ArrayList();

        Communicator server = new ServerCommunicator(12346);
        Communicator client = new ClientCommunicator("0.0.0.0", 12346);
        server.addListener(new ServerListener(passed));
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

    private static class ServerListenerBound implements CommunicationListener {
        private Communicator server;
        private List<Boolean> list;

        ServerListenerBound(Communicator server, List<Boolean> list) {
            this.server = server;
            this.list = list;
        }

        @Override
        public void onConnection() {
            server.start();
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

    private static class ServerListener implements CommunicationListener {
        private List<Boolean> list;

        ServerListener(List<Boolean> list) {
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
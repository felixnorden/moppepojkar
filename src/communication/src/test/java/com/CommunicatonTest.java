package com;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Hampus on 2017-09-14.
 */

public class CommunicatonTest {

    @Test
    public void testSendValueFromServer() {
        Communicator client = new ClientCommunicator("0.0.0.0", 12942);
        Communicator server = new ServerCommunicator(12942);
        ArrayList<Integer> vars = new ArrayList<>();
        client.addListener(new ClientListenerValue(server, vars));
        server.start();
        client.start();

        try {
            //Wait up to 5 seconds before failing
            for (int i = 0; i < 100; i++) {
                Thread.sleep(50);
                if (vars.size() != 0) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(MopedDataType.parseInt(vars.get(0)), MopedDataType.VELOCITY);
        assertEquals((int) vars.get(1), 10);
    }

    @Test
    public void testSendValueFromClient() {
        Communicator client = new ClientCommunicator("0.0.0.0", 12941);
        Communicator server = new ServerCommunicator(12941);
        ArrayList<Integer> vars = new ArrayList<>();
        server.addListener(new ServerListenerValue(client, vars));
        server.start();
        client.start();

        try {
            //Wait up to 5 seconds before failing
            for (int i = 0; i < 100; i++) {
                Thread.sleep(50);
                if (vars.size() != 0) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(MopedDataType.parseInt(vars.get(0)), MopedDataType.VELOCITY);
        assertEquals((int) vars.get(1), 20);
    }

    @Test
    public void testSendStateFromClient() {
        Communicator client = new ClientCommunicator("0.0.0.0", 12939);
        Communicator server = new ServerCommunicator(12939);
        ArrayList<Boolean> vars = new ArrayList<>();

        client.addListener(new ClientListenerState(server, vars));
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
    }

    @Test
    public void testSendStateFromServer() {
        Communicator client = new ClientCommunicator("0.0.0.0", 12940);
        Communicator server = new ServerCommunicator(12940);
        ArrayList<Boolean> vars = new ArrayList<>();
        server.addListener(new ServerListenerState(client, vars));
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
    }

    @Test
    public void testOnServerDisconnection() {
        //Let's test the client first
        Communicator client = new ClientCommunicator("0.0.0.0", 8666);
        Communicator server = new ServerCommunicator(8666);
        ArrayList<Boolean> vars = new ArrayList<>();
        client.addListener(new ServerListenerDisconnect(server, vars));
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
        //Check if only one item is in the list and if it is the value added in onDisconnection.
        assertTrue(vars.size() == 1 && vars.get(0));
    }

    @Test
    public void testOnClientDisconnection() {
        //Let's test the client first
        Communicator client = new ClientCommunicator("0.0.0.0", 8668);
        Communicator server = new ServerCommunicator(8668);
        ArrayList<Boolean> vars = new ArrayList<>();
        server.addListener(new ClientListenerDisconnect(client, vars));
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
        //Check if only one item is in the list and if it is the value added in onDisconnection.
        assertTrue(vars.size() == 1 && vars.get(0));
    }

    @Test
    public void testLogging() {
        Communicator server = new ServerCommunicator(5757);

        assertTrue(server.isLoggingEnabled());

        server.disableLogging();

        assertTrue(!server.isLoggingEnabled());

        server.enableLogging();

        assertTrue(server.isLoggingEnabled());
    }

    private static class ClientListenerDisconnect implements CommunicationListener {

        private List<Boolean> list;
        private Communicator client;

        ClientListenerDisconnect(Communicator client, List<Boolean> list) {
            this.list = list;
            this.client = client;
        }

        @Override
        public void onConnection() {
            client.stop();
        }

        @Override
        public void onStateChange(MopedState stateChange) {

        }

        @Override
        public void onDisconnection() {
            list.add(true);
        }

        @Override
        public void onValueChanged(MopedDataType type, int value) {
        }
    }

    private static class ServerListenerDisconnect implements CommunicationListener {

        private List<Boolean> list;
        private Communicator server;

        ServerListenerDisconnect(Communicator server, List<Boolean> list) {
            this.list = list;
            this.server = server;
        }

        @Override
        public void onConnection() {
            server.stop();
        }

        @Override
        public void onStateChange(MopedState stateChange) {

        }

        @Override
        public void onDisconnection() {
            list.add(true);
        }

        @Override
        public void onValueChanged(MopedDataType type, int value) {

        }
    }

    private static class ServerListenerValue implements CommunicationListener {

        private List<Integer> list;
        private Communicator client;

        ServerListenerValue(Communicator client, List<Integer> list) {
            this.list = list;
            this.client = client;
        }

        @Override
        public void onConnection() {
            client.setValue(MopedDataType.VELOCITY, 20);
        }

        @Override
        public void onStateChange(MopedState stateChange) {
        }

        @Override
        public void onDisconnection() {

        }

        @Override
        public void onValueChanged(MopedDataType type, int value) {
            list.add(type.toInt());
            list.add(value);
        }
    }

    private static class ClientListenerValue implements CommunicationListener {

        private List<Integer> list;
        private Communicator server;

        ClientListenerValue(Communicator server, List<Integer> list) {
            this.list = list;
            this.server = server;
        }

        @Override
        public void onConnection() {
            server.setValue(MopedDataType.VELOCITY, 10);
        }

        @Override
        public void onStateChange(MopedState stateChange) {

        }

        @Override
        public void onDisconnection() {

        }

        @Override
        public void onValueChanged(MopedDataType type, int value) {
            list.add(type.toInt());
            list.add(value);
        }
    }

    private static class ServerListenerState implements CommunicationListener {

        private List<Boolean> list;
        private Communicator client;

        ServerListenerState(Communicator client, List<Boolean> list) {
            this.list = list;
            this.client = client;
        }

        @Override
        public void onConnection() {
            client.setState(MopedState.MANUAL);
        }

        @Override
        public void onStateChange(MopedState stateChange) {
            list.add(true);
            assertTrue(stateChange == MopedState.MANUAL);
        }

        @Override
        public void onDisconnection() {

        }

        @Override
        public void onValueChanged(MopedDataType type, int value) {

        }
    }

    private static class ClientListenerState implements CommunicationListener {

        private List<Boolean> list;
        private Communicator server;

        ClientListenerState(Communicator server, List<Boolean> list) {
            this.list = list;
            this.server = server;
        }

        @Override
        public void onConnection() {
            server.setState(MopedState.ACC);
        }

        @Override
        public void onStateChange(MopedState stateChange) {
            list.add(true);
            assertTrue(stateChange == MopedState.ACC);
        }

        @Override
        public void onDisconnection() {

        }

        @Override
        public void onValueChanged(MopedDataType type, int value) {

        }
    }
}

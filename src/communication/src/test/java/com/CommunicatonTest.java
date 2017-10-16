package com;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Hampus on 2017-09-14.
 */

public class CommunicatonTest {

    @Test
    public void testSendValueFromServer() {
        Communicator client = new ClientCommunicator("0.0.0.0", 12942);
        final Communicator server = new ServerCommunicator(12942);

        final ArrayList<Integer> vars = new ArrayList<>();
        CommunicationListener cl = new CommunicationListener() {
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
                vars.add(type.toInt());
                vars.add(value);
            }
        };

        client.addListener(cl);
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
        final Communicator client = new ClientCommunicator("0.0.0.0", 12941);
        Communicator server = new ServerCommunicator(12941);

        final ArrayList<Integer> vars = new ArrayList<>();
        CommunicationListener cl = new CommunicationListener() {
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
                vars.add(type.toInt());
                vars.add(value);
            }
        };

        server.addListener(cl);
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
        final Communicator server = new ServerCommunicator(12939);

        final ArrayList<Boolean> vars = new ArrayList<>();
        CommunicationListener cl = new CommunicationListener() {
            @Override
            public void onConnection() {
                server.setState(MopedState.ACC);
            }

            @Override
            public void onStateChange(MopedState stateChange) {
                vars.add(true);
                assertTrue(stateChange == MopedState.ACC);
            }

            @Override
            public void onDisconnection() {

            }

            @Override
            public void onValueChanged(MopedDataType type, int value) {

            }
        };

        client.addListener(cl);
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
        final Communicator client = new ClientCommunicator("0.0.0.0", 12940);
        Communicator server = new ServerCommunicator(12940);

        final ArrayList<Boolean> vars = new ArrayList<>();
        CommunicationListener cl = new CommunicationListener() {
            @Override
            public void onConnection() {
                client.setState(MopedState.MANUAL);
            }

            @Override
            public void onStateChange(MopedState stateChange) {
                vars.add(true);
                assertTrue(stateChange == MopedState.MANUAL);
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
        final Communicator server = new ServerCommunicator(8666);
        final ArrayList<Boolean> vars = new ArrayList<>();
        CommunicationListener cl = new CommunicationListener() {
            @Override
            public void onConnection() {
                server.stop();
            }

            @Override
            public void onStateChange(MopedState stateChange) {

            }

            @Override
            public void onDisconnection() {
                vars.add(true);
            }

            @Override
            public void onValueChanged(MopedDataType type, int value) {

            }
        };
        client.addListener(cl);
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
        final Communicator client = new ClientCommunicator("0.0.0.0", 8668);
        Communicator server = new ServerCommunicator(8668);
        final ArrayList<Boolean> vars = new ArrayList<>();
        CommunicationListener cl = new CommunicationListener() {
            @Override
            public void onConnection() {
                client.stop();
            }

            @Override
            public void onStateChange(MopedState stateChange) {

            }

            @Override
            public void onDisconnection() {
                vars.add(true);
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
}

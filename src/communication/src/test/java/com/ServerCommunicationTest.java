package com;


import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServerCommunicationTest {

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

        Communicator server = new ServerCommunicator(9500);
        server.addListener(cl);
        server.start();


        try {
            Socket socket = new Socket("localhost", 9500);

            //Wait up to 5 seconds before failing
            for (int i = 0; i < 100; i++) {
                Thread.sleep(50);
                if (vars.size() == 1) {
                    break;
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        assertTrue(vars.size() == 1);
        assertTrue(vars.get(0));
    }

    @Test
    public void testConnectSocket() {
        final ArrayList<Boolean> passed = new ArrayList();

        Communicator server = new ServerCommunicator(12346);
        Communicator client = new ClientCommunicator("localhost", 12346);
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
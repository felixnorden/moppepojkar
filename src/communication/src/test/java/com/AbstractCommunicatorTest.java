package com;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AbstractCommunicatorTest {
    @Test
    public void testMultipleStarts() {
        try {
            Communicator server = new ServerCommunicator(45756);
            Communicator client = new ClientCommunicator("localhost", 45756);
            server.start();
            server.start();

            Thread.sleep(100);

            client.start();
            client.start();
            server.start();
            client.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientRestart() {
        try {
            final ArrayList<Boolean> passed = new ArrayList<>();
            Communicator server = new ServerCommunicator(45757);
            Communicator client = new ClientCommunicator("localhost", 45757);

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
            client.start();
            //Let them connect
            for (int i = 0; i < 1000; i++) {
                if (passed.size() == 1) {
                    break;
                }
                Thread.sleep(50);
            }

            client.stop();
            Thread.sleep(300);
            client.start();
            for (int i = 0; i < 1000; i++) {
                if (passed.size() == 2) {
                    break;
                }
                Thread.sleep(50);
            }

            assertEquals(passed.size(), 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServerRestart() {
        try {
            final ArrayList<Boolean> passed = new ArrayList<>();
            Communicator server = new ServerCommunicator(45758);
            Communicator client = new ClientCommunicator("localhost", 45758);

            client.addListener(new CommunicationListener() {
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
            client.start();
            //Let them connect
            for (int i = 0; i < 1000; i++) {
                if (passed.size() == 1) {
                    break;
                }
                Thread.sleep(50);
            }

            server.stop();
            Thread.sleep(100);
            server.start();
            client.start(); //Client also needs to be restarted
            for (int i = 0; i < 1000; i++) {
                if (passed.size() == 2) {
                    break;
                }
                Thread.sleep(50);
            }

            assertEquals(passed.size(), 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

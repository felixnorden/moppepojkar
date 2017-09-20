package test;

import com.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by Hampus on 2017-09-14.
 */

public class CommunicatonTest {

    @Test
    public void testSendFromClient() {
        Communicator client = new ClientCommunicator("localhost", 12939);
        Communicator server = new ServerCommunicator(12939);

        ArrayList<Boolean> vars = new ArrayList<>();
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
    public void testSendFromServer() {
        Communicator client = new ClientCommunicator("localhost", 12940);
        Communicator server = new ServerCommunicator(12940);

        ArrayList<Boolean> vars = new ArrayList<>();
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
        Communicator client = new ClientCommunicator("localhost", 8666);
        Communicator server = new ServerCommunicator(8666);
        ArrayList<Boolean> vars = new ArrayList<>();
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
        Communicator client = new ClientCommunicator("localhost", 8668);
        Communicator server = new ServerCommunicator(8668);
        ArrayList<Boolean> vars = new ArrayList<>();
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
}

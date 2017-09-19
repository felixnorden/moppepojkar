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


}

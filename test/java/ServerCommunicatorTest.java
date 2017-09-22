package test.java;

import main.java.CommunicationListener;
import main.java.Communicator;
import main.java.MopedState;
import main.java.ServerCommunicator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServerCommunicatorTest {

    @Test
    public void testOnConnection() {
        ArrayList<Boolean> vars = new ArrayList();

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
}
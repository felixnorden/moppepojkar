package test;

import com.ClientCommunicator;
import com.CommunicationListener;
import com.Communicator;
import com.ServerCommunicator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientCommunicatorTest {

    @Test
    public void testOnConnection() {
        ArrayList<Boolean> vars = new ArrayList<>();

        CommunicationListener cl = new CommunicationListener() {
            @Override
            public void onConnection() {
                vars.add(true);
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

        assertTrue(vars.size() == 1);
        assertTrue(vars.get(0));
    }

}
package main.java;

import java.util.Scanner;

/**
 * Created by Zack on 2017-09-20.
 */
public class Test {
    public static void main(String[] args) {
        new Test();
    }

    CommunicationListener cl1 = new CommunicationListener() {
        @Override
        public void onConnection() {
            System.out.println("[SERVER] CONNECT");
        }

        @Override
        public void onStateChange(MopedState stateChange) {
            System.out.println("[SERVER] STATE: " + stateChange.name());
        }

        @Override
        public void onDisconnection() {
            System.out.println("[SERVER] DISCONNECT");
        }
    };

    CommunicationListener cl2 = new CommunicationListener() {
        @Override
        public void onConnection() {
            System.out.println("[CLIENT] CONNECT");
        }

        @Override
        public void onStateChange(MopedState stateChange) {
            System.out.println("[CLIENT] STATE: " + stateChange.name());
        }

        @Override
        public void onDisconnection() {
            System.out.println("[CLIENT] DISCONNECT");
        }
    };

    public Test() {
        Communicator server = new ServerCommunicator(9000);
        Communicator client = new ClientCommunicator("localhost", 9000);

        client.addListener(cl2);
        server.addListener(cl1);

        Scanner in = new Scanner(System.in);

        while(true){
            String input = in.nextLine();

            switch (input) {
                case "sstart":
                    server.start();
                    break;
                case "sstop":
                    server.stop();
                    break;
                case "sstate ACC":
                    server.setState(MopedState.ACC);
                    break;
                case "sstate MANUAL":
                    server.setState(MopedState.MANUAL);
                    break;
                case "cstart":
                    client.start();
                    break;
                case "cstop":
                    client.stop();
                    break;
                case "cstate ACC":
                    client.setState(MopedState.ACC);
                    break;
                case "cstate MANUAL":
                    client.setState(MopedState.MANUAL);
                    break;
            }
        }
    }
}

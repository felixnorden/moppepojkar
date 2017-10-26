package com;

import java.util.Scanner;

/**
 * Created by Zack on 2017-09-20.
 * Class used to simulate a server/client.
 * Mopedport: 8999
 * Communicatorport: 9000
 * IP: ipconfig /all
 * Usage: See switch-case below.
 */
public class LocalManualTest {
    public static void main(String[] args) {
        new LocalManualTest();
    }

    //Listener which acts as the servers listener.
    CommunicationListener serverListener = new CommunicationListener() {
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

        @Override
        public void onValueChanged(MopedDataType type, int value) {
            System.out.println("[SERVER] VALUE: " + type.name() + " - " + value);
        }
    };

    //Listener which acts as the clients listener.
    CommunicationListener clientListener = new CommunicationListener() {
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

        @Override
        public void onValueChanged(MopedDataType type, int value) {
            System.out.println("[CLIENT] VALUE: " + type.name() + " - " + value);
        }
    };

    public LocalManualTest() {
        Communicator server = new ServerCommunicator(9000);
        //Change 0.0.0.0 to host ip if host isn't this computer.
        Communicator client = new ClientCommunicator("192.168.137.95", 9005);


        client.addListener(clientListener);
        server.addListener(serverListener);

        //Loop which let you interact with the simulated client/server.
        Scanner in = new Scanner(System.in, "UTF-8");
        while (true) {
            String input = in.nextLine();

            switch (input) {
                //Server commands
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
                case "ssim":
                    for (int i = 0; i < 10000; i++) {
                        server.setValue(MopedDataType.VELOCITY, (int) (Math.sin(i) * 10));
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                //Client commands
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
                default:
                    System.out.println("Please insert a real command.");
                    break;
            }

            //Client handling throttling
            if (input.startsWith("c t ")) {
                String[] arg = input.split(" ");
                int t;
                t = Integer.parseInt(arg[2]);
                client.setValue(MopedDataType.THROTTLE, t);
            } else if (input.startsWith("c s ")) {
                String[] arg = input.split(" ");
                int t;
                t = Integer.parseInt(arg[2]);
                client.setValue(MopedDataType.STEERING, t);
            }
        }
    }
}

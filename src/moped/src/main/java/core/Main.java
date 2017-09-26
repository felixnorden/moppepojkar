package core;

import core.behaviour_states.StateController;

public class Main {

    public static void main(String[] args) {
        StateController sc = new StateController();

        Thread mainLoop = new Thread(() -> {
            while (true) {
                sc.run();

                // Allow for other processes
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mainLoop.start();

        try {
            mainLoop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

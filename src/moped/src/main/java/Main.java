import com_io.CommunicatorFactoryImpl;
import core.behaviour_states.StateController;

public class Main {

    public static void main(String[] args) {
        StateController sc = new StateController();
        new RemoteMediator(9005, CommunicatorFactoryImpl.getFactoryInstance().getComInstance());

        Thread mainLoop = new Thread(() -> {
            while (true) {
                sc.run();

                // Allow for other processes
                try {
                    Thread.sleep(50);
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

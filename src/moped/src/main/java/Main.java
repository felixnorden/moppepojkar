import com_io.CommunicatorFactory;
import core.behaviour_states.StateController;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;
import utils.Config;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        StateController sc = new StateController();
        new RemoteMediator(9005, CommunicatorFactory.getComInstance());

        CameraTrackingMediator cameraTracking = null;
        try {
            ProcessRunner cReader = ProcessFactory.createPythonProcess(Config.C_READER);
            cameraTracking = new CameraTrackingMediator(CommunicatorFactory.getComInstance(), cReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Thread mainLoop = new Thread(() -> {
            while (true) {
                sc.run();

                // Allow for other processes
                try {
                    Thread.sleep(25);
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

        if (cameraTracking != null) {
            cameraTracking.kill();
        }
    }
}
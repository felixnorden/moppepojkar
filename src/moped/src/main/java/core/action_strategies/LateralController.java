package core.action_strategies;

import core.process_runner.InputSubscriber;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;
import pid.LateralPIDController;
import pid.PIDController;
import sensor_data_conversion.SensorDataConverter;

import java.io.FileNotFoundException;

public class LateralController implements ActionStrategy, InputSubscriber {

    private ProcessRunner imageRecognition;
    private double currentRotationalOffset;
    private PIDController lateralPid;
    private long lastActionTime;
    private double lastAction;

    LateralController(String imageRecognitionPath) {
        lastActionTime = System.currentTimeMillis();
        currentRotationalOffset = 0;
        lastAction = 0;
        lateralPid = new LateralPIDController(0, 10,0,0);

        try {
            imageRecognition = ProcessFactory.createPythonProcess(imageRecognitionPath);
            imageRecognition.start();
            imageRecognition.subscribeToInput(this);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Couldn't start LateralController python script!!!");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public double takeAction() {
        long currentTime = System.currentTimeMillis();
        int deltaTime = (int) (currentTime - lastActionTime);
        if (deltaTime > 100) {
            lastAction = lateralPid.evaluation(currentRotationalOffset, deltaTime * 1000);
        }

        return lastAction;
    }

    @Override
    public void outputString(String s) {
        double imageRotationValue = new SensorDataConverter().convertDistance(s);

        if (!Double.isNaN(imageRotationValue)) {
            currentRotationalOffset = imageRotationValue;
        }
    }
}

package core.action_strategies;

import core.process_runner.InputSubscriber;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;
import pid.LateralPIDController;
import pid.PIDController;
import sensor_data_conversion.SensorDataConverter;

import java.io.FileNotFoundException;

import static utils.Config.*;

// TODO: 2017-10-05 Refactor with PIDParser class to find some common base class
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
        lateralPid = new LateralPIDController(LAT_TGT_POS, LAT_P,LAT_I,LAT_D);

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
        if (deltaTime > LAT_UPDATE_DELAY) {
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

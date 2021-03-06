package core.action_strategies;

import com_io.CommunicationsMediator;
import com_io.DataReceiver;
import com_io.Direction;
import core.pid.LateralPIDController;
import core.pid.PIDController;
import utils.Config;

import static utils.Config.*;

/**
 * Lateral controlling class for handling lateral automation
 */
public class LateralController implements ActionStrategy, DataReceiver {

    private double currentCircleOffset;
    private PIDController lateralPid;
    private long lastActionTime;
    private double lastAction;


    LateralController(CommunicationsMediator communicationsMediator) {
        communicationsMediator.subscribe(Direction.INTERNAL, this);
        lastActionTime = System.currentTimeMillis();
        currentCircleOffset = 0;
        lastAction = 0;
        lateralPid = new LateralPIDController(LAT_TGT_POS, LAT_P, LAT_I, LAT_D);
    }

    @Override
    public synchronized double takeAction() {
        long currentTime = System.currentTimeMillis();
        double deltaTime = (double) (currentTime - lastActionTime);
        lastAction = lateralPid.evaluation(currentCircleOffset, deltaTime / 1000.0);
        lastActionTime = currentTime;

        return lastAction;
    }

    @Override
    public synchronized void dataReceived(String string) {
        String[] formattedData = string.split(Config.SEPARATOR);
        if (formattedData.length == 2 && formattedData[0].equals(Config.CAM_TGT_OFFSET)) {
            double circleOffset = Double.valueOf(formattedData[1]);
            if (!Double.isNaN(circleOffset)) {
                currentCircleOffset = circleOffset;
            }
        }
    }
}

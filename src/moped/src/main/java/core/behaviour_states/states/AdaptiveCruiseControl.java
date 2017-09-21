package core.behaviour_states.states;

import com_io.CommunicationsMediator;
import com_io.CommunicatorFactory;
import com_io.CommunicatorFactoryImpl;
import core.action_strategies.*;
import core.car_control.CarControl;
import core.sensors.DistanceSensor;
import core.sensors.DistanceSensorImpl;

class AdaptiveCruiseControl implements BehaviourState {

    private static final int EMERGENCY_THRESHOLD = 10;

    private BidirectionalHandler currentHandler;

    private final BidirectionalHandler accHandler;
    private final BidirectionalHandler emergencyHandler;

    private DistanceSensor distanceSensor;
    private int emergencyCount;

    private CarControl carController;

    public AdaptiveCruiseControl(CarControl carController) {
        emergencyCount = 0;

        CommunicatorFactory comFactory = CommunicatorFactoryImpl.getFactoryInstance();
        CommunicationsMediator comIO = comFactory.getComInstance();

        ActionStrategyFactory actionFactory = ActionStrategyFactoryImpl.getInstance();

        ActionStrategy pidController = actionFactory.createPIDController();
        ActionStrategy steerController = actionFactory.createSteerController();
        this.accHandler = new BidirectionalHandlerImpl(pidController, steerController);

        ActionStrategy emergencyController = actionFactory.createEmgyController();
        this.emergencyHandler = new BidirectionalHandlerImpl(emergencyController, emergencyController);

        this.distanceSensor = DistanceSensorImpl.getInstance();
        this.carController = carController;
    }

    @Override
    public void run() {
        this.currentHandler = determineHandler();

        carController.setThrottle((int) currentHandler.takeLatitudeAction());
        carController.setSteerValue((int) currentHandler.takeLongitudeAction());
    }

    private BidirectionalHandler determineHandler() {
        double range = distanceSensor.getDistance();
        // TODO: 21/09/2017 Create class to calculate break length.
        double breakLength = 110; // Temporary value

        if (breakLength > range) {
            emergencyCount++;
            if (emergencyCount >= EMERGENCY_THRESHOLD) {
                return emergencyHandler;
            }
        } else {
            emergencyCount = 0;
        }

        return accHandler;
    }
}

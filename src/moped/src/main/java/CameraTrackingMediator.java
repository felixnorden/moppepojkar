import com_io.CommunicationsMediator;
import com_io.Direction;
import core.process_runner.ProcessRunner;
import utils.Config;

class CameraTrackingMediator {

    private final ProcessRunner processRunner;
    private CommunicationsMediator communicationsMediator;

    CameraTrackingMediator(CommunicationsMediator communicationsMediator, ProcessRunner processRunner) {
        this.communicationsMediator = communicationsMediator;
        this.processRunner = processRunner;

        processRunner.subscribeToInput(this::stringRecevied);
        processRunner.start();
    }

    void stringRecevied(String string) {
        String[] formattedData = string.split(Config.REGEX);
        if (formattedData.length == 2) {
            String targetOffset  = formattedData[0];
            String targetDistance  = formattedData[1];

            communicationsMediator.transmitData(Config.CAM_TGT_OFFSET + Config.REGEX + targetOffset, Direction.INTERNAL);
            communicationsMediator.transmitData(Config.CAM_TGT_DIST + Config.REGEX + targetDistance, Direction.INTERNAL);
        }
    }

    void kill() {
        processRunner.forceCloseProcess();
    }
}

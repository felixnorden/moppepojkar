import com_io.CommunicationsMediator;
import com_io.Direction;
import core.process_runner.ProcessRunner;
import utils.Config;

class CameraTrackingMediator {

    private final ProcessRunner processRunner;
    private CommunicationsMediator communicationsMediator;
    private StringBuilder stringBuilder;

    CameraTrackingMediator(CommunicationsMediator communicationsMediator, ProcessRunner processRunner) {
        this.communicationsMediator = communicationsMediator;
        this.processRunner = processRunner;
        this.stringBuilder = new StringBuilder();

        processRunner.subscribeToInput(this::stringReceived);
        processRunner.start();
    }

    synchronized void stringReceived(String string) {
        for (char c : string.toCharArray()) {
            if (c != 10 && c != 13) {
                stringBuilder.append(c);
            } else {
                String[] formattedData = stringBuilder.toString().split(Config.REGEX);
                if (formattedData.length == 2) {
                    String targetOffset  = formattedData[0];
                    String targetDistance  = formattedData[1];

                    communicationsMediator.transmitData(Config.CAM_TGT_OFFSET + Config.REGEX + targetOffset, Direction.INTERNAL);
                    communicationsMediator.transmitData(Config.CAM_TGT_DIST + Config.REGEX + targetDistance, Direction.INTERNAL);
                }
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
    }

    void kill() {
        processRunner.forceCloseProcess();
    }
}


import com_io.CommunicatorFactory;

import core.car_control.CarControl;
import core.car_control.CarControlImpl;
import core.process_runner.InputSubscriber;
import core.process_runner.ProcessFactory;
import core.process_runner.ProcessRunner;
import utils.Config;
import utils.StrToDoubleConverter;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LatencyTesting implements Runnable, InputSubscriber {

    private int cycle = 0;
    private final Object threadLock = new Object();
    private int latitude = 0;
    private int longitude = 0;
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void run() {
        CarControl carControl = new CarControlImpl(CommunicatorFactory.getComInstance());

        try {
            ProcessRunner driveCalc = ProcessFactory.createPythonProcess("C:\\Users\\amk19\\Desktop\\mock-drive-calc.py");
            driveCalc.subscribeToInput(this);
            driveCalc.start();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }


        while (true) {
            synchronized (threadLock) {
                carControl.setSteerValue(longitude);
                carControl.setThrottle(latitude);
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public synchronized void receivedString(String string) {
        for (char c : string.toCharArray()) {
            if (c != 10 && c != 13) {
                stringBuilder.append(c);
            } else {
                String[] formattedData = stringBuilder.toString().split(Config.REGEX);
                if (formattedData.length == 2) {
                    String targetOffset = formattedData[0];
                    String targetDistance = formattedData[1];


                    synchronized (threadLock) {
                        longitude = (int) StrToDoubleConverter.convertStringToDouble(targetDistance);
                        latitude = (int) StrToDoubleConverter.convertStringToDouble(targetOffset);
                        if (longitude == 100) {
                            System.out.println("CYCLE: " + cycle);
                            cycle++;
                        }
                    }
                }
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
    }

    public static void main(String[] args) {
        Thread latencyTesting = new Thread(new LatencyTesting());
        latencyTesting.start();

        Scanner sc = new Scanner(System.in);
        System.out.println("Press enter to exit! \n");
        sc.nextLine();
        sc.close();
        latencyTesting.interrupt();
        System.out.println("\n\nExiting...");
        System.exit(0);
    }
}

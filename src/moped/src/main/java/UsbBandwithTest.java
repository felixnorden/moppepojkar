import arduino.ArduinoCommunicator;
import arduino.ArduinoCommunicatorImpl;

public class UsbBandwithTest {
    static int counter = 0;

    static int i = -100;
    static int mod = 1;

    static long time;

    public static void main(String[] args){
        ArduinoCommunicator arduinoCommunicator = ArduinoCommunicatorImpl.getInstance();

        arduinoCommunicator.addArduinoListener(UsbBandwithTest::receivedString);

        while (true) {
            if (i < -99) {
                mod = 1;
            } else if (i > 99) {
                mod = -1;
                counter++;
                System.out.println(counter + " MAX REACHED");
//                long deltaTime = System.currentTimeMillis() - time;
//                System.out.println("DeltaTime: " + deltaTime);
//                time = System.currentTimeMillis();
//                System.out.println("Speed: " + deltaTime/20 + "/ms per action");
            }

            i += mod;

            System.out.println("Value: " + i);
            //arduinoCommunicator.write((byte) 0);
            arduinoCommunicator.write((byte) (i + 128));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static StringBuilder sb = new StringBuilder();

    static synchronized void receivedString(String s) {
        for (char c : s.toCharArray()) {
            if (c != 10 && c != 13) {
                sb.append(c);
            } else {
                if (sb.length() > 0) {
                    //System.out.println("Received: " + sb.toString());
                    sb = new StringBuilder();
                }
            }
        }
    }

}

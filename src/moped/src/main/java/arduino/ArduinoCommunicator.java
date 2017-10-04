package arduino;

import jssc.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArduinoCommunicator implements SerialPortEventListener {

    private final List<ArduinoListener> arduinoListeners;
    private SerialPort serialPort;

    private static ArduinoCommunicator instance;
    public static ArduinoCommunicator getInstance() {
        if (instance == null) {
            instance = new ArduinoCommunicator();

            List<String> portNames = instance.getAllPortNames();

            try {
                instance.connect(portNames.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private ArduinoCommunicator() {
        arduinoListeners = new ArrayList();
    }

    private void alertArduinoListeners(String s) {
        for (ArduinoListener arduinoListener : arduinoListeners) {
            arduinoListener.inputReceived(s);
        }
    }

    public void connect(String portName) throws Exception {
        serialPort = new SerialPort(portName);

        try {
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT
            );

            serialPort.addEventListener(this, SerialPort.MASK_RXCHAR);
        }
        catch (SerialPortException ex) {
            System.out.println("There are an error on writing string to port т: " + ex);
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                String receivedData = serialPort.readString(event.getEventValue());
                //System.out.print(receivedData);
                alertArduinoListeners(receivedData);
            }
            catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
    }

    public boolean isConnected() {
        return serialPort != null;
    }

    public void disconnect() {
        if (serialPort != null) {
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                // TODO: 03/10/2017 Handle exception
                e.printStackTrace();
            }
        }
    }

    public synchronized void write(byte data)  {
        try {
            serialPort.writeByte(data);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public synchronized void write(byte[] data) {
        try {
            serialPort.writeBytes(data);
        } catch (SerialPortException e) {
            // TODO: 03/10/2017 Handle exception
            e.printStackTrace();
        }
    }

    public String getConnectedPortName() {
        return serialPort.getPortName();
    }

    public List<String> getAllPortNames() {
        List<String> portNames = new ArrayList<>();

        portNames.addAll(Arrays.asList(SerialPortList.getPortNames()));

        if (portNames.size() == 0) {
            System.out.println("There are no serial-ports present");
         }

        return portNames;
    }

    public void addArduinoListener(ArduinoListener arduinoListener) {
        arduinoListeners.add(arduinoListener);
    }

    public void removeArduinoListener(ArduinoListener arduinoListener) {
        arduinoListeners.remove(arduinoListener);
    }

}

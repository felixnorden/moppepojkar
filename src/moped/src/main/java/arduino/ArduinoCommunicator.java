package arduino;

public interface ArduinoCommunicator {
    String getPortName();

    boolean write(byte data);
    boolean write(byte[] data);

    void addArduinoListener(ArduinoListener arduinoListener);
    void removeArduinoListener(ArduinoListener arduinoListener);

    boolean isConnected();
}

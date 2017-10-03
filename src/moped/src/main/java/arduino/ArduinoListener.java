package arduino;

/**
 * Interface for listening to the connected Arduino.
 *
 */
public interface ArduinoListener {

    void inputReceived(String s);

}

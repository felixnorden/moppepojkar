package core.process_runner;

/**
 * Subscriber interface for subscribers using data
 * from {@link ProcessRunner}
 */
public interface InputSubscriber {

    void receivedString(String string);

}

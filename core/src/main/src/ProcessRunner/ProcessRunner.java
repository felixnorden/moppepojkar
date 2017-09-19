package ProcessRunner;

import java.io.IOException;

/**
 * Created by Virtuality.
 * I/O Definition: Output is sent to the process and input is received from the process.
 */
public interface ProcessRunner {

    /**
     * Starts execution of the process.
     */
    public void start();

    /**
     * Stores the output that will be sent to the process in a buffer.
     * Use {@link ProcessRunner#flushOutput()} to send the buffer to the process.
     *
     * @param text String that will be output to the process.
     * @throws IOException Throws if process has yet to be started or if another output error has occurred.
     */
    public void outputToScript(String text) throws IOException;

    /**
     * Flushes the output buffer to the process.
     *
     * @throws IOException Throws if the process has yet to be started or if another output error has occurred.
     */
    public void flushOutput() throws IOException;

    /**
     * Force stops the process execution.
     * Once executed on the ProcessRunner-object, the ProcessRunner will be obsolete.
     */
    public void forceCloseProcess();

    /**
     * Subscribes an observer to receive input from the process.
     * Warning, all data that is output from the process will be sent,
     * including all echoes that the process will output from using the
     * {@link ProcessRunner#outputToScript(String)} method.
     *
     * @param observer Object that will receive the output.
     */
    public void subscribeToInput(InputSubscriber observer);

    /**
     * Unsubscribes an observer to stop receiving the input from the process's output.
     *
     * @param observer Object that will unsubscribe.
     */
    public void unsubscribeFromInput(InputSubscriber observer);
}

package ProcessRunner;

import java.io.IOException;

/**
 * Created by Virtuality.
 */
public interface ProcessRunner {

    /**
     * Starts execution of the process.
     */
    public void start();

    /**
     * Send output dynamically to the process.
     *
     * @param s String that will be outputted to the process.
     * @throws IOException Throws if process has yet to be started or if another output error has occurred.
     */
    public void outputToScript(String s) throws IOException;

    /**
     * Force stops the process execution.
     * This current object will be useless once this method has been run.
     */
    public void forceCloseProcess();

    /**
     * Adds an observer that will receive the output from the process.
     * Warning, all data that is outputted from the process will be sent,
     * including all echoes that the process will output from using the
     * {@link ProcessRunner#outputToScript(String)} method.
     *
     * @param observer Object that will receive the output.
     */
    public void addInputObserver(InputObserver observer);

    /**
     * Removes an observer to stop receiving the input from the process's output.
     *
     * @param observer Object that will stop receiving the input.
     */
    public void removeInputObserver(InputObserver observer);
}

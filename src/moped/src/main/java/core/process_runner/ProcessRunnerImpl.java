package core.process_runner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ProcessRunnerImpl} is used to run external processes from Java.
 * It can both write output to the process's input and receive its output as an input.
 * I/O Definition: Output is sent to the process and input is received from the process.
 */
public class ProcessRunnerImpl extends Thread implements ProcessRunner {

    private ProcessBuilder pb;
    private Process p;

    private List<InputSubscriber> inputSubscribers;
    private BufferedWriter writer;


    /**
     * Creates a {@link ProcessRunnerImpl} object, but does not start the execution of the process.
     * Use {@link ProcessRunnerImpl#start()} to start executing the process.
     *
     * @param pb The {@link ProcessBuilder} that will be used.
     */
    ProcessRunnerImpl(ProcessBuilder pb) {
        inputSubscribers = new ArrayList<>();
        this.pb = pb;
    }

    @Override
    public synchronized void start() {
        try {
            p = pb.start();
        } catch (IOException e) {
            System.out.println("--Process_Start_Error--");
            System.out.println(e.getMessage());
            System.out.println("-----------------------");
        }

        StreamReader reader = new StreamReader(p.getInputStream());
        reader.setOnInputRead(this::alertObservers);
        reader.start();
        writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public void outputToScript(String text) throws IOException {
        if (writer != null) {
            writer.write(text);
        } else {
            throw new IOException("Process has not yet been started!");
        }
    }

    @Override
    public void flushOutput() throws IOException {
        writer.flush();
    }

    @Override
    public void forceCloseProcess() {
        p.destroyForcibly();
    }

    @Override
    public void subscribeToInput(InputSubscriber observer) {
        inputSubscribers.add(observer);
    }

    @Override
    public void unsubscribeFromInput(InputSubscriber observer) {
        inputSubscribers.remove(observer);
    }

    /**
     * Alerts all of the current observers of the String that has been output from the process.
     *
     * @param s String that has been outputt from the process.
     */
    private void alertObservers(String s) {
        for (InputSubscriber inputSubscriber : inputSubscribers) {
            inputSubscriber.outputString(s);
        }
    }
}

package ProcessRunner;


import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Virtuality.
 */
class ProcessIntegrationTest {

    @Test
    void cmdReadTest() {
        // 1. Create process
        ProcessBuilder pb = new ProcessBuilder("cmd");
        ProcessRunner process = ProcessFactory.createCustomProcess(pb);

        InputReceiver receiver = new InputReceiver();
        process.addInputObserver(receiver);

        process.start();

        // 2. Wait for process to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        // 3. The first word should be "Microsoft"
        String lastReceived;
        StringBuilder sb = new StringBuilder();
        while ((lastReceived = receiver.getNextInput()) != null && !lastReceived.equals(" ")) {
            if (!lastReceived.equals("\n")) {
                sb.append(lastReceived);
            }
        }

        // First word should be "Microsoft"
        assert sb.toString().equals("Microsoft");
    }

    @Test
    void cmdWriteTest() {
        // 1. Create cmd process
        ProcessBuilder pb = new ProcessBuilder("cmd");
        ProcessRunner process = ProcessFactory.createCustomProcess(pb);

        InputReceiver receiver = new InputReceiver();
        process.addInputObserver(receiver);

        process.start();

        // 2. Wait for process to start
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }


        // 3. Skip beginning lines from cmd
        receiver.discardBufferedLinesReceived();


        // 4. Send "test"
        try {
            process.outputToScript("test");
        } catch (IOException ignored) {
        }

        // 5. Wait for command to process
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        // 6. Read the first word of the command output
        String lastReceived;
        StringBuilder sb = new StringBuilder();
        while ((lastReceived = receiver.getNextInput()) != null) {
            if (!lastReceived.equals("\n")) {
                sb.append(lastReceived);
            } else {
                break;
            }
        }

        // The first word read should be "test"
        assert sb.toString().equals("test");
    }

    @Test
    void pythonReadTest() throws IOException, InterruptedException {
        // 1. Create temporary Python file
        File pyScript = new File("pythonTemp.py");
        BufferedWriter bf;
        bf = new BufferedWriter(new FileWriter(pyScript));

        String[] pythonCode = {
                "import time\n" +
                        "\n",
                "while True:\n",
                "\tprint(\"test\")\n",
                "\ttime.sleep(0.05)\n",
        };

        bf.write("");
        for (String s : pythonCode) {
            bf.append(s);
            bf.flush();
        }

        // 2. Create Python process
        ProcessRunner process = ProcessFactory.createPythonProcess(pyScript.getAbsolutePath());

        InputReceiver receiver = new InputReceiver();
        process.addInputObserver(receiver);
        process.start();

        // 3. Wait for process to start
        Thread.sleep(500);

        // 3. Read the first word of the command output
        String lastReceived;
        StringBuilder sb = new StringBuilder();
        while (
                (lastReceived = receiver.getNextInput()) != null &&
                        !lastReceived.equals(" ") &&
                        !lastReceived.equals("\n")
                ) {
            sb.append(lastReceived);
        }

        // First word should be "test"
        assert sb.toString().equals("test");
    }

    @Test
    void pythonWriteTest() throws IOException, InterruptedException {
        // 1. Create temporary Python file
        File pyScript = new File("pythonTemp.py");
        BufferedWriter bf;
        bf = new BufferedWriter(new FileWriter(pyScript));

        String[] pythonCode = {
                "import time\n" +
                        "\n" +
                        "input(\"\")\n",
                "print(\"test\")\n",
        };

        bf.write("");
        for (String s : pythonCode) {
            bf.append(s);
            bf.flush();
        }

        // 2. Create Python process
        ProcessRunner process = ProcessFactory.createPythonProcess(pyScript.getAbsolutePath());

        InputReceiver receiver = new InputReceiver();
        process.addInputObserver(receiver);
        process.start();

        // 3. Wait for process to start
        Thread.sleep(250);

        // 4. Output "1" to the process
        process.outputToScript("1");

        // 5. Wait for process to handle the output
        Thread.sleep(250);

        // 6. Read the first output from the process
        String lastReceived;
        StringBuilder sb = new StringBuilder();
        while (
                (lastReceived = receiver.getNextInput()) != null &&
                        !lastReceived.equals(" ") &&
                        !lastReceived.equals("\n")
                ) {
            sb.append(lastReceived);
        }

        // First word should be test
        assert sb.toString().equals("test");
    }


    class InputReceiver implements InputObserver {

        private final List<String> receivedInputs;

        InputReceiver() {
            this.receivedInputs = new ArrayList<>();
        }

        @Override
        public void stringOutputted(String s) {
            synchronized (receivedInputs) {
                receivedInputs.add(s);
            }
        }

        String getNextInput() {
            String returningInput = null;
            synchronized (receivedInputs) {
                if (receivedInputs.size() > 0) {
                    returningInput = receivedInputs.get(0);
                    receivedInputs.remove(0);
                }
            }
            return returningInput;
        }

        private void discardBufferedLinesReceived() {
            while (true) {
                String nextInput = this.getNextInput();
                if (nextInput == null) {
                    break;
                }
            }
        }

    }

}

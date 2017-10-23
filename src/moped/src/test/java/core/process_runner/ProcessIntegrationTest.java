package core.process_runner;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Virtuality.
 */

class ProcessIntegrationTest {

    @AfterAll
    static void deleteTestFiles() {
        File processReadTest = new File("Test.java");
        if (processReadTest.exists()) {
            boolean delete = processReadTest.delete();
            if (!delete) {
                System.out.println("Couldn't delete: " + processReadTest.getAbsolutePath());
            }

        }

        File processReadCompile = new File("Test.class");
        if (processReadCompile.exists()) {
            boolean delete = processReadCompile.delete();
            if (!delete) {
                System.out.println("Couldn't delete: " + processReadTest.getAbsolutePath());
            }
        }
    }

    @Test
    void ProcessReadTest() throws IOException, InterruptedException {
        // 1. Create temporary Java file
        List<String> source = Arrays.asList("class Test{\n", "public static void main(String[] args) {\n",
                "System.out.println(\"Test\");\n",
                "}\n",
                "}\n");

        Path sourceFile = Paths.get("Test.java");
        Files.write(sourceFile, source, Charset.forName("UTF-8"));

        ProcessBuilder compilerProcess = new ProcessBuilder("javac", "Test.java");
        compilerProcess.start().waitFor();

        ProcessBuilder pb = new ProcessBuilder("java", "Test");

        ProcessRunner process = ProcessFactory.createCustomProcess(pb);

        InputSubscriberImpl receiver = new InputSubscriberImpl();
        process.subscribeToInput(receiver);
        process.start();

        Thread.sleep(500);

        // 6. Read the first word of the command output
        String lastReceived;
        StringBuilder sb = new StringBuilder();
        while ((lastReceived = receiver.getNextInput()) != null) {
            if (!lastReceived.equals("\n") && !lastReceived.equals("\r")) {
                sb.append(lastReceived);
            } else
                break;
        }

        // The first word read should be "test"
        assertEquals("Test", sb.toString());
    }


    static class InputSubscriberImpl implements InputSubscriber {

        private final List<String> buffer;

        InputSubscriberImpl() {
            this.buffer = new ArrayList<>();
        }

        @Override
        public void receivedString(String string) {
            synchronized (buffer) {
                buffer.add(string);
            }
        }

        String getNextInput() {
            String returningInput = null;
            synchronized (buffer) {
                if (buffer.size() > 0) {
                    returningInput = buffer.get(0);
                    buffer.remove(0);
                }
            }
            return returningInput;
        }

        private void clearBuffer() {
            synchronized (buffer) {
                while (buffer.size() > 0) {
                    buffer.remove(0);
                }
            }
        }

    }

}

package core.process_runner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

public class ProcessRunnerImplTest {

    @BeforeAll
    static void setup() throws IOException, InterruptedException {
        List<String> source = Arrays.asList("public class ProcessRunnerImplTestSource{ \npublic static void main(String[] args){", "while(true){", "java.util.Scanner sc = new java.util.Scanner(System.in);", "String input = sc.nextLine();", "if(input.equals(\"exit\")){ break; ", "}else{ System.out.print(input + \"123\"); } } }}");

        Path sourceFile = Paths.get("ProcessRunnerImplTestSource.java");
        Files.write(sourceFile, source, Charset.forName("UTF-8"));

        ProcessBuilder compilerProcess = new ProcessBuilder("javac", "ProcessRunnerImplTestSource.java");
        compilerProcess.start().waitFor();
    }

    @AfterAll
    static void cleanFiles() {
        File source = new File("ProcessRunnerImplTestSource.java");
        if (source.exists()) {
            boolean delete = source.delete();
            if (!delete) {
                System.out.println("Couldn't delete: " + source.getAbsolutePath());
            }
        }

        File compiled = new File("ProcessRunnerImplTestSource.class");
        if (compiled.exists()) {
            boolean delete = compiled.delete();
            if (!delete) {
                System.out.println("Couldn't delete: " + source.getAbsolutePath());
            }
        }
    }


    @Test
    void testProcessRunner() throws InterruptedException, IOException {
        List<String> received = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("java", "ProcessRunnerImplTestSource");
        ProcessRunnerImpl runner = new ProcessRunnerImpl(pb);

        InputSubscriber sub = new InputSubscriber() {
            @Override
            public void receivedString(String string) {
                received.add(string);
            }
        };

        runner.subscribeToInput(sub);
        runner.start();

        Thread.sleep(1000);

        runner.outputToScript("test\n");
        runner.flushOutput();

        Thread.sleep(1000);

        StringBuilder receivedString = new StringBuilder();
        for (String s : received) {
            receivedString.append(s);
        }

        assertEquals("test123", receivedString.toString());
    }
}
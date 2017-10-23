package core.process_runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Reader for handling the input stream of characters coming from
 * the run process in {@link ProcessRunner}
 */
class StreamReader extends Thread {

    private InputStream inputStream;
    private Consumer<String> onInputRead;

    StreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    @Override
    public void run() {
        try {
            readCharLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setOnInputRead(Consumer<String> consumer) {
        this.onInputRead = consumer;
    }


    private void readCharLoop() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        int receivedValue = 0;
        while (receivedValue != -1) {
            receivedValue = reader.read();
            if (receivedValue != 0) {
                onInputRead.accept(String.valueOf(((char)receivedValue)));
            }
        }
        System.out.println("StreamReader closed!");
    }
}

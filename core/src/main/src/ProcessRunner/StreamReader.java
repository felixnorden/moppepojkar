package ProcessRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * Created by Virtuality.
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
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);

        int receivedValue = 0;
        while (receivedValue != -1) {
            receivedValue = br.read();
            if (receivedValue != 0) {
                onInputRead.accept(String.valueOf(((char)receivedValue)));
            }
        }
        System.out.println("StreamReader closed!");
    }
}

package ProcessRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * Created by Virtuality.
 */

//Class is largely based on:
//https://stackoverflow.com/questions/14165517/processbuilder-forwarding-stdout-and-stderr-of-started-processes-without-blocki
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


    //Bug found, will only return a string when a \n has been read.
    private void readLineLoop() throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine()) != null)
            onInputRead.accept(line);
    }
}

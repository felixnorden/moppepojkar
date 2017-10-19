package core.process_runner;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamReaderTest {

    @Test
    void testOnDataRead() {
        final String data = "A";
        List<String> receivedData = new ArrayList<>();
        Consumer c = new Consumer(receivedData);

        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        StreamReader reader = new StreamReader(stream);
        reader.setOnInputRead(c::stringRecieved);
        reader.run();

        assertEquals(receivedData.get(0), data);
        assertEquals(receivedData.get(1), String.valueOf((char) -1));
    }

    private class Consumer {
        public List<String> received = new ArrayList<>();

        public Consumer(List<String> list){
            this.received = list;
        }

        void stringRecieved(String s) {
            received.add(s);
        }
    }
}
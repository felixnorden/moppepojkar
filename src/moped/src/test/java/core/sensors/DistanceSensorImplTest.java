package core.sensors;

import arduino.ArduinoCommunicator;
import arduino.ArduinoListener;
import com_io.CommunicationsMediator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DistanceSensorImplTest {

    private ArduinoCommunicator ardMock;
    private CommunicationsMediator comMock;

    private ArduinoListener arduinoListener;
    private DistanceSensorImpl sensorInstance;


    @Test
    void getDistance() {
    }

    @Test
    void getValue() {
    }

    @Test
    @DisplayName("should subscribe a consumer")
    void subscribe() {
        Consumer<Double> consumer = new ConsumerImpl();
        Consumer<Double> consumerSpy = spy(consumer);

        StringBuilder sb = new StringBuilder();
        sensorInstance.subscribe(consumerSpy);
        sensorInstance.receivedString("0.3\n", sb);

        verify(consumerSpy, times(1)).accept(any());
    }

    @Test
    void unsubscribe() {
    }

    @Test
    @DisplayName("should build string from input")
    void receivedString() {

        StringBuilder sb = new StringBuilder();
        sensorInstance.receivedString("100", sb);

        assertEquals("100", sb.toString());
    }

    @Test
    @DisplayName("should reset the StringBuilder on new line")
    void receivedString2() {
        StringBuilder sb = new StringBuilder();
        sensorInstance.receivedString("100\n", sb);

        assertEquals("", sb.toString());
    }

    @BeforeEach
    void setUp() {
        comMock = mock(CommunicationsMediator.class);
        ardMock = mock(ArduinoCommunicator.class);

        sensorInstance = new DistanceSensorImpl(comMock, ardMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
        assert sensorInstance != null;
    }

    class ConsumerImpl implements Consumer<Double>{
        Double d;

        @Override
        public void accept(Double aDouble) {
            this.d = 1.0;
        }
    }

}
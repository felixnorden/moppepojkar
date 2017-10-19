package core.sensors;

import arduino.ArduinoCommunicator;
import arduino.ArduinoListener;
import com_io.CommunicationsMediator;
import com_io.DataReceiver;
import com_io.Direction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static utils.Config.CAM_TGT_DIST;
import static utils.Config.LP_WEIGHT;

class DistanceSensorImplTest {

    private ArduinoCommunicator ardMock;
    private CommunicationsMediator comMock;

    private ArduinoListener arduinoListener;
    private DistanceSensorImpl sensorInstance;

    @Test
    void getDistance(){
        sensorInstance.receivedString("1.0\n",new StringBuilder());
        assertEquals(0.7,sensorInstance.getDistance(), 0.01);
    }

    @Test
    void getValue(){
        sensorInstance.receivedString("1.0\n",new StringBuilder());
        assertEquals(0.7,sensorInstance.getValue(), 0.01);
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
    @DisplayName("should unsubscribe a consumer")
    void unsubscribe() {
        Consumer<Double> consumer = new ConsumerImpl();
        Consumer<Double> consumerSpy = spy(consumer);

        StringBuilder sb = new StringBuilder();
        sensorInstance.subscribe(consumerSpy);
        sensorInstance.receivedString("0.3\n", sb);
        verify(consumerSpy, times(1)).accept(any());

        sensorInstance.unsubscribe(consumerSpy);
        sensorInstance.receivedString("0.3\n", sb);
        verify(consumerSpy, times(1)).accept(any());


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
        sensorInstance = new DistanceSensorImpl(comMock, ardMock, new LowPassFilter(LP_WEIGHT));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
        assert sensorInstance != null;
    }

    class ConsumerImpl implements Consumer<Double> {
        Double d;

        @Override
        public void accept(Double aDouble) {
            this.d = 1.0;
        }
    }

    @Test
    void testRecieveData() {
        CommunicationsMediator cm = new CommunicationsMediator() {

            private DataReceiver distanceSensor;

            @Override
            public void transmitData(String data, Direction direction) {
                distanceSensor.dataReceived(data);
            }

            @Override
            public void subscribe(Direction direction, DataReceiver receiver) {
                distanceSensor = receiver;
            }

            @Override
            public void unsubscribe(Direction direction, DataReceiver receiver) {

            }
        };

        DistanceSensor ds = new DistanceSensorImpl(cm, ardMock, new LowPassFilter(LP_WEIGHT));
        cm.transmitData(CAM_TGT_DIST + ",1.0\n", Direction.INTERNAL);
        /**
         * The lowPassFilter will return a value roughly equal to 70% of the value due to the weight(that is set to 0.7)
         */
        assertEquals(0.7, ds.getDistance(),0.01);
    }


}
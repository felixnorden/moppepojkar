package com_io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommunicationsMediatorImplTest {
    CommunicationsMediator comInstance;
    final String value = "Test";


    @BeforeEach
    void initMediator() {

        comInstance = CommunicatorFactory.getComInstance();
    }

    @Test
    @DisplayName("should transmit data to receivers")
    void transmitData() {
        final int[] called = new int[1];
        called[0] = 0;

        DataReceiver receiver1 = new DataReceiver() {
            int[] call = called;

            @Override
            public void dataReceived(String data) {
                call[0]++;
            }
        };
        DataReceiver receiver2 = new DataReceiver() {
            int[] call = called;

            @Override
            public void dataReceived(String data) {
                call[0]++;
            }
        };
        DataReceiver receiver3 = new DataReceiver() {
            int[] call = called;

            @Override
            public void dataReceived(String data) {
                call[0]++;
            }
        };

        comInstance.subscribe(Direction.INTERNAL, receiver1);
        comInstance.subscribe(Direction.INTERNAL, receiver2);
        comInstance.subscribe(Direction.INTERNAL, receiver3);

        // Act
        comInstance.transmitData(value, Direction.INTERNAL);

        // Asserts
        assertEquals(3, called[0]);
    }


    @Test
    @DisplayName("should add DataReceiver as a subscriber")
    void subscribe() {
        // Arrange
        DataReceiver receiver = new DataReceiver() {
            @Override
            public void dataReceived(String data) {
                assertEquals(value, data);
            }
        };

        comInstance.subscribe(Direction.INTERNAL, receiver);

        // Act & Assert
        comInstance.transmitData(value, Direction.INTERNAL);
    }



    @Test
    @DisplayName("should unsubscribe a subscribing receiver")
    void unsubscribe() {
        // Arrange
        DataReceiver receiver = new DataReceiver() {
            @Override
            public void dataReceived(String data) {
                assert false;
            }
        };

        comInstance.subscribe(Direction.INTERNAL, receiver);

        // Act
        comInstance.unsubscribe(Direction.INTERNAL, receiver);

        // Assert
        comInstance.transmitData(value, Direction.INTERNAL);

    }


    @Test
    @DisplayName("should contact subscribers on multiple channels")
    void subscribeInternalTest() {
        // Arrange
        final int[] called = new int[1];
        called[0] = 0;

        DataReceiver receiver = new DataReceiver() {
            int[] call = called;

            @Override
            public void dataReceived(String data) {
                call[0]++;
            }
        };

        comInstance.subscribe(Direction.INTERNAL, receiver);
        comInstance.subscribe(Direction.EXTERNAL, receiver);

        // Act
        comInstance.transmitData(value, Direction.EXTERNAL);
        comInstance.transmitData(value, Direction.INTERNAL);

        // Asserts
        assertEquals(2, called[0]);

    }

    @Test
    @DisplayName("should remove subscriber from designated channel")
    void unsubscribeInternalTest() {
        // Arrange
        final int[] called = new int[1];
        called[0] = 0;

        DataReceiver receiver = new DataReceiver() {
            int[] call = called;

            @Override
            public void dataReceived(String data) {
                call[0]++;
            }
        };

        comInstance.subscribe(Direction.INTERNAL, receiver);
        comInstance.subscribe(Direction.EXTERNAL, receiver);

        // Act
        comInstance.unsubscribe(Direction.INTERNAL, receiver);
        comInstance.transmitData(value, Direction.EXTERNAL);
        comInstance.transmitData(value, Direction.INTERNAL);

        // Asserts
        assertEquals(1, called[0]);
    }

}
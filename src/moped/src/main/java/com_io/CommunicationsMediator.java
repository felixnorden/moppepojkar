package com_io;

public interface CommunicationsMediator {

    void transmitData(int[] data, Direction direction);
    void subscribe(DataReceiver receiver, Direction direction);
    void unsubscribe(DataReceiver receiver);

}

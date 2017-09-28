package com_io;

public interface CommunicationsMediator {

    void transmitData(String data, Direction direction);
    void subscribe(Direction direction, DataReceiver receiver);
    void unsubscribe(Direction direction, DataReceiver receiver);

}

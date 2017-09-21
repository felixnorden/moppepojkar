package com_io;

import java.util.Map;

class CommunicationsMediatorImpl implements CommunicationsMediator{

    private Map<Direction, DataReceiver> subscribers;

    private void directPacket(int[] data, Direction direction){}

    @Override
    public void transmitData(int[] data, Direction direction) {

    }

    @Override
    public void subscribe(DataReceiver receiver, Direction direction) {

    }

    @Override
    public void unsubscribe(DataReceiver receiver) {

    }
}

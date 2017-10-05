package com_io;

import java.util.*;

/**
 * Mediator implementation of {@link CommunicationsMediator} for handling
 * subscribing {@link DataReceiver} instances and mediating data
 * between them
 */
class CommunicationsMediatorImpl implements CommunicationsMediator{

    private Map<Direction, List<DataReceiver>> subscribers;

    public CommunicationsMediatorImpl() {
        this.subscribers = new HashMap<>();

        this.subscribers.put(Direction.INTERNAL, new ArrayList<>());
        this.subscribers.put(Direction.EXTERNAL, new ArrayList<>());
    }

    @Override
    public void transmitData(String data, Direction direction) {
        for (DataReceiver dataReceiver : this.subscribers.get(direction)) {
            dataReceiver.dataReceived(data);
        }
    }

    @Override
    public void subscribe(Direction direction, DataReceiver receiver) {
        List<DataReceiver> dataReceivers = this.subscribers.get(direction);

        if(!dataReceivers.contains(receiver)) {
            dataReceivers.add(receiver);
        }
    }

    @Override
    public void unsubscribe(Direction direction, DataReceiver receiver) {
        int index = -1;

        List<DataReceiver> receivers = this.subscribers.get(direction);
        for (int i = 0; i < receivers.size(); i++) {
            DataReceiver dataReceiver = receivers.get(i);
            if (dataReceiver == receiver) {
                index = i;
            }
        }

        if(index != -1) {
            receivers.remove(index);
        }
    }
}

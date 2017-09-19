package com_io;

public interface CommunicationsMediator {

    public void transmitData(int[] data, Direction direction){}
    public void inputReciever(InputReciever reciever, Direction direction){}
    public void unsubscribe(Inputreciever reciever){}

}

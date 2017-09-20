package com_io;

import java.util.Map;

abstract class CommunicationsMediatorImpl implements CommunicationsMediator{

    private Map<Direction, DataReceiver> subscribers;

    private void directPacket(int[] data, Direction direction){}
}

package com_io;

/**
 * Directions for the transmitted data in the
 * {@link CommunicationsMediator#transmitData(String, Direction)}
 */
public enum Direction {
    /**
     * Data to be sent internally
     */
    INTERNAL,

    /**
     * Data to be sent externally
     */
    EXTERNAL
}

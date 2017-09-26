/*
 *   Copyright (c) 2017. Felix Nordén
 *   All rights reserved.
 *   This code may be used by other parties,
 *   as long as the used snippets are referenced to the author.
 *
 */

package core.sensors;

public interface Sensor {

    /**
     * Gives the current value of the sensor.
     * @return The current value of the sensor.
     */
    double getValue();

    /**
     * Kills any active thread running in the class.
     */
    void kill();
}

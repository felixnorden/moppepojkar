/*
 *   Copyright (c) 2017. Felix Nordén
 *   All rights reserved.
 *   This code may be used by other parties,
 *   as long as the used snippets are referenced to the author.
 *
 */

package core.sensors;

import java.util.function.Consumer;

public interface Sensor {

    /**
     * Gives the current value of the sensor.
     * @return The current value of the sensor.
     */
    double getValue();

    /**
     * Subscribes to the sensor data flow
     * @param dataConsumer to be subscribed
     */
    void subscribe(Consumer<Double> dataConsumer);

    /**
     * Unsubscribes to the sensor data flow
     * @param dataConsumer to be unsubscribed
     */
    void unsubscribe(Consumer<Double> dataConsumer);
}

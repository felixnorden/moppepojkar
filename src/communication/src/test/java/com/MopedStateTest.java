package com;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by konglobemeralt on 2017-09-14.
 */

public class MopedStateTest {
    @Test
    public void basicTest() {
        MopedDataPair pair = new MopedDataPair(MopedDataType.MopedState, MopedState.ACC.toInt());
        MopedDataPair pair1 = new MopedDataPair(MopedDataType.Velocity, 100);

        assertTrue(!pair.equals(pair1));

        assertTrue(pair.getType().equals(MopedDataType.MopedState));
        assertTrue(pair.getValue().equals(MopedState.ACC.toInt()));

        assertTrue(pair1.getType().equals(MopedDataType.Velocity));
        assertTrue(pair1.getValue() == 100);

        assertTrue(pair.hashCode() != pair1.hashCode());

        MopedDataPair pair2 = new MopedDataPair(MopedDataType.MopedState, MopedState.ACC.toInt());

        assertTrue(pair.hashCode() == pair2.hashCode());
    }
}
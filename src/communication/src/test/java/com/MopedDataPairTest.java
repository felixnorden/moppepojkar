package com;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Zack on 2017-09-26.
 */
public class MopedDataPairTest {
    @Test
    public void basicTest() {
        //Tests basic functionality and hashCode() + equals()
        MopedDataPair pair = new MopedDataPair(MopedDataType.MOPED_STATE, MopedState.ACC.toInt());
        MopedDataPair pair1 = new MopedDataPair(MopedDataType.VELOCITY, 100);

        assertTrue(!pair.equals(pair1));

        assertTrue(pair.getType().equals(MopedDataType.MOPED_STATE));
        assertTrue(pair.getValue().equals(MopedState.ACC.toInt()));

        assertTrue(pair1.getType().equals(MopedDataType.VELOCITY));
        assertTrue(pair1.getValue() == 100);

        assertTrue(pair.hashCode() != pair1.hashCode());

        MopedDataPair pair2 = new MopedDataPair(MopedDataType.MOPED_STATE, MopedState.ACC.toInt());

        assertTrue(pair.hashCode() == pair2.hashCode());
    }
}

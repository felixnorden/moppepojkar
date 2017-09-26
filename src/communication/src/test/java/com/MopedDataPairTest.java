package com;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Zack on 2017-09-26.
 */
public class MopedDataPairTest {
    @Test
    public void convert() {
        MopedState mopedState = MopedState.MANUAL;
        assertTrue(MopedState.parseInt(mopedState.toInt()) == mopedState);
        assertTrue(MopedState.parseInt(MopedState.values().length + 1) == null);
        assertTrue(MopedState.parseInt(-5) == null);
    }
}

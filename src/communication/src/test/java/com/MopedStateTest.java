package com;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by konglobemeralt on 2017-09-14.
 */

public class MopedStateTest {

    @Test
    public void convert() {
        MopedState mopedState = MopedState.MANUAL;
        assertTrue(MopedState.parseInt(mopedState.toInt()) == mopedState);
        assertTrue(MopedState.parseInt(MopedState.values().length + 1) == null);
        assertTrue(MopedState.parseInt(-5) == null);
    }


}
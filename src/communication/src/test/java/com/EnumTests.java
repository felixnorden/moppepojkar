package com;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by konglobemeralt on 2017-09-14.
 */

public class EnumTests {
    @Test
    public void MopedStateTest() {
        for (MopedState type : MopedState.values()) {
            assertEquals(type, MopedState.parseInt(type.toInt()));
        }

        assertTrue(MopedState.parseInt(MopedState.values().length + 1) == null);
    }

    @Test
    public void MopedDataTypeTest() {
        for (MopedDataType type : MopedDataType.values()) {
            assertEquals(type, MopedDataType.parseInt(type.toInt()));
        }

        assertEquals(MopedDataType.parseInt(MopedDataType.values().length + 1), null);
    }
}
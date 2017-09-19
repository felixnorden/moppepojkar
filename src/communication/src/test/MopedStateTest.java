package test;

import com.MopedState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by konglobemeralt on 2017-09-14.
 */

class MopedStateTest {

    @Test
    public void convert() {
        MopedState mopedState = MopedState.MANUAL;
        assertTrue(MopedState.parseInt(mopedState.toInt()) == mopedState);
        assertTrue(MopedState.parseInt(MopedState.values().length + 1) == null);
        assertTrue(MopedState.parseInt(-5) == null);
    }


}
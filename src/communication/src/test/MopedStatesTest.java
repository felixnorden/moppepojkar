package test;

import com.MopedStates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by konglobemeralt on 2017-09-14.
 */

class MopedStatesTest {

    @Test
    public void convert() {
        MopedStates mopedState = MopedStates.MANUAL;
        assertTrue(MopedStates.parseInt(mopedState.toInt()) == mopedState);
        assertTrue(MopedStates.parseInt(MopedStates.values().length + 1) == null);
        assertTrue(MopedStates.parseInt(-5) == null);
    }


}
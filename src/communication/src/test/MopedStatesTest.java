package test;

import com.MopedStates;
import org.junit.jupiter.api.Test;

/**
 * Created by konglobemeralt on 2017-09-14.
 */

class MopedStatesTest {

    @Test
    public void enumTest(){
       // System.out.println(MopedState.DrivingState.ACC.value);
       //  System.out.println(MopedState.foo.ordinal());
        System.out.println(MopedStates.ACC.getStateType());
        System.out.println(MopedStates.MANUAL.getStateType());
        System.out.println(MopedStates.MANUAL.ordinal());
        System.out.println(MopedStates.values()[MopedStates.MANUAL.ordinal()]);
    }

}
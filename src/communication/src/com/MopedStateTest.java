package com;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by konglobemeralt on 2017-09-14.
 */

class MopedStateTest {

    @Test
    public void enumTest(){
        System.out.println(MopedState.DrivingState.ACC.ordinal());
        System.out.println(MopedState.foo.ordinal());
    }

}
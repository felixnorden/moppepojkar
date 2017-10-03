package under_construction;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Emil Jansson on 2017-09-13.
 */
public class PreliminaryDistanceLogicTest {
    @Test
    public void enginePower1() {

        PreliminaryDistanceLogic logic = new PreliminaryDistanceLogic(1, 35, 2,4);

        logic.enginePower(35);
        int b = logic.enginePower(35);
        System.out.println(b);
        assertEquals(4, b);


        int a = logic.enginePower(40);
        System.out.println(a + "  " + b);
        assertTrue(a>b);
        try {Thread.sleep(2000);
        }catch (Exception e){}
        logic.enginePower(35);
        b = logic.enginePower(25);
        System.out.println(b);
        assertEquals(3,b);

    }



}

package under_construction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreliminaryDistanceLogicTest {
    @Test
    public void enginePower1() {

        PreliminaryDistanceLogic logic = new PreliminaryDistanceLogic(1, 35, 2, 4);

        logic.enginePower(35);
        int b = logic.enginePower(35);
        assertEquals(4, b);


        int a = logic.enginePower(40);
        assertTrue(a > b);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        logic.enginePower(35);
        b = logic.enginePower(25);
        assertEquals(3, b);

    }


}

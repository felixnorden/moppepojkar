package distancekeeper.distancekeepertest;



import distancekeeper.PreliminaryDistanceLogic;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Emil Jansson on 2017-09-13.
 */
public class PreliminaryDistanceLogicTest {
    @Test
    public void enginePower() {

        PreliminaryDistanceLogic logic = new PreliminaryDistanceLogic(1, 35);

        int a = logic.enginePower(35,0);
        int b = logic.enginePower(35,0);
        assertEquals(b, 0);

        a = logic.enginePower(40,0);
        try {Thread.sleep(2000);
        }catch (Exception e){}
        b = logic.enginePower(35, 5);
        //TODO test not completed
    }
}

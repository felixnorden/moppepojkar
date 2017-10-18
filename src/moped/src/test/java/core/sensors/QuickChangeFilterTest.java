package core.sensors;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Emil Jansson on 2017-10-17.
 */
public class QuickChangeFilterTest {
    final int maxQueueSize = 10;
    QuickChangeFilter filter = new QuickChangeFilter(10,maxQueueSize);

    @Test
    public void filterValue() {

        double[] dataSet = {1,2,3,17,5,6,-18,8,9,10};
        for (int i = 0; i < maxQueueSize; i++) {
            assertTrue(dataSet[i] == filter.filterValue(dataSet[i]));
        }

    }

    @Test
    public void filterValue1() {
        double[] dataSet = {1,2,3,50,5,6,-50,8,9,10};
        for (double d: dataSet) {
            assertTrue(filter.filterValue(d)<=10 && filter.filterValue(d)>=1);
        }
    }

    @Test
    public void filterValue2(){
        double[] zeroSet = {0,0,0,0,0,0,0,0,0,0};
        for(double d: zeroSet){
            filter.filterValue(d);
        }
        double[] giantSet = {100,100,100,100,100,100,100,100,100,100};
        for (int i = 0; i < maxQueueSize/2; i++){
            assertTrue(filter.filterValue(giantSet[i]) == 0);
        }
        for (int i = 0; i < maxQueueSize/2; i++){
            assertTrue(filter.filterValue(giantSet[i]) == 100);
        }
    }

}
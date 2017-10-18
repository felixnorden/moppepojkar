package core.sensors;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Emil Jansson on 2017-10-17.
 */
public class QuickChangeFilterTest {


    @Test
    public void filterValue() {
        final int maxQueueSize = 10;
        QuickChangeFilter filter = new QuickChangeFilter(10,maxQueueSize);
        double[] dataSet = {1,2,3,17,5,6,-18,8,9,10};
        for (int i = 0; i < maxQueueSize; i++) {
            assertTrue(dataSet[i] == filter.filterValue(dataSet[i]));
        }

    }

    @Test
    public void filterValue1() {
        final int maxQueueSize = 10;
        QuickChangeFilter filter = new QuickChangeFilter(10,maxQueueSize);
        double[] dataSet = {1,2,3,50,5,6,-50,8,9,10};
        for (int i = 0; i < maxQueueSize; i++) {
            assertTrue(dataSet[i] == filter.filterValue(dataSet[i]));
        }
        for (double d: dataSet) {
            double filteredValue = filter.filterValue(d);
            System.out.println("Raw: "+d+"  Filtered: " + filteredValue);
            assertTrue(filteredValue<=10 && filteredValue>=1);
        }
    }

    @Test
    public void filterValue2(){
        final int maxQueueSize = 10;
        QuickChangeFilter filter = new QuickChangeFilter(10,maxQueueSize);
        double[] zeroSet = {0,0,0,0,0,0,0,0,0,0};
        for(double d: zeroSet){
            double filteredValue = filter.filterValue(d);
            System.out.println("Raw: "+d+"  Filtered: " + filteredValue);
        }
        double[] giantSet = {20,20,20,20,20,20,20,20,20,20};
        for (int i = 0; i < maxQueueSize/2 +1; i++){
            double filteredValue = filter.filterValue(giantSet[i]);
            System.out.println("Raw: "+giantSet[i]+"  Filtered: " + filteredValue);
            assertTrue(filteredValue == 0);
        }
        for (int i = 0; i < maxQueueSize/2 -1; i++){
            double filteredValue = filter.filterValue(giantSet[i]);
            System.out.println("Raw: "+giantSet[i]+"  Filtered: " + filteredValue);
            assertTrue(filteredValue == 20);
        }
    }

}
package core.sensors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by amk19 on 26/09/2017.
 */
class LowPassFilterTest {

    LowPassFilter filter;

    @BeforeEach
    void setUp() {
        filter = new LowPassFilter(0.1);
    }

    @Test
    void positiveFilterValue() {
        // Arrange
        final double updatedValue = 10;

        filter.filterValue(updatedValue);

        // Act
        double value = filter.filterValue(updatedValue);

        // Assert
        assertEquals(1.9, value);
    }

    @Test
    void negativeFilterValue() {
        // Arrange
        filter.filterValue(100);

        // Act
        double value = filter.filterValue(1);

        // Assert
        assertEquals(9.1, value);
    }

    @Test
    void valueTesting() {
        for (int i = 0; i < 100; i++) {
            filter.filterValue(0.3);
        }

        filter.filterValue(0.7);

        for (int i = 0; i < 4; i++) {
            filter.filterValue(0.3);
        }

        double value = filter.filterValue(0.3);

        assertEquals(0.3,value,0.05);
    }

    @Test
    void extremeValueTesting() {
        for (int i = 0; i < 100; i++) {
            filter.filterValue(0.3);
        }

        filter.filterValue(0.7);


        double value = filter.filterValue(0.3);

        assertEquals(0.3,value,0.05);
    }

    @Test
    void noiseControl() {

        double[] arr = { 0.3, 0.35, 0.32, 0.28, 0.77, 0.3, 0.28, 0.34, 0.7};

        double temp = 0;
        for (double v : arr) {
            temp = filter.filterValue(v);
        }

        assertEquals(0.3, temp, 0.05);
    }
}
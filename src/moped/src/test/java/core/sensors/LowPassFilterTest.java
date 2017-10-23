package core.sensors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Double.NaN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LowPassFilterTest {

    LowPassFilter filter;

    @BeforeEach
    void setUp() {
        filter = new LowPassFilter(0.1);
    }

    @Test
    @DisplayName("should return positive values")
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
    @DisplayName("should return negative values")
    void negativeFilterValue() {
        // Arrange
        filter.filterValue(100);

        // Act
        double value = filter.filterValue(1);

        // Assert
        assertEquals(9.1, value);
    }

    @Test
    @DisplayName("should return values with a set error span")
    void valueTesting() {
        // Arrange
        for (int i = 0; i < 100; i++) {
            filter.filterValue(0.3);
        }
        filter.filterValue(0.7);

        // Act
        for (int i = 0; i < 4; i++) {
            filter.filterValue(0.3);
        }

        double value = filter.filterValue(0.3);

        // Assert
        assertEquals(0.3,value,0.05);
    }

    @Test
    @DisplayName("should handle noise spikes")
    void extremeValueTesting() {
        // Arrange
        for (int i = 0; i < 100; i++) {
            filter.filterValue(0.3);
        }
        filter.filterValue(0.7);

        // Act
        double value = filter.filterValue(0.3);

        // Assert
        assertEquals(0.3,value,0.05);
    }

    @Test
    @DisplayName("should balance incoming stream of noise")
    void noiseControl() {
        // Arrange
        double[] arr = {0.3,
                        0.35,
                        0.32,
                        0.28,
                        0.77,
                        0.3,
                        0.28,
                        0.34,
                        0.7};

        double temp = 0;

        // Act
        for (double v : arr) {
            temp = filter.filterValue(v);
        }

        // Assert
        assertEquals(0.3, temp, 0.05);
    }

    @Test
    @DisplayName("should handle NaN values without fail")
    void errorProneSensorValue() {
        // Arrange
        double arr[] =  {
                            NaN,
                            0.3,
                            0.56,
                            0.34,
                            0.78,
                            0.28,
                            NaN,
                            0.35,
                            NaN
                        };

        double value = filter.filterValue(0.37);

        // Act
        for (double reading : arr) {
            value = filter.filterValue(reading);
        }

        // Assert
        assertThat(value, not(NaN));
    }
}
package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by elias and marcus on 2017-09-12.
 */
public class StrToDoubleConverterTest {
    /**
     * Tests that the convertStringToDouble() method only accepts valid inputs and converts them to doubles
     */
    @Test
    public void convertDistanceTest(){
        StrToDoubleConverter strToDoubleConverter = new StrToDoubleConverter();
        assertEquals(Double.NaN, strToDoubleConverter.convertStringToDouble("ASD"),0.01);
        assertEquals(-1, strToDoubleConverter.convertStringToDouble("-1"), 0.01);
        assertEquals(23.3, strToDoubleConverter.convertStringToDouble("23.3"),0.01);
    }
}

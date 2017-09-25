import enginedataconversion.EngineDataConverter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EngineDataConverterTest {

    @Test
    void convertToMotorValue(){
        int [] motorValues = {1,2,3,4,5,6,7,8,9,10};
        EngineDataConverter EDC = new EngineDataConverter(motorValues);
        assertEquals(1,EDC.convertToMotorValue(1.2));
        assertEquals(1,EDC.convertToMotorValue(-5));
        assertEquals(5,EDC.convertToMotorValue(5.4));
        assertEquals(7, EDC.convertToMotorValue(6.5));

    }

}
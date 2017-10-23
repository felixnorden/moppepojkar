package utils;

import static java.lang.Double.NaN;

/**
 * The class is used to validate and convert Strings to doubles, while checking if the string
 *  is on the correct format,in order to make them usable for calculations.
 */
public class StrToDoubleConverter {

    /**
     * Validates and converts the input that is given.
     * @param input String containing only numbers and sign
     * @return The value in the form of a double
     */
    public double convertStringToDouble(String input) {
        if (input.matches("[0-9.-]+")) {
            double convertedDistance = Double.valueOf(input);
                return convertedDistance;
        } else {
            return NaN;
        }
    }
}

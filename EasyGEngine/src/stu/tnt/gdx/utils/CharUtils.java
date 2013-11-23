package stu.tnt.gdx.utils;

public class CharUtils {
	/**
     * <p>Converts the character to the Integer it represents, throwing an
     * exception if the character is not numeric.</p>
     * 
     * <p>This method coverts the char '1' to the int 1 and so on.</p>
     *
     * <pre>
     *   CharUtils.toIntValue('3')  = 3
     *   CharUtils.toIntValue('A')  throws IllegalArgumentException
     * </pre>
     *
     * @param ch  the character to convert
     * @return the int value of the character
     * @throws IllegalArgumentException if the character is not ASCII numeric
     */
    public static int toIntValue(char ch) {
        if (isAsciiNumeric(ch) == false) {
            throw new IllegalArgumentException("The character " + ch + " is not in the range '0' - '9'");
        }
        return ch - 48;
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit numeric.</p>
     *
     * <pre>
     *   CharUtils.isAsciiNumeric('a')  = false
     *   CharUtils.isAsciiNumeric('A')  = false
     *   CharUtils.isAsciiNumeric('3')  = true
     *   CharUtils.isAsciiNumeric('-')  = false
     *   CharUtils.isAsciiNumeric('\n') = false
     *   CharUtils.isAsciiNumeric('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 48 and 57 inclusive
     */
    public static boolean isAsciiNumeric(char ch) {
        return ch >= '0' && ch <= '9';
    }
}

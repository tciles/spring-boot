package fr.eni.demoSpringFramework.Utils;

public class NumberUtils {
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}

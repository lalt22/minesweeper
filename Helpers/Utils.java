package Helpers;

public class Utils {
    public static int[] getIntegerCoords(String[] splitCoords) {
        int[] coords = new int[2];
        for (int i = 0; i < splitCoords.length; i++) {
            //Try to parse int. If exception thrown, return null
            try {
                coords[i] = Integer.parseInt(splitCoords[i]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return coords;
    }
}

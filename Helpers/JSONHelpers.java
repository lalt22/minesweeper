package Helpers;

import org.json.simple.*;

public class JSONHelpers {

    public static int getRows(JSONObject jsonObject){
        long rows = (long) jsonObject.get("rows");
        return (int) rows;
    }


    public static int getCols(JSONObject jsonObject) {
        long cols = (long) jsonObject.get("cols");
        return (int) cols;
    }

    public static int getnumMines(JSONObject jsonObject) {
        long mines = (long) jsonObject.get("num_mines");
        return (int) mines;
    }
}

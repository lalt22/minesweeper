import Grid.*;
import Squares.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.stream.*;


public class Play {

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("=======Welcome To MineSweeper\"=======");
        System.out.println("=======To Play, Enter 2 whole numbers between 0 and 9. To exit game, enter \"EXIT\"=======");

        //Get the game config
        FileReader configReader = new FileReader("configure.json");
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(configReader);

        int numRows = getRows(jsonObject);
        int numCols = getCols(jsonObject);
        int numMines = getnumMines(jsonObject);

        if (numMines > numCols*numRows) {
            System.out.println("=======Too Many Mines. Please Reconfigure=======");
            return;
        }

        //Initialise the game and the grid
        Grid grid = new Grid(numRows, numCols, numMines);
        grid.showGrid();

        //While game is running, prompt for commands
        while (true) {
            System.out.println("=======Enter Command:=======");

            //READING INPUT AND PARSING
            BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
            String inString = inReader.readLine();
            //Checking if exit
            if (inString.equals("EXIT")) {
                System.out.println("=======Goodbye=======");
                break;
            }

            //If not exiting game, get the coordinates
            String[] splitCoords = inString.split(" ");
            int[] intCoords = getIntegerCoords(splitCoords);

            //Checking if input is invalid -> not enough inputs or parsing int throws error
            if (splitCoords.length < 2 || intCoords==null) {
                System.out.println("=======INVALID: Please Enter Two Integers=======");
                continue;
            }
            //Checking if input is invalid -> incorrect range
            if(IntStream.of(intCoords).anyMatch(val -> (val > 9) || (val < 0))) {
                System.out.println("=======INVALID: Please Enter Two Integers Between 0-9=======");
            }


            //If no issues, get the square
            Square selectedSquare = grid.getSquare(intCoords[0], intCoords[1]);
            //If already shown this square, give message
            if (selectedSquare.getRevealed()) {
                System.out.printf("=======You have seen this square before: %s, %s=======%n", intCoords[0], intCoords[1]);
            }
            selectedSquare.setRevealed();
            grid.showGrid();



            //If player loses, exit
            if (selectedSquare instanceof Mine) {
                System.out.printf("=======BOOM! YOU LOST! MINE AT %s, %s=======%n", intCoords[0], intCoords[1]);
                break;
            }
            //If player wins, exit
            if (grid.checkAllRevealed()) {
                System.out.println("=======YOU WON!=======");
                break;
            }
            System.out.println("There are " + grid.findMines(selectedSquare) + " mines around this square at " + intCoords[0] + ", " + intCoords[1]);
        }

    }

    protected static int[] getIntegerCoords(String[] splitCoords) {
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

    protected static int getRows(JSONObject jsonObject){
        long rows = (long) jsonObject.get("rows");
        return (int) rows;
    }


    protected static int getCols(JSONObject jsonObject) {
        long cols = (long) jsonObject.get("cols");
        return (int) cols;
    }

    protected static int getnumMines(JSONObject jsonObject) {
        long mines = (long) jsonObject.get("num_mines");
        return (int) mines;
    }



}

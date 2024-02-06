import Grid.*;
import Squares.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.stream.*;

import static Helpers.JSONHelpers.*;
import static Helpers.Utils.getIntegerCoords;


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

        //Check for invalid config
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

            //Cascading reveal
            grid.revealAdjacent(selectedSquare);
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
            System.out.println("There are " + selectedSquare.getMinesAround() + " mines around this square at " + intCoords[0] + ", " + intCoords[1]);
        }
    }
}

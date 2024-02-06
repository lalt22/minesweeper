import GameState.GameState;
import Grid.Grid;
import Squares.Square;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Play {

    public static void main(String[] args) throws IOException {
        System.out.println("=======Welcome To MineSweeper\"=======");
        System.out.println("=======To Play, Enter 2 whole numbers between 0 and 9. To exit game, enter \"EXIT\"=======");

        //Initialise the game and the grid
        GameState gameState = new GameState();
        Grid grid = new Grid();
        grid.showGrid();

        //While game is running, prompt for commands
        while (gameState.getGameState()) {
            System.out.println("=======Enter Command:=======");

            //READING INPUT AND PARSING
            BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
            String inString = inReader.readLine();
            //Checking if exit
            if (inString.equals("EXIT")) {
                System.out.println("=======Goodbye=======");
                gameState.exitGame();
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
                System.out.println(String.format("=======You have seen this square before: %s, %s=======", intCoords[0], intCoords[1]));
            }
            selectedSquare.setRevealed(gameState);




            //If player loses, exit
            if (!gameState.getGameState()) {
                grid.showGrid();
                System.out.println(String.format("=======BOOM! YOU LOST! MINE AT %s, %s=======", intCoords[0], intCoords[1]));
                break;
            }
            if (grid.checkAllRevealed()) {
                System.out.println(String.format("=======YOU WON!=======", intCoords[0], intCoords[1]));
                gameState.exitGame();
                break;
            }
            grid.showGrid();
            System.out.println("There are " + grid.findMines(selectedSquare) + " mines around this square at " + intCoords[0] + ", " + intCoords[1]);
        }

        //If player wins, exit


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


}

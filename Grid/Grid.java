package Grid;

import Squares.*;

import java.util.*;

public class Grid {
    Square[][] grid;
    int rows, cols;

    //Default init values
    public Grid() {
        this.grid = new Square[10][10];
        this.rows = 10;
        this.cols = 10;
        initialiseGrid();
        randomiseMines(10);
        initialiseMinesAround();
    }

    //Values given by config
    public Grid(int rows, int cols, int numMines) {
        this.grid = new Square[rows][cols];
        this.rows = rows;
        this.cols = cols;
        initialiseGrid();
        randomiseMines(numMines);
        initialiseMinesAround();
    }

    //Fill Grid with Squares
    protected void initialiseGrid() {
        for(int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.grid[i][j] = new Square(i, j);
            }
        }
    }

    //Disperse Mines randomly throughout Grid
    protected void randomiseMines(int numMines) {
        Random random = new Random();
        int countMines = 0;
        while (countMines < numMines) {
            int randX = random.nextInt(this.rows);
            int randY = random.nextInt(this.cols);

            //If there is already a mine there, don't redo and dont add to count
            if (!(getSquare(randX, randY) instanceof Mine)) {
                this.grid[randX][randY] = new Mine(randX, randY);
                countMines++;
            }
        }
    }

    //For each Square, check how many Mines are around it using findMines
    protected void initialiseMinesAround() {
        for (int x = 0; x < this.rows; x++) {
            for (int y = 0; y < this.cols; y++) {
                Square sq = getSquare(x, y);
                sq.setMinesAround(findMines(sq));
            }
        }
    }

    //Get number of Mines adjacent to Square (includes diagonal)
    public int findMines(Square square) {
        int mineCount = 0;
        Map<String, Integer> adjacents = adjacentIndices(square);

        int xCheckMin = adjacents.get("xCheckMin");
        int xCheckMax = adjacents.get("xCheckMax");
        int yCheckMin = adjacents.get("yCheckMin");
        int yCheckMax = adjacents.get("yCheckMax");

        for (int x = xCheckMin; x <= xCheckMax; x++) {
            for (int y = yCheckMin; y <= yCheckMax; y++) {
                if(getSquare(x, y) instanceof Mine) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }


    //Print Grid
    public void showGrid() {
        for (int i = 0; i < this.rows; i++) {
            System.out.println();
            for (int j = 0; j < this.cols; j++) {
                if (!(getSquare(i, j).getRevealed())) {
                    System.out.print(" * ");
                }
                else if (getSquare(i, j) instanceof Mine) {
                    System.out.print(" X ");
                }
                else {
                    System.out.print(" " + getSquare(i, j).getMinesAround() + " ");
                }
            }
        }
        System.out.println();
    }

    //Get Square
    public Square getSquare(int x, int y) {
        return this.grid[x][y];
    }

    //Check all non-Mine Squares revealed
    public boolean checkAllRevealed() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(!getSquare(i, j).getRevealed() && !(getSquare(i, j) instanceof Mine)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*Recursive method to cascade reveal empty squares.
    * Base Case: when Square has a Mine adjacent
    * Recursive Case: when a Square has no Mines adjacent
    */
    public void revealAdjacent(Square square) {
        if (square.getMinesAround() > 0) {
            square.setRevealed();
            return;
        }

        if (square.getMinesAround() == 0) {
            square.setRevealed();
            Map<String, Integer> adjacents = adjacentIndices(square);

            int xCheckMin = adjacents.get("xCheckMin");
            int xCheckMax = adjacents.get("xCheckMax");
            int yCheckMin = adjacents.get("yCheckMin");
            int yCheckMax = adjacents.get("yCheckMax");

            for (int x = xCheckMin; x <= xCheckMax; x++) {
                for (int y = yCheckMin; y <= yCheckMax; y++) {
                    if (!getSquare(x, y).getRevealed()) {
                        revealAdjacent(getSquare(x, y));
                    }
                }
            }
        }

    }


    //Map for adjacency checking values. Minimises code re-use
    private Map<String, Integer> adjacentIndices(Square square) {
        Map<String, Integer> adjacentIndices = new HashMap<>();

        int xCheckMin = square.getxCoordinate() - 1;
        int xCheckMax = square.getxCoordinate() + 1;
        int yCheckMin = square.getyCoordinate() - 1;
        int yCheckMax = square.getyCoordinate() + 1;

        //Check if square is on edge of grid. If yes, narrow the check ranges
        if (square.getxCoordinate() == 0) {
            xCheckMin = 0;
        }
        else if (square.getxCoordinate() == this.rows - 1) {
            xCheckMax = this.rows - 1;
        }
        if (square.getyCoordinate() == 0) {
            yCheckMin = 0;
        }
        else if (square.getyCoordinate() == this.rows - 1) {
            yCheckMax = this.rows - 1;
        }
        adjacentIndices.put("xCheckMin", xCheckMin);
        adjacentIndices.put("xCheckMax", xCheckMax);
        adjacentIndices.put("yCheckMin", yCheckMin);
        adjacentIndices.put("yCheckMax", yCheckMax);

        return adjacentIndices;
    }
}

package Grid;

import Squares.Mine;
import Squares.Square;

import java.util.Random;

public class Grid {
    Square[][] grid;
    int rows, cols;

    public Grid() {
        this.grid = new Square[10][10];
        this.rows = 10;
        this.cols = 10;
        initialiseGrid();
        randomiseMines(10);

    }

    public Grid(int rows, int cols, int numMines) {
        this.grid = new Square[rows][cols];
        this.rows = rows;
        this.cols = cols;
        initialiseGrid();
        randomiseMines(numMines);
        initialiseMinesAround();
    }

    protected void initialiseGrid() {
        for(int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.grid[i][j] = new Square(i, j);
            }
        }
    }


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

    protected void initialiseMinesAround() {
        for (int x = 0; x < this.rows; x++) {
            for (int y = 0; y < this.cols; y++) {
                Square sq = getSquare(x, y);
                sq.setMinesAround(findMines(sq));
            }
        }
    }

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

    public Square getSquare(int x, int y) {
        return this.grid[x][y];
    }

    public int findMines(Square square) {
        int mineCount = 0;
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

        for (int x = xCheckMin; x <= xCheckMax; x++) {
            for (int y = yCheckMin; y <= yCheckMax; y++) {
                if(getSquare(x, y) instanceof Mine) {
                    mineCount++;
                }
            }
        }


        return mineCount;
    }

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

    public void revealAdjacent(Square square) {
        System.out.println("CHECKING ADJACENT AT "+ square.getxCoordinate() + ", " + square.getyCoordinate());
        if (square.getMinesAround() > 0) {
            System.out.println("MINES AROUND NO RECURSION");
            square.setRevealed();
            return;
        }

        if (square.getMinesAround() == 0) {
            square.setRevealed();

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

            for (int x = xCheckMin; x <= xCheckMax; x++) {
                for (int y = yCheckMin; y <= yCheckMax; y++) {
                    if (!getSquare(x, y).getRevealed()) {
                        revealAdjacent(getSquare(x, y));
                    }

                }
            }
        }

    }
}

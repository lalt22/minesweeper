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
            if (!(this.grid[randX][randY] instanceof Mine)) {
                this.grid[randX][randY] = new Mine(randX, randY);
                countMines++;
            }
        }
    }

    public void showGrid() {
        for (int i = 0; i < this.rows; i++) {
            System.out.println();
            for (int j = 0; j < this.cols; j++) {
                if (!this.grid[i][j].getRevealed()) {
                    System.out.print(" * ");
                }
                else if (this.grid[i][j] instanceof Mine) {
                    System.out.print(" X ");
                }
                else {
                    System.out.print(" o ");
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
                if(this.grid[x][y] instanceof Mine) {
                    mineCount++;
                }
            }
        }


        return mineCount;
    }

    public boolean checkAllRevealed() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(!this.grid[i][j].getRevealed() && !(this.grid[i][j] instanceof Mine)) {
                    return false;
                }
            }
        }
        return true;
    }


}

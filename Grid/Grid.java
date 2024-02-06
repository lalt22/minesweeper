package Grid;

import Squares.Mine;
import Squares.Square;

import java.util.Random;

public class Grid {
    Square[][] grid;

    public Grid() {
        this.grid = new Square[10][10];
        initialiseGrid();
        randomiseMines();

    }

    protected void initialiseGrid() {
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.grid[i][j] = new Square(i, j);
            }
        }
    }

    protected void randomiseMines() {
        Random random = new Random();
        int numMines = 10;

        for (int i = 0; i < numMines; i++) {
            int randX = random.nextInt(10);
            int randY = random.nextInt(10);

            this.grid[randX][randY] = new Mine(randX, randY);
        }
    }

    public void showGrid() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
            for (int j = 0; j < 10; j++) {
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
        else if (square.getxCoordinate() == 9) {
            xCheckMax = 9;
        }
        if (square.getyCoordinate() == 0) {
            yCheckMin = 0;
        }
        else if (square.getyCoordinate() == 9) {
            yCheckMax = 9;
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!this.grid[i][j].getRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }


}

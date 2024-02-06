package Squares;

import GameState.GameState;

public class Square {
    int xCoordinate;
    int yCoordinate;

    boolean revealed;

    public Square(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.revealed = false;
    }

    public void setRevealed(GameState gameState) {
        this.revealed = true;
    }

    public boolean getRevealed() {
        return this.revealed;
    }

    public int getxCoordinate() {
        return this.xCoordinate;
    }

    public int getyCoordinate() {
        return this.yCoordinate;
    }

//
//    public String toString() {
//        if (!revealed) {
//            return "*";
//        }
//        else return "o";
//    }



}

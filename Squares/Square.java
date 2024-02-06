package Squares;

public class Square {
    private final int xCoordinate;
    private final int yCoordinate;

    boolean revealed;
    private int minesAround;

    public Square(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.revealed = false;
    }

    public void setRevealed() {
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

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public int getMinesAround() {
        return this.minesAround;
    }

//
//    public String toString() {
//        if (!revealed) {
//            return "*";
//        }
//        else return "o";
//    }



}

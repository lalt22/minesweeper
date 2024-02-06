package Squares;

public class Mine extends Square{
    public Mine(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
    }

    @Override
    public void setRevealed() {
        super.setRevealed();
    }

    @Override
    public boolean getRevealed() {
        return super.revealed;
    }
}

package Squares;

import GameState.GameState;

public class Mine extends Square{
    public Mine(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
    }

    @Override
    public void setRevealed(GameState gameState) {
        super.setRevealed(gameState);
        gameState.exitGame();
    }

    @Override
    public boolean getRevealed() {
        return super.revealed;
    }
}

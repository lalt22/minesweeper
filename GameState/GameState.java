package GameState;

public class GameState {
    boolean running;

    public GameState() {
        this.running = true;
    }

    public void exitGame() {
        this.running = false;
    }

    public boolean getGameState() {
        return this.running;
    }
}

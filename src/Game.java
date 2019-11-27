public interface Game {
    public abstract boolean isGameEnd();
    public abstract boolean isWin();
    public abstract boolean isLose();
    public abstract boolean nextTurn(String input);
    public abstract boolean printOneTurn();
}
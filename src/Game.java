import java.util.Scanner;

public interface Game {
    public abstract boolean initialize(int bet);
    public abstract boolean isGameEnd();
    public abstract boolean nextTurn(Scanner input);
    public abstract boolean printOneTurn();
    public abstract int cheapGain(int bet);
}
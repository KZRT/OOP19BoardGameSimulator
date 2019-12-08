package Casino.Game;
import java.util.Scanner;

public interface Game {
    /*
    Initialize whole game as turn 1.
    Return true if succeeded.
     */
    public abstract boolean initialize(int bet);

    /*
    Checks if the game is end or not.
    Return true if game is finished.
     */
    public abstract boolean isGameEnd();

    /*
    Gives Scanner to process your input for the next turn.
    Return true if the input is valid and game should be going on.
     */
    public abstract boolean nextTurn(Scanner input);

    /*
    Works as Display(), but only for one turn.
    Return true if one turn is fully printed, and next turn is needed.
    Return false if next turn is not needed.
     */
    public abstract boolean printOneTurn();

    /*
    Return how much chip player earn, or lose.
     */
    public abstract int chipGain(int bet);
}
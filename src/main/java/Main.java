import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameManager gm = new GameManager();
        int gameNum = 0;
    	do {
    		System.out.print("Which game to play? 1.Poker 2.BlackJack 0.Finish\n");
            Scanner input = new Scanner(System.in);
            gameNum = input.nextInt();
            gm.gametoPlay(gameNum);
    	} while (gameNum != 0);
        
    }
}

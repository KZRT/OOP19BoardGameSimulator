import java.util.Scanner;

public class GameManager {
    public void gametoPlay(int temp) {
        Game wholeGame = null;
        String gameName;
        switch (temp) {
            case 0:
                return;
            case 1:
                wholeGame = new Poker();
                gameName = "[Poker]";
                break;
            case 2:
                wholeGame = new Blackjack();
                gameName = "[Blackjack]";
                break;
            case 3:
                wholeGame = new Baccarat();
                gameName = "[Baccarat]";
                break;
            default:
                System.out.println("no such game");
                return;
        }

        int card = 0;
        System.out.println(gameName);
        System.out.print("How much will you bet: ");
        Scanner input = new Scanner(System.in);
        int bet = input.nextInt();
        input.nextLine();
        wholeGame.initialize(bet);
        while (!wholeGame.isGameEnd()) {
            System.out.flush();
            if (!wholeGame.printOneTurn())
                break;
            if (!wholeGame.nextTurn(input))
                break;
        }
        card += wholeGame.cheapGain(bet);
        System.out.println("\n(You won " + card + "!)\n");
    }
}

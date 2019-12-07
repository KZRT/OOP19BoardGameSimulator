import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Scanner;

public class GameManager {
    public void gametoPlay(int temp) {
        Game wholeGame = null;
        String gameName = "";
        Player player = Player.getInstance();

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
            case 4:
                try {
                    wholeGame = new Slot();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    break;
                }
                gameName = "[Slot]";
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
        while (bet <= 0 || bet > player.getWallet())
        {
            System.out.println("You can place bet between $1 and $" + NumberFormat.getInstance().format(player.getWallet()) + "\n");
            System.out.print("How much will you bet: ");
            bet = input.nextInt();
        }
        input.nextLine();
        player.payWallet(bet);
        wholeGame.initialize(bet);
        while (!wholeGame.isGameEnd()) {
            System.out.flush();
            if (!wholeGame.printOneTurn())
                break;
            if (!wholeGame.nextTurn(input))
                break;
        }
        card = wholeGame.cheapGain(bet);
        if (card > 0) {
            System.out.println("\n(You won $" + NumberFormat.getInstance().format(card) + "!)\n");
        }
        else {
            System.out.println("\n(You Lose)\n");
        }
        player.setWallet(player.getWallet() + card);
    }
}

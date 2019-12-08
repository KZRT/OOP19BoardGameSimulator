import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;
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
        case 5:
            wholeGame = new Taisai();
            gameName = "[Taisai]";
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

        int betAllowedRatio = 1;

        if (temp == 1)
            betAllowedRatio = 6;
        else if (temp == 2)
            betAllowedRatio = 4;

        int betLow = 1;
        while (bet < betLow || bet * betAllowedRatio > player.getWallet()) {
            System.out.println("You can place bet between $" + betLow + " and $"
                    + NumberFormat.getInstance().format(player.getWallet() / betAllowedRatio) + "\n");
            System.out.print("How much will you bet: ");
            bet = input.nextInt();
        }

        input.nextLine();

        wholeGame.initialize(bet);
        while (!wholeGame.isGameEnd()) {
            System.out.flush();
            if (!wholeGame.printOneTurn())
                break;
            if (!wholeGame.nextTurn(input))
                break;
        }

        card = wholeGame.cheapGain(bet);
        System.out
                .println("\n(Total Profit: " + (card > 0 ? "+" : "") + NumberFormat.getInstance().format(card) + ")\n");
    }
}

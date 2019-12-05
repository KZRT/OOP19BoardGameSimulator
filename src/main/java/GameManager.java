import java.util.Scanner;

public class GameManager {
    public void gametoPlay(int temp) {
        switch (temp) {
        case 0:
            break;
        case 1:
            poker();
            break;
        case 2:
            blackJack();
            break;
        case 3:
            baccarat();
            break;
        default:
            System.out.print("no such game");
        }
    }

    private void poker() {
        Poker a = new Poker();
        int card = 0;
        System.out.println("[Poker]");
        System.out.print("How much will you bet: ");
        Scanner input = new Scanner(System.in);
        int bet = input.nextInt();
        input.nextLine();
        a.initialize(bet);
        while (!a.isGameEnd()) {
            System.out.flush();
            if (!a.printOneTurn())
                break;
            if (!a.nextTurn(input))
                break;
        }
        card += a.cheapGain(bet);
        System.out.println("\n(You won " + card + "!)\n");
    }

    private void blackJack() {
        Blackjack b = new Blackjack();
        int card = 0;
        System.out.println("[BlackJack]");
        System.out.print("How much will you bet: ");
        Scanner input = new Scanner(System.in);
        int bet = input.nextInt();
        input.nextLine();
        b.initialize(bet);
        while (!b.isGameEnd()) {
            System.out.flush();
            if (!b.printOneTurn())
                break;
            if (!b.nextTurn(input))
                break;
        }
        card += b.cheapGain(bet);
        System.out.println("\n(You won " + card + "!)\n");
    }

    private void baccarat() {
        Baccarat c = new Baccarat();
        int card = 0;
        System.out.println("[Baccarat]");
        System.out.print("How much will you bet: ");
        Scanner input = new Scanner(System.in);
        int bet = input.nextInt();
        input.nextLine();
        c.initialize(bet);
        while (!c.isGameEnd()) {
            System.out.flush();
            if (!c.printOneTurn())
                break;
            if (!c.nextTurn(input))
                break;
        }
        card += c.cheapGain(bet);
        System.out.println("\n(You won " + card + "!)\n");
    }
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Poker a = new Poker();
        int card = 0;
        System.out.println("How much will you bet: ");
        Scanner input = new Scanner(System.in);
        int bet = input.nextInt();
        input.nextLine();
        a.initialize(bet);
        while(!a.isGameEnd())
        {
            System.out.flush();
            if(!a.printOneTurn()) break;
            if(!a.nextTurn(input)) break;
        }
        card += a.cheapGain(bet);
        System.out.print(card);
    }
}

import java.util.Scanner;

public class GameManager {
	public void gametoPlay(int temp) {
		switch (temp) {
		case 0:break;
		case 1:poker(); break;
		case 2:blackJack(); break;
		default: System.out.print("no such game");
		}
	}
	private void poker() {
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
	private void blackJack() {
		System.out.print("blackJack\n");
	}
}
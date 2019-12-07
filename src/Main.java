import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player player = Player.getInstance();
        GameManager gm = new GameManager();
        int gameNum = 0;
        do {
            clearScreen();

            System.out.println("====================");
            System.out.println("|    OOP Casino    |");
            System.out.println("====================");
            System.out.println("|   1. Poker       |");
            System.out.println("|   2. BlackJack   |");
            System.out.println("|   3. Baccarat    |");
            System.out.println("|   4. Slot        |");
            System.out.println("|   0. Finish      |");
            System.out.println("====================");

            if (player.getWallet() == 0) {
                System.out.println("\nYour money is $0.");
                System.out.println("You can't play game anymore.");
                System.out.println("Get out of our casino!");
                break;
            }

            System.out.println("(My Money: $" + NumberFormat.getInstance().format(player.getWallet()) + ")\n");
            System.out.print(">> ");
            Scanner input = new Scanner(System.in);
            gameNum = input.nextInt();
            input.nextLine();

            clearScreen();
            gm.gametoPlay(gameNum);

            if (gameNum != 0) {
                System.out.print("Press Enter to Continue...");
                input.nextLine();
            }
        } while (gameNum != 0);

    }

    public static void clearScreen() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++)
            builder.append('\n');
        System.out.println(builder.toString());
    }
}

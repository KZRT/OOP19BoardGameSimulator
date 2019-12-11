package Casino;

import Casino.dataClass.Color;
import Casino.dataClass.Player;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player player = Player.getInstance();
        GameManager gm = new GameManager();
        int gameNum;

        do {
            clearScreen();

            setOutputColor(Color.ANSI_BLUE);
            System.out.println("====================");
            System.out.println("|    OOP Casino    |");
            System.out.println("====================");
            System.out.println("|   1. Poker       |");
            System.out.println("|   2. BlackJack   |");
            System.out.println("|   3. Baccarat    |");
            System.out.println("|   4. Slot        |");
            System.out.println("|   5. Taisai      |");
            System.out.println("|   9. Exchange    |");
            System.out.println("|   0. Finish      |");
            System.out.println("====================");
            resetOutputColor();

            if (player.getWallet() == 0) {
                System.out.println("\nYour money is $0.");
                System.out.println("You can't play game anymore.");
                System.out.println("Get out of our casino!");
                break;
            }

            printMoney();

            System.out.print(">> ");
            Scanner input = new Scanner(System.in);
            gameNum = input.nextInt();
            input.nextLine();

            clearScreen();
            if (gameNum == 9) {
                printMoney();
                System.out.println("How much do you want to Charge?");
                System.out.print(">> ");
                int tempInt = input.nextInt();
                while (tempInt < 0) {
                    System.out.println("You should input more than 0");
                    System.out.print(">> ");
                    tempInt = input.nextInt();
                }
                player.setWallet(player.getWallet() + tempInt);
            } else gm.gametoPlay(gameNum);
            if (gameNum != 0) {
                System.out.print("Press Enter to Continue...");
                input.nextLine();
            }
        } while (gameNum != 0);
    }

    public static final boolean isWindows = System.getProperty("os.name")
            .toLowerCase().contains("win");

    public static void printMoney() {
        System.out.print("(My Money: ");
        setOutputColor(Color.ANSI_YELLOW);
        System.out.print('$' + NumberFormat.getInstance()
                .format(Player.getInstance().getWallet()));
        resetOutputColor();
        System.out.println(')');
    }

    public static void clearScreen() {
        if (isWindows) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 100; i++)
                builder.append('\n');
            System.out.println(builder.toString());
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void setOutputColor(Color color) {
        System.out.print(color);
    }

    public static void resetOutputColor() {
        setOutputColor(Color.ANSI_RESET);
    }
}
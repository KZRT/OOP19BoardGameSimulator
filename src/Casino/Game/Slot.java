package Casino.Game;

import Casino.Main;
import Casino.dataClass.Player;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class Slot implements Game {
    // 1배 20%
    // 5배 5%
    // 10배 1%
    // 20배 0.45%
    // 30배 0.3%
    // 75배 0.04%
    // 100배 0.02%
    // 5000배 0.0001%

    private int turn = 0;
    private int bet = 0;
    private GameResult result = GameResult.NONE;
    private boolean gameEnd = false;
    private String[] line = { "？", "？", "？" };
    private SecureRandom r;

    public Slot() throws NoSuchAlgorithmException {
        r = SecureRandom.getInstance("SHA1PRNG");
    }

    public boolean initialize(int bet) {
        this.bet = bet;
        turn = 1;
        result = makeBetResult();
        line = makeSlotPictures(result);
        return true;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public boolean nextTurn(Scanner input) {
        System.out.print("\nPress Enter to Continue..");
        input.nextLine();
        return true;
    }

    public boolean printOneTurn() {
        Main.clearScreen();
        String[] copyLine = Arrays.copyOf(line, line.length);
        for (int i = turn - 1; i < copyLine.length; i++) {
            copyLine[i] = "？";
        }
        this.print(copyLine);

        if (turn <= 3) {
            System.out.println("\n");
            turn++;
            return true;
        } else {
            gameEnd = true;

            if (result == GameResult.SEVEN) {
                System.out.println("\n"
                        + "::::::'##::::'###:::::'######::'##:::'##:'########:::'#######::'########:\n"
                        + ":::::: ##:::'## ##:::'##... ##: ##::'##:: ##.... ##:'##.... ##:... ##..::\n"
                        + ":::::: ##::'##:. ##:: ##:::..:: ##:'##::: ##:::: ##: ##:::: ##:::: ##::::\n"
                        + ":::::: ##:'##:::. ##: ##::::::: #####:::: ########:: ##:::: ##:::: ##::::\n"
                        + "'##::: ##: #########: ##::::::: ##. ##::: ##.....::: ##:::: ##:::: ##::::\n"
                        + " ##::: ##: ##.... ##: ##::: ##: ##:. ##:: ##:::::::: ##:::: ##:::: ##::::\n"
                        + ". ######:: ##:::: ##:. ######:: ##::. ##: ##::::::::. #######::::: ##::::\n"
                        + ":......:::..:::::..:::......:::..::::..::..::::::::::.......::::::..:::::");
            }

            return false;
        }
    }

    public int chipGain(int bet) {
        int profit = calcProfit();
        Player.getInstance().setWallet(Player.getInstance().getWallet() + profit);
        return profit;
    }

    private int calcProfit() {
        switch (result) {
        case NONE:
            return -bet;
        case ONE_CHERRY:
            return bet;
        case TWO_CHERRY:
            return bet * 5;
        case CHERRY:
            return bet * 10;
        case BAR:
            return bet * 20;
        case HEART:
            return bet * 30;
        case SPADE:
            return bet * 75;
        case STAR:
            return bet * 100;
        case SEVEN:
            return bet * 5000;
        }

        return 0;
    }

    private GameResult makeBetResult() {
        int[] p = { 200000, 50000, 10000, 4500, 3000, 400, 200, 1 };
        int roll = r.nextInt(1000000) + 1;

        if (bet == 777) {
            return GameResult.SEVEN;
        }

        int sum = 0;
        for (int i = 0; i < p.length; i++) {
            sum += p[i];
            if (roll <= sum) {
                return GameResult.values()[i + 1];
            }
        }

        return GameResult.NONE;
    }

    private String[] makeSlotPictures(GameResult result) {
        String[] symbols = { "♬", "Ｂ", "♥", "♠", "★", "７" };
        String[] line = { null, null, null };

        if (result == GameResult.CHERRY) {
            return new String[] { "♬", "♬", "♬" };
        }

        if (result == GameResult.BAR) {
            return new String[] { "Ｂ", "Ｂ", "Ｂ" };
        }

        if (result == GameResult.HEART) {
            return new String[] { "♥", "♥", "♥" };
        }

        if (result == GameResult.SPADE) {
            return new String[] { "♠", "♠", "♠" };
        }

        if (result == GameResult.STAR) {
            return new String[] { "★", "★", "★" };
        }

        if (result == GameResult.SEVEN) {
            return new String[] { "７", "７", "７" };
        }

        if (result == GameResult.ONE_CHERRY) {
            line[r.nextInt(line.length)] = "♬";

            for (int i = 0; i < line.length; i++) {
                if (line[i] != null)
                    continue;

                do {
                    line[i] = symbols[r.nextInt(symbols.length)];
                } while (line[i].equals("♬"));
            }
        }

        if (result == GameResult.TWO_CHERRY) {
            int idx = r.nextInt(line.length);
            do {
                line[idx] = symbols[r.nextInt(symbols.length)];
            } while (line[idx].equals("♬"));

            for (int i = 0; i < line.length; i++) {
                if (line[i] != null)
                    continue;

                line[i] = "♬";
            }
        }

        if (result == GameResult.NONE) {
            do {
                for (int i = 0; i < line.length; i++) {
                    do {
                        line[i] = symbols[r.nextInt(symbols.length)];
                    } while (line[i].equals("♬"));
                }
            } while (Arrays.stream(line).distinct().count() == 1);
        }

        return line;
    }

    private void print(String[] line) {
        System.out.println("========================");
        System.out.println("|       OOP Slot       |");
        System.out.println("========================");
        System.out.println("|  ７ ７ ７   : 5000x  |");
        System.out.println("|  ★ ★ ★   : 100x   |");
        System.out.println("|  ♠ ♠ ♠   : 75x    |");
        System.out.println("|  ♥ ♥ ♥   : 30x    |");
        System.out.println("|  Ｂ Ｂ Ｂ   : 20x    |");
        System.out.println("|  ♬ ♬ ♬   : 10x    |");
        System.out.println("|  ANY TWO ♬ : 5x     |");
        System.out.println("|  ANY ONE ♬ : 1x     |");
        System.out.println("========================");
        System.out.println("||      |      |      ||");
        System.out.println("||  " + line[0] + "  |  " + line[1] + "  |  " + line[2] + "  ||");
        System.out.println("||      |      |      ||");
        System.out.println("========================");
    }

    private enum GameResult {
        NONE, ONE_CHERRY, TWO_CHERRY, CHERRY, BAR, HEART, SPADE, STAR, SEVEN
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Baccarat implements Game {
    private Deck deck = new Deck(1);
    private BaccaratHands hands = new BaccaratHands();
    private ArrayList<Bet> bets = new ArrayList<>();
    private int turn = 0;
    private boolean gameEnd = false;

    public boolean initialize(int bet) {
        Scanner input = new Scanner(System.in);
        System.out.print("'P' to Bet on Player(x1), 'B' to Bet on Banker(x0.95): ");
        bets.add(new Bet((input.nextLine().trim().toUpperCase().equals("P") ? BetType.PLAYER : BetType.BANKER), bet));

        System.out.println("\n[BONUS BET]");
        System.out.print("Enter the amount to Bet on Tie(x8): ");
        int money = Integer.parseInt(input.nextLine());
        if (money > 0) {
            bets.add(new Bet(BetType.TIE, money));
        }

        if (bets.get(0).type == BetType.PLAYER) {
            System.out.print("Enter the amount to Bet on Banker Pair(x11): ");
            money = Integer.parseInt(input.nextLine());
            if (money > 0) {
                bets.add(new Bet(BetType.BANKER_PAIR, money));
            }
        } else {
            System.out.print("Enter the amount to Bet on Player Pair(x11): ");
            money = Integer.parseInt(input.nextLine());
            if (money > 0) {
                bets.add(new Bet(BetType.PLAYER_PAIR, money));
            }
        }

        turn = 1;
        deck.shuffleDeck();

        for (int i = 0; i < 2; i++) {
            hands.addCardToPlayer(deck.popOneCard());
            hands.addCardToBanker(deck.popOneCard());
        }

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
        System.out.println("=============================");
        System.out.println("[BANKER]");
        for (int i = 0; i < hands.sizeOfBanker(); i++) {
            hands.getBankerCard(i).print();
            if (i < hands.sizeOfBanker() - 1)
                System.out.print(" | ");
        }
        System.out.println("\n\n[PLAYER]");
        for (int i = 0; i < hands.sizeOfPlayer(); i++) {
            hands.getPlayerCard(i).print();
            if (i < hands.sizeOfPlayer() - 1)
                System.out.print(" | ");
        }
        System.out.println("\n");

        if (turn == 1) {
            // Player Turn
            if (hands.getSumOfPlayer() >= 8 || hands.getSumOfBanker() >= 8) {
                // natural
                System.out.println(">> NATURAL\n");
                gameEnd = true;
                printGameResult();
                return false;
            } else {
                if (hands.getSumOfPlayer() >= 6) {
                    // player stand
                    System.out.println(">> PLAYER STAND");
                    turn++;
                } else {
                    // player hit
                    System.out.println(">> PLAYER HIT");
                    hands.addCardToPlayer(deck.popOneCard());
                }
            }
        }

        if (turn == 2) {
            // Banker Turn
            if (hands.sizeOfPlayer() == 2) {
                // when player stand
                if (hands.getSumOfBanker() >= 6) {
                    // banker stand
                    System.out.println(">> BANKER STAND");
                    turn++;
                } else {
                    // banker hit
                    System.out.println(">> BANKER HIT");
                    hands.addCardToBanker(deck.popOneCard());
                }
            } else {
                // when player hit
                boolean[][] isStand = new boolean[10][10];

                isStand[3][8] = true;

                isStand[4][0] = true;
                isStand[4][1] = true;
                isStand[4][8] = true;
                isStand[4][9] = true;

                isStand[5][0] = true;
                isStand[5][1] = true;
                isStand[5][2] = true;
                isStand[5][3] = true;
                isStand[5][8] = true;
                isStand[5][9] = true;

                isStand[6][0] = true;
                isStand[6][1] = true;
                isStand[6][2] = true;
                isStand[6][3] = true;
                isStand[6][4] = true;
                isStand[6][5] = true;
                isStand[6][8] = true;
                isStand[6][9] = true;

                switch (hands.getSumOfBanker()) {
                case 0:
                case 1:
                case 2:
                    System.out.println(">> BANKER HIT");
                    hands.addCardToBanker(deck.popOneCard());
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    if (isStand[hands.getSumOfBanker()][hands.getPlayerLastCardValue()]) {
                        System.out.println(">> BANKER STAND");
                        turn++;
                    } else {
                        System.out.println(">> BANKER HIT");
                        hands.addCardToBanker(deck.popOneCard());
                    }
                    break;
                case 7:
                    System.out.println(">> BANKER STAND");
                    turn++;
                }
            }
        }

        if (turn == 3) {
            // last turn
            gameEnd = true;
            printGameResult();
            return false;
        }

        turn++;
        return true;
    }

    public int cheapGain(int bet) {
        return calcReward();
    }

    private void printGameResult() {
        System.out.println("[GAME RESULT]");

        if (hands.getSumOfPlayer() > hands.getSumOfBanker()) {
            System.out.println(">> PLAYER WIN (" + hands.getSumOfPlayer() + " > " + hands.getSumOfBanker() + ")");
        } else if (hands.getSumOfPlayer() < hands.getSumOfBanker()) {
            System.out.println(">> BANKER WIN (" + hands.getSumOfPlayer() + " < " + hands.getSumOfBanker() + ")");
        } else {
            System.out.println(">> TIE (" + hands.getSumOfPlayer() + " = " + hands.getSumOfBanker() + ")");
        }

        if (hands.getPlayerCard(0).equals(hands.getPlayerCard(1))) {
            System.out.println(">> PLAYER PAIR");
        }

        if (hands.getBankerCard(0).equals(hands.getBankerCard(1))) {
            System.out.println(">> BANKER PAIR");
        }
    }

    private int calcReward() {
        int result = 0;

        for (Bet bet : bets) {
            switch (bet.type) {
            case PLAYER:
                if (hands.getSumOfPlayer() > hands.getSumOfBanker()) {
                    result += bet.getReward();
                }
                break;
            case BANKER:
                if (hands.getSumOfPlayer() < hands.getSumOfBanker()) {
                    result += bet.getReward();
                }
                break;
            case TIE:
                if (hands.getSumOfPlayer() == hands.getSumOfBanker()) {
                    result += bet.getReward();
                }
                break;
            case PLAYER_PAIR:
                if (hands.getPlayerCard(0).equals(hands.getPlayerCard(1))) {
                    result += bet.getReward();
                }
                break;
            case BANKER_PAIR:
                if (hands.getBankerCard(0).equals(hands.getBankerCard(1))) {
                    result += bet.getReward();
                }
            }
        }

        return result;
    }

    private enum BetType {
        PLAYER, BANKER, TIE, PLAYER_PAIR, BANKER_PAIR
    }

    private class Bet {
        public BetType type;
        public int money;

        public Bet(BetType type, int money) {
            this.type = type;
            this.money = money;
        }

        public int getReward() {
            int result = money;

            switch (type) {
            case PLAYER:
                result += money;
                break;
            case BANKER:
                result += money * 0.95;
                break;
            case TIE:
                result += money * 8;
                break;
            case PLAYER_PAIR:
            case BANKER_PAIR:
                result += money * 11;
            }

            return result;
        }
    }
}

class BaccaratHands {
    private ArrayList<Card> player = new ArrayList<>();
    private ArrayList<Card> banker = new ArrayList<>();

    public Card getPlayerCard(int idx) {
        return player.get(idx);
    }

    public Card getBankerCard(int idx) {
        return banker.get(idx);
    }

    public int getPlayerLastCardValue() {
        if (!player.isEmpty()) {
            Card card = getPlayerCard(sizeOfPlayer() - 1);
            return (card.getNum() < 10 ? card.getNum() : 0);
        }

        return -1;
    }

    public int sizeOfPlayer() {
        return player.size();
    }

    public int sizeOfBanker() {
        return banker.size();
    }

    public void addCardToPlayer(Card card) {
        player.add(card);
    }

    public void addCardToBanker(Card card) {
        banker.add(card);
    }

    public int getSumOfPlayer() {
        return _getSumOfCards(player);
    }

    public int getSumOfBanker() {
        return _getSumOfCards(banker);
    }

    public void clear() {
        player.clear();
        banker.clear();
    }

    private int _getSumOfCards(ArrayList<Card> cards) {
        int result = 0;

        for (Card card : cards) {
            result += (card.getNum() < 10 ? card.getNum() : 0);
        }

        return result % 10;
    }
}
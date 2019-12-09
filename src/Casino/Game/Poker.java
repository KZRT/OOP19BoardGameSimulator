package Casino.Game;

import Casino.dataClass.Card;
import Casino.dataClass.Deck;
import Casino.dataClass.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Poker implements Game {
    private static final String blankCard = "? ?";
    private Deck pokerDeck;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> riverHand;
    private int betOnce, betMoney;
    private int turn;
    private boolean fold = false, tie = false;

    public Poker() {
        pokerDeck = new Deck(1);
        playerHand = new ArrayList<>(7);
        dealerHand = new ArrayList<>(7);
        riverHand = new ArrayList<>(7);
        betOnce = 0;
        betMoney = 0;
        turn = 0;
    }

    public boolean initialize(int bet) {
        betOnce = bet;
        betMoney = betOnce;

        turn = 1;
        pokerDeck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            playerHand.add(pokerDeck.popOneCard());
            dealerHand.add(pokerDeck.popOneCard());
        }
        for (int i = 0; i < 5; i++)
            riverHand.add(pokerDeck.popOneCard());
        return true;
    }

    public boolean isGameEnd() {
        return turn > 6;
    }

    public boolean nextTurn(Scanner input) {
        System.out.print("Y to Bet, N to fold: ");
        String tempChar = input.nextLine();
        if (tempChar.startsWith("Y") | tempChar.startsWith("y")) {
            betMoney += betOnce;
            return true;
        } else {
            fold = true;
            return false;
        }
    }

    public boolean printOneTurn() {
        if (turn == 1) {
            System.out.println("\t\t\t\t\t\tTurn " + turn);
            System.out.print("\t\t\t\tRiver:\t");
            for (int i = 0; i < 5; i++)
                System.out.print(blankCard + " ");
            System.out.println();
            System.out.println("\tDealer Hand: " + blankCard + " " + blankCard);
            System.out.print("\tYour Hand:\t ");
            playerHand.get(0).print();
            System.out.print(" ");
            playerHand.get(1).print();
            System.out.println();
            System.out.println("You Should Bet " + betOnce + " To Continue");
            turn++;
            return true;
        } else if (turn < 6) {

            System.out.println("\t\t\t\t\t\tTurn " + turn);

            System.out.print("\t\t\t\tRiver:\t");
            for (int i = 0; i < turn; i++) {
                riverHand.get(i).print();
                System.out.print("\t");
            }
            for (int i = turn; i < 5; i++)
                System.out.print(blankCard + " ");
            System.out.println();
            System.out.println("\tDealer Hand: " + blankCard + " " + blankCard);
            System.out.print("\tYour Hand:\t ");
            playerHand.get(0).print();
            System.out.print(" ");
            playerHand.get(1).print();
            System.out.println();
            System.out.println("You Should Bet " + betOnce + " To Continue");
            turn++;
            return true;
        } else if (turn == 6) {

            System.out.println("\t\t\t\t\t\tEnd of Game");
            dealerHand.addAll(riverHand);
            playerHand.addAll(riverHand);
            Collections.sort(dealerHand);
            Collections.sort(playerHand);

            System.out.print("\tDealer Hand: ");
            for (Card dealerCard : dealerHand) {
                dealerCard.print();
                System.out.print(" ");
            }
            System.out.println();

            System.out.print("\tYour Hand:\t ");
            for (Card playerCard : playerHand) {
                playerCard.print();
                System.out.print(" ");
            }
            System.out.println();

            final HandScorer dealerScore = new HandScorer(dealerHand);
            final HandScorer playerScore = new HandScorer(playerHand);
            final long finalDealerScore = dealerScore.getScore();
            final long finalPlayerScore = playerScore.getScore();

            if (finalDealerScore > finalPlayerScore) {
                System.out.println("\tDealer wins by " + dealerScore.getDescription());
                fold = true;
            } else if (finalDealerScore < finalPlayerScore) {
                System.out.println("\tYou wins by " + playerScore.getDescription());
            } else {
                System.out
                        .println("\tDraws by " + dealerScore.getDescription() + " and " + playerScore.getDescription());
                tie = true;
            }
            turn = 0;
            return false;
        }
        turn = 0;
        return false;
    }

    public int chipGain(int bet) {
        int profit;

        if (tie)
            profit = 0;
        else if (fold)
            profit = -betMoney;
        else
            profit = betMoney;

        Player.getInstance().setWallet(Player.getInstance().getWallet() + profit);
        return profit;
    }
}

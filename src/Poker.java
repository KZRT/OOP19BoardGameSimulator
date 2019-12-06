import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Poker implements Game {
    private Deck pokerDeck;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> riverHand;
    private int betMoney;
    private int turn;
    private static final String blankCard = "? ?";

    Poker() {
        pokerDeck = new Deck(1);
        playerHand = new ArrayList<>(7);
        dealerHand = new ArrayList<>(7);
        riverHand = new ArrayList<>(7);
        betMoney = 0;
        turn = 0;
    }

    public boolean initialize(int bet) {
        betMoney = bet;
        turn = 1;
        pokerDeck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            playerHand.add(pokerDeck.popOneCard());
            dealerHand.add(pokerDeck.popOneCard());
        }
        for (int i = 0; i < 5; i++) riverHand.add(pokerDeck.popOneCard());
        return true;
    }

    
    public boolean isGameEnd() {
        if (turn > 6) return true;
        return false;
    }

    public boolean nextTurn(Scanner input) {
        System.out.print("Y to Bet, N to fold: ");
        String tempChar = input.nextLine();
        if(tempChar.startsWith("Y")) return true;
        else
        {
            betMoney = 0;
            return false;
        }
    }

    
    public boolean printOneTurn() {
        if (turn == 1) {
            System.out.println("\t\t\t\t\t\tTurn " + turn);
            System.out.print("\t\t\t\tRiver:\t");
            for (int i = 0; i < 5; i++)
                System.out.print(blankCard + "\t");
            System.out.println();
            System.out.println("\tDealer Hand: " + blankCard + " " + blankCard);
            System.out.print("\tYour Hand:\t ");
            playerHand.get(0).print();
            System.out.print(" ");
            playerHand.get(1).print();
            System.out.println();
            System.out.println("You Should Bet " + betMoney + " To Continue");
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
                System.out.print(blankCard + "\t");
            System.out.println();
            System.out.println("\tDealer Hand: " + blankCard + " " + blankCard);
            System.out.print("\tYour Hand:\t ");
            playerHand.get(0).print();
            System.out.print(" ");
            playerHand.get(1).print();
            System.out.println();
            System.out.println("You Should Bet " + betMoney + " To Continue");
            turn++;
            return true;
        } else if (turn == 6) {

            System.out.println("\t\t\t\t\t\tEnd of Game");

            System.out.print("\t\t\t\tRiver:\t");
            for (int i = 0; i < 5; i++) {
                riverHand.get(i).print();
                System.out.print("\t");
            }
            System.out.println();
            System.out.print("\tDealer Hand: ");
            dealerHand.get(0).print();
            System.out.print(" ");
            dealerHand.get(1).print();
            System.out.println();
            System.out.print("\tYour Hand:\t ");
            playerHand.get(0).print();
            System.out.print(" ");
            playerHand.get(1).print();
            System.out.println();
            dealerHand.addAll(riverHand);
            playerHand.addAll(riverHand);
            final HandScorer dealerScore = new HandScorer(dealerHand);
            final HandScorer playerScore = new HandScorer(playerHand);
            final int finalDealerScore = dealerScore.getScore();
            final int finalPlayerScore = playerScore.getScore();
            if (finalDealerScore > finalPlayerScore) {
                System.out.println("\tDealer wins by " + dealerScore.getDescription());
                betMoney = 0;
            } else if (finalDealerScore < finalPlayerScore) {
                System.out.println("\tYou wins by " + playerScore.getDescription());
            } else 
                System.out.println("\tDraws by " + dealerScore.getDescription() + " and " + playerScore.getDescription());
            } 
            turn = 0;
            return false;
        } 
   

    public int cheapGain(int bet) {
        return betMoney * 6;
    }
}
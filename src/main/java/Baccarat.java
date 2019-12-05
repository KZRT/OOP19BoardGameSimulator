import java.util.ArrayList;
import java.util.Scanner;

public class Baccarat implements Game {
    private Deck deck = new Deck(1);
    private BaccaratHands hands;
    private int betMoney = 0;
    private int turn = 0;
    private boolean gameEnd = false;

    @Override
    public boolean initialize(int bet) {
        betMoney = bet;
        turn = 1;
        deck.shuffleDeck();

        for (int i = 0; i < 2; i++) {
            hands.addCardToPlayer(deck.popOneCard());
            hands.addCardToBanker(deck.popOneCard());
        }

        return true;
    }

    @Override
    public boolean isGameEnd() {
        return gameEnd;
    }

    @Override
    public boolean nextTurn(Scanner input) {
        System.out.print("Press Enter to Continue..");
        input.nextLine();
        return true;
    }

    @Override
    public boolean printOneTurn() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int cheapGain(int bet) {
        // TODO Auto-generated method stub
        return 0;
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

    public Card getPlayerLastCard() {
        return (!player.isEmpty() ? player.get(player.size() - 1) : null);
    }

    public Card getBankerLastCard() {
        return (!banker.isEmpty() ? banker.get(banker.size() - 1) : null);
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
            result += (card.getNum() < 10 ? card.getNum() : 10);
        }

        return result % 10;
    }
}
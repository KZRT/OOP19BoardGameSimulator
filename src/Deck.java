import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> currentDeck;
    private int deckCount;

    Deck() {
        this.deckCount = 1;
        this.currentDeck = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 13; k++) {
                this.currentDeck.add(new Card(k + 1, j));
            }
        }
    }

    Deck(int deckCount) {
        this.deckCount = deckCount;
        this.currentDeck = new ArrayList<>();
        for (int i = 0; i < deckCount; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 13; k++) {
                    this.currentDeck.add(new Card(k + 1, j));
                }
            }
        }
    }

    public boolean shuffleDeck() {
        currentDeck.clear();
        for (int i = 0; i < deckCount; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 13; k++) {
                    this.currentDeck.add(new Card(k + 1, j));
                }
            }
        }
        Collections.shuffle(currentDeck);
        return true;
    }

    public boolean isDeckEmpty() {
        return this.currentDeck.isEmpty();
    }

    public Card popOneCard() {
        if (isDeckEmpty()) {
            this.shuffleDeck();
            System.out.println("Deck Is Empty. Shuffling Deck Again");
        }
        return this.currentDeck.remove(0);
    }

    public boolean discardOneCard() {
        if (isDeckEmpty()) return false;
        this.currentDeck.remove(0);
        return true;
    }
}

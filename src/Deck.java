public class Deck {
    private Card[] currentDeck;
    private int deckCount;

    Deck(int deckCount) {
        this.deckCount = deckCount;
        for (int i = 0; i < deckCount; i++) {

        }
    }

    public boolean shuffleDeck() {
        return true;
    }

    public Card popOneCard() {
        return new Card();
    }

    public boolean isDeckEmpty() {
        return false;
    }
}

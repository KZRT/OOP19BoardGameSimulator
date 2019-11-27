public class Deck {
    private Card[] currentDeck;
    private int deckCount;

    Deck(int deckCount) {}

    public boolean shuffleDeck() {return true;}
    public Card popOneCard() {return new Card();}
    public boolean isDeckEmpty() {return false;}
}

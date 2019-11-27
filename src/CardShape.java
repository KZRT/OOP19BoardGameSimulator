public enum CardShape {
    SPADE("♠"), HEART("♥"), DIAMOND("♦"), CLOVE("♣");

    public final String label;

    private CardShape(String label) {
        this.label = label;
    }

    public void print() {
        System.out.print(this.label);
    }
}

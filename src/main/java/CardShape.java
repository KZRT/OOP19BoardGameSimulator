public enum CardShape {
    SPADE("¢¼"), HEART("¢¾"), DIAMOND("¡ß"), CLOVE("¢À");

    public final String label;

    private CardShape(String label) {
        this.label = label;
    }

    public void print() {
        System.out.print(this.label);
    }

}

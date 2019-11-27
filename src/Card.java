public class Card {
    private int num;
    private CardShape shape;

    Card() {
    }

    Card(int num, CardShape shape) {
        this.num = num;
        this.shape = shape;
    }

    public void print() {
        this.shape.print();
        System.out.print(" " + num);
    }
}

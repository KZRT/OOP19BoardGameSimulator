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
        System.out.print(" ");
        if (this.num > 10) {
            switch (this.num) {
                case 11:
                    System.out.print("J");
                    break;
                case 12:
                    System.out.print("Q");
                    break;
                case 13:
                    System.out.print("K");
                    break;
            }
        } else if (this.num == 1) {
            System.out.print("A");
        } else System.out.print(num);
    }

    public int getNum() {
        return this.num;
    }

    public CardShape getShape() {
        return this.shape;
    }
}

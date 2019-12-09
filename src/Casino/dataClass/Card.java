package Casino.dataClass;

import Casino.Main;

public class Card implements Comparable<Card> {
    private int num;
    private CardShape shape;

    Card() {
    }

    Card(int num, CardShape shape) {
        this.num = num;
        this.shape = shape;
    }

    Card(int num, int shape) {
        this.num = num;
        switch (shape) {
            case 0:
                this.shape = CardShape.SPADE;
                break;
            case 1:
                this.shape = CardShape.HEART;
                break;
            case 2:
                this.shape = CardShape.DIAMOND;
                break;
            case 3:
                this.shape = CardShape.CLOVE;
                break;
        }
    }

    public void print() {
        Main.setOutputColor(Color.ANSI_WHITE_BACKGROUND);
        if (shape == CardShape.SPADE || shape == CardShape.CLOVE)
            Main.setOutputColor(Color.ANSI_BLACK);
        else
            Main.setOutputColor(Color.ANSI_RED);

        shape.print();
        System.out.print(" ");
        if (num != 10) System.out.print(" ");
        if (num > 10) {
            switch (num) {
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
        } else if (num == 1) {
            System.out.print("A");
        } else
            System.out.print(num);

        Main.resetOutputColor();
    }

    public int getNum() {
        return this.num;
    }

    public CardShape getShape() {
        return this.shape;
    }

    @Override
    public int compareTo(Card o) {
        int tempThisNum = this.num;
        int tempObjectNum = o.num;
        if (this.num == 1)
            tempThisNum = 14;
        if (o.num == 1)
            tempObjectNum = 14;
        return tempObjectNum - tempThisNum;
    }
}

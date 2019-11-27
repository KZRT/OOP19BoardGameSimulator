public class Main {
    public static void main(String[] args) {
        Dice a = new Dice();
        for (int i = 0; i < 100; i++) {

            a.roll();
            a.print();
        }
    }
}

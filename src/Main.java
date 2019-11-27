public class Main {
    public static void main(String[] args){
        Deck a = new Deck();
        for(int i=0; i<123; i++){
            a.popOneCard().print();
            System.out.println();
        }
    }
}

import static java.lang.Math.*;

public class Dice {
    private int diceNum;
    Dice(){
        diceNum = 1;
    }
    public int roll()
    {
        this.diceNum = (int) (random() * 6) + 1;
        return this.diceNum;
    }
    public int getDiceNum() {return this.diceNum;}
    public void print() {System.out.print(this.diceNum);}
}

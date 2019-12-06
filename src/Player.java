import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Player {
    private int wallet;
    private int chipTotal;
    private ArrayList<Chip> chips;

    Player() {
        wallet = 0;
        chipTotal = 0;
        chips = new ArrayList<>();
    }

    Player(int amount) {
        wallet = amount;
        chipTotal = 0;
        chips = new ArrayList<>();
    }

    public int getWallet() {return this.wallet;}
    public void printWallet()
    {
        System.out.println("Player currently has " + wallet + "$");
        System.out.println("Player currently has " + chipTotal + "chips");
    }
    public int chipToWallet(int amount)
    {
        for(Chip chip: chips)
        {
            wallet += chip.getChipValue();
        }
        return wallet;
    }
    ///fuckfucksafdhadsugids

}

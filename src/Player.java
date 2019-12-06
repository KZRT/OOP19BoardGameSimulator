import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Player {
    private int wallet;
    private int chipTotal;
    private int[] chipCount;

    Player() {
        wallet = 0;
        chipTotal = 0;
        chipCount = new int[13];
    }

    Player(int amount) {
        wallet = amount;
        chipTotal = 0;
        chipCount = new int[13];
    }

    public int getWallet() {
        return this.wallet;
    }

    public void printWallet() {
        System.out.println("Player currently has " + wallet + "$");
        System.out.println("Player currently has " + chipTotal + "chips");
    }

    public int chipToWallet() {
        for (int i = 0; i < 13; i++) {
            wallet += Chip.indexToChipValue(i) * chipCount[i];
            chipCount[i] = 0;
        }
        chipTotal = 0;
        return wallet;
    }

    public int specificChipToWallet(int index) {
        wallet += Chip.indexToChipValue(index) * chipCount[index];
        chipTotal -= Chip.indexToChipValue(index) * chipCount[index];
        chipCount[index] = 0;
        return wallet;
    }

    public int walletToChip(int[] chipCounts) throws SecurityException{
        for(int i=12; i>=0; i++)
        {
            if(wallet - chipCounts[i] * Chip.indexToChipValue(i) < 0)
            {
                throw new SecurityException();
            }
            wallet -= chipCounts[i] * Chip.indexToChipValue(i);
            chipCount[i] += chipCounts[i];
            chipTotal += chipCounts[i] * Chip.indexToChipValue(i);
        }
        return chipTotal;
    }
}

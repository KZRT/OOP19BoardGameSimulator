package Casino.dataClass;

public class Player {
    private int wallet;
    private int chipTotal;
    private int[] chipCount;
    private static Player player = new Player(1000000);

    private Player() {
        wallet = 0;
        chipTotal = 0;
        chipCount = new int[13];
    }

    private Player(int amount) {
        wallet = amount;
        chipTotal = 0;
        chipCount = new int[13];
    }

    public static Player getInstance() {
        return player;
    }

    public int getWallet() {
        return this.wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void payWallet(int amount) {
        if (amount <= this.wallet) {
            this.wallet -= amount;
        } else {
            throw new RuntimeException("The amount in wallet is less than " + amount);
        }
    }

    public void printWallet() {
        System.out.println("Casino.dataClass.Player currently has " + wallet + "$");
        System.out.println("Casino.dataClass.Player currently has " + chipTotal + "chips");
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

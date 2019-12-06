public enum Chip {

    WHITE(1), YELLOW( 2), RED(5), BLUE(10), GREY(20), GREEN(25), ORANGE(50),
    BLACK(100), PINK(250), PURPLE(500), GOLD(1000), SKY(2000), BROWN(5000);

    private final int chipValue;


    Chip(int value) {
        this.chipValue = value;
    }

    public int getChipValue() {
        return this.chipValue;
    }

    public static int indexToChipValue(int index)
    {
        switch(index)
        {
            case 0: return WHITE.chipValue;
            case 1: return YELLOW.chipValue;
            case 2: return RED.chipValue;
            case 3: return BLUE.chipValue;
            case 4: return GREY.chipValue;
            case 5: return GREEN.chipValue;
            case 6: return ORANGE.chipValue;
            case 7: return BLACK.chipValue;
            case 8: return PINK.chipValue;
            case 9: return PURPLE.chipValue;
            case 10: return GOLD.chipValue;
            case 11: return SKY.chipValue;
            case 12: return BROWN.chipValue;
            default: return 0;
        }
    }
}

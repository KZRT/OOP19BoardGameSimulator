public enum Chip {

    WHITE(1), YELLOW(2), RED(5), BLUE(10), GREY(20), GREEN(25), ORANGE(50),
    BLACK(100), PINK(250), PURPLE(500), GOLD(1000), SKY(2000), BROWN(5000);

    private final int chipValue;

    Chip(int price) {
        this.chipValue = price;
    }

    public int getChipValue() {
        return this.chipValue;
    }
}

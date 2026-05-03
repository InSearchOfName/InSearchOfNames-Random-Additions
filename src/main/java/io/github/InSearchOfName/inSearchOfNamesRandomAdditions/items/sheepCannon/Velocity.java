package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon;

public enum Velocity {
    Low("Low", 2.5),
    Medium("Medium", 3.5),
    High("High", 5),
    UberCharged("UberCharged", 10);

    private final String DISPLAY_NAME;
    private final double multiplier;

    Velocity(String DISPLAY_NAME, double multiplier) {
        this.DISPLAY_NAME = DISPLAY_NAME;
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return DISPLAY_NAME;
    }
}

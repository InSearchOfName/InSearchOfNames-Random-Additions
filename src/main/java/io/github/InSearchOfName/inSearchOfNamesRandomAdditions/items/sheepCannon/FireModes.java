package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon;

public enum FireModes {
    Automatic("Automatic"),
    Burst("Burst"),
    SemiAutomatic("Semi-Automatic");

    private final String DISPLAY_NAME;

    FireModes(String DISPLAY_NAME) {
        this.DISPLAY_NAME = DISPLAY_NAME;
    }

    @Override
    public String toString() {
        return DISPLAY_NAME;
    }
}

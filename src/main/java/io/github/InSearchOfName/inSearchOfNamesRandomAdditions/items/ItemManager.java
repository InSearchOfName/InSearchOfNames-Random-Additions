package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannon;
import org.bukkit.Bukkit;

public class ItemManager {
    private final ColoredShears coloredShears;
    private final SheepCannon sheepCannon;

    @Inject
    public ItemManager(ColoredShears coloredShears, SheepCannon sheepCannon) {
        this.coloredShears = coloredShears;
        this.sheepCannon = sheepCannon;
    }

    public void registerItems() {
        Bukkit.addRecipe(coloredShears.getRecipe());
        Bukkit.addRecipe(sheepCannon.getRecipe());
    }
}

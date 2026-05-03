package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public interface ItemService {
    ItemStack create();

    void onClick(PlayerInteractEvent event);

    ShapedRecipe getRecipe();
}

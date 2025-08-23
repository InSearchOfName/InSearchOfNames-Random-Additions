package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface ItemService {
    ItemStack create();

    void onClick(PlayerInteractEvent event);
}

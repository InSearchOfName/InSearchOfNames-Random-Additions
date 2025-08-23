package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears;

import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public interface ColoredShearsService {
    ItemStack create();

    void changeColorOfShears(PlayerInteractEvent event);

    void onShear(PlayerShearEntityEvent event);

    void preventNaturallyDroppedWool(EntityDropItemEvent event);

    DyeColor getColorOfShears(ItemStack item);

    NamespacedKey getShearColorKey();

    Set<UUID> getRecentlySheared();
}

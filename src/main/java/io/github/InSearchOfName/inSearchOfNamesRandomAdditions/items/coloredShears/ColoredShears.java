package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ItemService;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public interface ColoredShears extends ItemService {

    void onShear(PlayerShearEntityEvent event);

    void preventNaturallyDroppedWool(EntityDropItemEvent event);

    void onCraft(PrepareItemCraftEvent event);

    DyeColor getColorOfShears(ItemStack item);

    NamespacedKey getShearColorKey();

    Set<UUID> getRecentlySheared();
}

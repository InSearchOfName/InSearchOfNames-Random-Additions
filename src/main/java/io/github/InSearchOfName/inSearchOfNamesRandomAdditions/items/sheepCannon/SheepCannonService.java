package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface SheepCannonService {
    ItemStack create();

    void onClick(PlayerInteractEvent event);

    void handleFire(Player player, FireModes mode, Velocity velocity);

    void shootSheep(Player player, Velocity velocity);

    NamespacedKey getKey();
}

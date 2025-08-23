package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ItemService;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public interface SheepCannon extends ItemService {

    void handleFire(Player player, FireModes mode, Velocity velocity);

    void shootSheep(Player player, Velocity velocity);

    NamespacedKey getKey();
}

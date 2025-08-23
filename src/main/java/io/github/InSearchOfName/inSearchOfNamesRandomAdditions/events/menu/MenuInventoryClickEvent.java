package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.menu;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis.MenuInventory;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MenuInventoryClickEvent implements Listener {
    private final ColoredShears coloredShears;
    private final SheepCannon sheepCannon;

    @Inject
    public MenuInventoryClickEvent(ColoredShears coloredShears, SheepCannon sheepCannon) {
        this.coloredShears = coloredShears;
        this.sheepCannon = sheepCannon;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof MenuInventory menuInventory)) {
            return;
        }

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item != null && item.hasItemMeta() && event.getWhoClicked() instanceof Player player) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            if (pdc.has(coloredShears.getShearColorKey(), PersistentDataType.INTEGER)) {
                ItemStack clone = item.clone();

                player.getInventory().addItem(clone);
                player.sendMessage("You received Colored Shears!");
            }
            if (pdc.has(sheepCannon.getKey(), PersistentDataType.BOOLEAN)) {
                ItemStack clone = item.clone();

                player.getInventory().addItem(clone);
                player.sendMessage("You received Sheep Cannon!");
            }
        }

    }
}

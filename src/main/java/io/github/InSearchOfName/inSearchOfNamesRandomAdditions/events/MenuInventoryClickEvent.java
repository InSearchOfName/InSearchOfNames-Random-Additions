package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis.MenuInventory;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MenuInventoryClickEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof MenuInventory menuInventory)) {
            return;
        }

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(
                ColoredShears.getShearColorKey(), PersistentDataType.INTEGER)) {

            // Give the player a copy of the item
            if (event.getWhoClicked() instanceof Player player) {
                ItemStack shearsCopy = item.clone();

                player.getInventory().addItem(shearsCopy);
                player.sendMessage("You received Colored Shears!");
            }
        }
    }
}

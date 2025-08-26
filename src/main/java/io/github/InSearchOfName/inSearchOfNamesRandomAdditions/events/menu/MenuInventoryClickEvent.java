package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.menu;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis.MenuInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuInventoryClickEvent implements Listener {
    private final MenuInventory menuInventory;

    @Inject
    public MenuInventoryClickEvent(MenuInventory menuInventory) {
        this.menuInventory = menuInventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MenuInventory menuInventory) {
            menuInventory.handleClick(event);
        }
    }

}

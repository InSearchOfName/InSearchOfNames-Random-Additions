package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ColoredShears;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MenuInventory implements InventoryHolder {
    private final Inventory inventory;

    public MenuInventory(InSearchOfNamesRandomAdditions plugin) {
        inventory = plugin.getServer().createInventory(this, 54, Component.text("Menu Inventory"));

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, ItemStack.of(Material.GRAY_STAINED_GLASS_PANE));
        }
        for (int i = 9; i < 50; i += 9) {
            inventory.setItem(i, ItemStack.of(Material.GRAY_STAINED_GLASS_PANE));
            inventory.setItem(i - 1, ItemStack.of(Material.GRAY_STAINED_GLASS_PANE));
        }
        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, ItemStack.of(Material.GRAY_STAINED_GLASS_PANE));
        }
        inventory.setItem(10, ColoredShears.createColoredShears());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}

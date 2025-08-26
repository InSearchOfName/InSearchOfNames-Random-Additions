package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MenuInventory implements InventoryHolder {
    private static final int SIZE = 54;
    private static final Material BORDER_MATERIAL = Material.GRAY_STAINED_GLASS_PANE;
    private static final Material NAV_MATERIAL = Material.FEATHER;
    private static final int[] CONTENT_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
    };
    private static final int SLOTS_PER_PAGE = CONTENT_SLOTS.length;
    private final ColoredShears coloredShears;
    private final SheepCannon sheepCannon;
    private final List<ItemStack> items = new ArrayList<>();
    private int page = 0;
    private Inventory inventory;

    @Inject
    public MenuInventory(ColoredShears coloredShears, SheepCannon sheepCannon) {
        this.coloredShears = coloredShears;
        this.sheepCannon = sheepCannon;

        // Example items
        items.add(coloredShears.create());
        items.add(sheepCannon.create());
        

        buildInventory();
    }

    private void buildInventory() {
        inventory = Bukkit.createInventory(this, SIZE,
                Component.text("Menu Inventory (Page " + (page + 1) + ")"));

        // borders
        for (int i = 0; i < 9; i++) inventory.setItem(i, createItem(BORDER_MATERIAL, ""));
        for (int row = 1; row <= 4; row++) {
            inventory.setItem(row * 9, createItem(BORDER_MATERIAL, ""));
            inventory.setItem(row * 9 + 8, createItem(BORDER_MATERIAL, ""));
        }
        for (int i = 45; i < 54; i++) inventory.setItem(i, createItem(BORDER_MATERIAL, ""));

        // items
        int start = page * SLOTS_PER_PAGE;
        int end = Math.min(start + SLOTS_PER_PAGE, items.size());

        int slotIndex = 0;
        for (int i = start; i < end; i++) {
            inventory.setItem(CONTENT_SLOTS[slotIndex++], items.get(i));
        }

        // nav buttons
        if (page > 0) inventory.setItem(45, createItem(NAV_MATERIAL, "Previous Page"));
        if (end < items.size()) inventory.setItem(53, createItem(NAV_MATERIAL, "Next Page"));
    }

    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (name != null && !name.isEmpty()) {
            meta.displayName(Component.text(name));
        } else {
            meta.displayName(Component.text(" ")); // default empty name (e.g. for borders)
        }
        item.setItemMeta(meta);
        return item;
    }

    private void nextPage(Player player) {
        page++;
        buildInventory();
        player.openInventory(inventory);
    }

    private void prevPage(Player player) {
        page--;
        buildInventory();
        player.openInventory(inventory);
    }

    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        String plainName = meta.hasDisplayName()
                ? PlainTextComponentSerializer.plainText().serialize(meta.displayName())
                : "";

        // nav
        if (plainName.equals("Previous Page") && page > 0) {
            prevPage(player);
            return;
        }
        if (plainName.equals("Next Page") && (page + 1) * SLOTS_PER_PAGE < items.size()) {
            nextPage(player);
            return;
        }

        // item actions
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        handleItemActions(player, pdc, item);
    }

    private void handleItemActions(Player player, PersistentDataContainer pdc, ItemStack item) {
        if (pdc.has(coloredShears.getShearColorKey(), PersistentDataType.INTEGER)) {
            player.getInventory().addItem(item.clone());
            player.sendMessage("You received Colored Shears!");
        }
        if (pdc.has(sheepCannon.getKey(), PersistentDataType.BOOLEAN)) {
            player.getInventory().addItem(item.clone());
            player.sendMessage("You received Sheep Cannon!");
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}

package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ColoredShears {
    private static final NamespacedKey SHEAR_COLOR_KEY = new NamespacedKey(InSearchOfNamesRandomAdditions.getPlugin(), "color_shears");
    private static final Set<UUID> recentlySheared = new HashSet<>();

    public static ItemStack createColoredShears() {
        ItemStack item = new ItemStack(Material.SHEARS);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Colored Shears").decorate(TextDecoration.BOLD));
        meta.getPersistentDataContainer().set(SHEAR_COLOR_KEY, PersistentDataType.INTEGER, DyeColor.WHITE.ordinal());

        meta.addEnchant(Enchantment.UNBREAKING,1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        updateMeta(item);
        return item;
    }

    public static void changeColorOfShears(PlayerInteractEvent event) {
        Boolean RightClick = null;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            RightClick = true;
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            RightClick = false;
        }

        Player player = event.getPlayer();
        if (player.getOpenInventory().getTopInventory().getType() != InventoryType.CRAFTING) return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (!pdc.has(SHEAR_COLOR_KEY, PersistentDataType.INTEGER)) return;

        Integer ordinal = pdc.get(SHEAR_COLOR_KEY, PersistentDataType.INTEGER);
        if (ordinal == null) return;

        if (RightClick == null) return;
        else if (RightClick) {
            ordinal++;
            if (ordinal >= DyeColor.values().length) ordinal = 0;
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
        } else {
            ordinal--;
            if (ordinal < 0) ordinal = DyeColor.values().length - 1;

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.5f); // Chipmunk tone
        }

        pdc.set(SHEAR_COLOR_KEY, PersistentDataType.INTEGER, ordinal);
        item.setItemMeta(meta);
        updateMeta(item);

    }


    private static void updateMeta(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        Integer ordinal = pdc.get(SHEAR_COLOR_KEY, PersistentDataType.INTEGER);

        if (ordinal == null || ordinal < 0 || ordinal >= DyeColor.values().length) return;

        DyeColor color = DyeColor.values()[ordinal];

        // Create a lore line with the selected color
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("When shearing a sheep no matter the color of the wool,").color(TextColor.color(0,170,170)));
        lore.add(Component.text("it will change into the color of the Colored Shears.").color(TextColor.color(0,170,170)));

        lore.add(Component.text("Selected Color: ")
                .append(
                        Component.text(color.name().toUpperCase()).color(TextColor.color(color.getColor().asRGB()))
                ).decorate(TextDecoration.BOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Right-Click: Next Color").decorate(TextDecoration.BOLD).color(TextColor.color(255,170,0)));
        lore.add(Component.text("Left-Click: Previous Color").decorate(TextDecoration.BOLD).color(TextColor.color(255,170,0)));

        // Set the lore
        meta.lore(lore);

        // Save the updated meta back to the item
        item.setItemMeta(meta);
    }

    public static void onShear(PlayerShearEntityEvent event) {
        if (!(event.getEntity() instanceof Sheep sheep)) return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() != Material.SHEARS) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(SHEAR_COLOR_KEY, PersistentDataType.INTEGER)) return;

        event.setCancelled(true);

        // Mark this sheep so we can block the vanilla drop
        recentlySheared.add(sheep.getUniqueId());

        sheep.shear();

        int amount = new Random().nextInt(3) + 1;
        ItemStack wool = new ItemStack(Material.valueOf(getColorOfShears(item) + "_WOOL"), amount);

        sheep.getWorld().dropItemNaturally(sheep.getLocation(), wool);
    }

    public static void preventNaturallyDroppedWool(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof Sheep sheep)) return;

        if (recentlySheared.contains(sheep.getUniqueId())){
            recentlySheared.remove(sheep.getUniqueId());
            event.setCancelled(true);
        }

    }

    private static DyeColor getColorOfShears(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (!pdc.has(SHEAR_COLOR_KEY, PersistentDataType.INTEGER)) return null;

        Integer ordinal = pdc.get(SHEAR_COLOR_KEY, PersistentDataType.INTEGER);
        if (ordinal == null || ordinal < 0 || ordinal >= DyeColor.values().length) return null;

        return DyeColor.values()[ordinal];
    }

    public static NamespacedKey getShearColorKey() {
        return SHEAR_COLOR_KEY;
    }
}

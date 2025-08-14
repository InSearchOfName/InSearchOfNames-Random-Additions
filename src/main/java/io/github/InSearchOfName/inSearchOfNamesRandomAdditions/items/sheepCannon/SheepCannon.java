package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SheepCannon {
    private static final NamespacedKey FIRE_MODE_KEY = new NamespacedKey(InSearchOfNamesRandomAdditions.getPlugin(), "fire_mode");
    private static final NamespacedKey SHEEP_CANNON_KEY = new NamespacedKey(InSearchOfNamesRandomAdditions.getPlugin(), "sheep_cannon");
    private static final NamespacedKey VELOCITY_KEY = new NamespacedKey(InSearchOfNamesRandomAdditions.getPlugin(), "velocity");

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Sheep Cannon").decorate(TextDecoration.BOLD));
        meta.getPersistentDataContainer().set(SHEEP_CANNON_KEY, PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(FIRE_MODE_KEY, PersistentDataType.INTEGER, FireModes.Automatic.ordinal());
        meta.getPersistentDataContainer().set(VELOCITY_KEY, PersistentDataType.INTEGER, Velocity.Low.ordinal());

        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        updateMeta(item);
        return item;
    }

    private static void updateMeta(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        boolean isSheepCannon = Boolean.TRUE.equals(pdc.get(SHEEP_CANNON_KEY, PersistentDataType.BOOLEAN));
        Integer fireModeOrdinal = pdc.get(FIRE_MODE_KEY, PersistentDataType.INTEGER);
        Integer velocityOrdinal = pdc.get(VELOCITY_KEY, PersistentDataType.INTEGER);
        if (!isSheepCannon || fireModeOrdinal == null || velocityOrdinal == null) return;
        FireModes fireMode = FireModes.values()[fireModeOrdinal];
        Velocity velocity = Velocity.values()[velocityOrdinal];


        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Shoots a silly sheep ;)").color(TextColor.color(0, 170, 170)));
        lore.add(Component.text("Left-Click: Shoot da sheep").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0)));
        lore.add(Component.empty());
        lore.add(
                Component.text("Right-Click: Fire-mode ").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0))
                        .append(Component.text(fireMode.toString()).decorate(TextDecoration.BOLD).color(TextColor.color(253, 253, 84)))
        );
        lore.add(Component.empty());
        lore.add(
                Component.text("Shift-Right-Click: Velocity ").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0))
                        .append(Component.text(velocity.toString()).color(TextColor.color(253, 253, 84)))
        );


        meta.lore(lore);
        item.setItemMeta(meta);
    }


}

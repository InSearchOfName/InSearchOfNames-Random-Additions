package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ColoredShearsServiceImpl implements ColoredShearsService {
    private final InSearchOfNamesRandomAdditions plugin;
    private final NamespacedKey SHEAR_COLOR_KEY;
    private final Set<UUID> recentlySheared = new HashSet<>();

    @Inject
    public ColoredShearsServiceImpl(InSearchOfNamesRandomAdditions plugin) {
        this.plugin = plugin;
        this.SHEAR_COLOR_KEY = new NamespacedKey(plugin, "color_shears");
    }

    @Override
    public ItemStack create() {
        ItemStack item = new ItemStack(Material.SHEARS);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Colored Shears").decorate(TextDecoration.BOLD));
        meta.getPersistentDataContainer().set(SHEAR_COLOR_KEY, PersistentDataType.INTEGER, DyeColor.WHITE.ordinal());

        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        updateMeta(item);
        return item;
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        Boolean rightClick = null;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            rightClick = true;
        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            rightClick = false;
        }
        if (rightClick == null) return;

        Player player = event.getPlayer();
        InventoryView openInv = player.getOpenInventory();
        Inventory topInv = openInv.getTopInventory();

        if (!(topInv.getType() == InventoryType.CRAFTING && topInv.getHolder() instanceof Player)) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.SHEARS || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(SHEAR_COLOR_KEY, PersistentDataType.INTEGER)) return;

        Integer ordinal = pdc.get(SHEAR_COLOR_KEY, PersistentDataType.INTEGER);
        if (ordinal == null) return;

        if (rightClick) {
            ordinal = (ordinal + 1) % DyeColor.values().length;
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
        } else {
            ordinal = (ordinal - 1 + DyeColor.values().length) % DyeColor.values().length;
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.5f);
        }

        pdc.set(SHEAR_COLOR_KEY, PersistentDataType.INTEGER, ordinal);
        item.setItemMeta(meta);
        updateMeta(item);
        player.getInventory().setItemInMainHand(item);
    }

    private void updateMeta(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        Integer ordinal = pdc.get(SHEAR_COLOR_KEY, PersistentDataType.INTEGER);

        if (ordinal == null || ordinal < 0 || ordinal >= DyeColor.values().length) return;

        DyeColor color = DyeColor.values()[ordinal];

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("When shearing a sheep no matter the color of the wool,").color(TextColor.color(0, 170, 170)));
        lore.add(Component.text("it will change into the color of the Colored Shears.").color(TextColor.color(0, 170, 170)));

        lore.add(Component.text("Selected Color: ")
                .append(
                        Component.text(color.name().toUpperCase()).color(TextColor.color(color.getColor().asRGB()))
                ).decorate(TextDecoration.BOLD));
        lore.add(Component.empty());
        lore.add(Component.text("Right-Click: Next Color").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0)));
        lore.add(Component.text("Left-Click: Previous Color").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0)));

        meta.lore(lore);
        item.setItemMeta(meta);
    }

    @Override
    public void onShear(PlayerShearEntityEvent event) {
        if (!(event.getEntity() instanceof Sheep sheep)) return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() != Material.SHEARS) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(SHEAR_COLOR_KEY, PersistentDataType.INTEGER)) return;

        event.setCancelled(true);
        recentlySheared.add(sheep.getUniqueId());
        sheep.shear();

        int amount = new Random().nextInt(3) + 1;
        ItemStack wool = new ItemStack(Material.valueOf(getColorOfShears(item) + "_WOOL"), amount);

        sheep.getWorld().dropItemNaturally(sheep.getLocation(), wool);
    }

    @Override
    public void preventNaturallyDroppedWool(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof Sheep sheep)) return;

        if (recentlySheared.contains(sheep.getUniqueId())) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                    recentlySheared.remove(sheep.getUniqueId()), 1L
            );
        }
    }

    @Override
    public DyeColor getColorOfShears(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (!pdc.has(SHEAR_COLOR_KEY, PersistentDataType.INTEGER)) return null;

        Integer ordinal = pdc.get(SHEAR_COLOR_KEY, PersistentDataType.INTEGER);
        if (ordinal == null || ordinal < 0 || ordinal >= DyeColor.values().length) return null;

        return DyeColor.values()[ordinal];
    }

    @Override
    public NamespacedKey getShearColorKey() {
        return SHEAR_COLOR_KEY;
    }

    @Override
    public Set<UUID> getRecentlySheared() {
        return recentlySheared;
    }
}

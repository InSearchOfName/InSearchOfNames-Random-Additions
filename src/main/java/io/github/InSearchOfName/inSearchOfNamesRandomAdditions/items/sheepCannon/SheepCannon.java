package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SheepCannon {
    private static final NamespacedKey FIRE_MODE_KEY = new NamespacedKey(InSearchOfNamesRandomAdditions.getPlugin(), "fire_mode");
    private static final NamespacedKey SHEEP_CANNON_KEY = new NamespacedKey(InSearchOfNamesRandomAdditions.getPlugin(), "sheep_cannon");
    private static final NamespacedKey VELOCITY_KEY = new NamespacedKey(InSearchOfNamesRandomAdditions.getPlugin(), "velocity");

    private static final Set<UUID> recentlyShotSheep = new HashSet<>();
    private static final Set<UUID> automaticPlayers = new HashSet<>();

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
        lore.add(Component.empty());
        lore.add(Component.text("Left-Click: Shoot da sheep").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0)));
        lore.add(
                Component.text("Right-Click: Fire-mode ").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0))
                        .append(Component.text(fireMode.toString()).decorate(TextDecoration.BOLD).color(TextColor.color(253, 253, 84)))
        );
        lore.add(
                Component.text("Shift-Right-Click: Velocity ").decorate(TextDecoration.BOLD).color(TextColor.color(255, 170, 0))
                        .append(Component.text(velocity.toString()).color(TextColor.color(253, 253, 84)))
        );


        meta.lore(lore);
        item.setItemMeta(meta);
    }

    public static void onClick(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        InventoryView openInv = player.getOpenInventory();
        Inventory topInv = openInv.getTopInventory();

        // Only proceed if player has no GUI open (meaning top inventory is the player's own inventory)
        if (!(topInv.getType() == InventoryType.CRAFTING && topInv.getHolder() instanceof Player)) {
            return; // Some GUI is open, ignore event
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.STICK || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(SHEEP_CANNON_KEY, PersistentDataType.BOOLEAN)) return;

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Integer velocityOrdinal = pdc.get(VELOCITY_KEY, PersistentDataType.INTEGER);
            Integer fireModeOrdinal = pdc.get(FIRE_MODE_KEY, PersistentDataType.INTEGER);
            if (velocityOrdinal != null && fireModeOrdinal != null) {
                Velocity velocity = Velocity.values()[velocityOrdinal];
                FireModes fireMode = FireModes.values()[fireModeOrdinal];
                handleFire(player, item, fireMode, velocity);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                changeVelocity(item, player);
            } else {
                changeFireMode(item, player);
            }
            updateMeta(item);
        }

    }

    // Handle shooting based on fire mode
    public static void handleFire(Player player, ItemStack item, FireModes mode, Velocity velocity) {
        switch (mode) {

            case SemiAutomatic -> shootSheep(player, velocity);

            case Burst -> {
                new BukkitRunnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        shootSheep(player, velocity);
                        if (++count >= 3) cancel();
                    }
                }.runTaskTimer(InSearchOfNamesRandomAdditions.getPlugin(), 0L, 5L); // 5 ticks between shots
            }

            case Automatic -> {
                if (automaticPlayers.contains(player.getUniqueId())) return; // already firing

                automaticPlayers.add(player.getUniqueId());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!player.isOnline() || !isHoldingSheepCannon(player)) {
                            automaticPlayers.remove(player.getUniqueId());
                            cancel();
                            return;
                        }
                        shootSheep(player, velocity);
                    }
                }.runTaskTimer(InSearchOfNamesRandomAdditions.getPlugin(), 0L, 4L); // every 4 ticks (~5 shots/sec)
            }
        }
    }

    // Spawn and shoot a rainbow sheep
    public static void shootSheep(Player player, Velocity velocity) {
        Sheep sheep = player.getWorld().spawn(player.getEyeLocation(), Sheep.class, s -> {
            s.customName(Component.text("jeb_")); // rainbow sheep
            s.setCustomNameVisible(false);
            s.setInvulnerable(true);
            s.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 800, 0));
        });

        // Launch the sheep
        sheep.setVelocity(player.getLocation().getDirection().normalize()
                .multiply(velocity.getMultiplier()));

        recentlyShotSheep.add(sheep.getUniqueId());

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHEEP_AMBIENT, 1f, 1f);

        // Make it explode on block collision
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!sheep.isValid() || sheep.isDead()) {
                    cancel();
                    recentlyShotSheep.remove(sheep.getUniqueId());
                    return;
                }
                if (sheep.isOnGround()) {
                    sheep.getWorld().createExplosion(sheep.getLocation(), 2.0f, false, false, sheep);
                    sheep.remove();
                    cancel();
                    recentlyShotSheep.remove(sheep.getUniqueId());
                }
            }
        }.runTaskTimer(InSearchOfNamesRandomAdditions.getPlugin(), 1L, 1L);
    }

    // Check if player is holding a sheep cannon
    private static boolean isHoldingSheepCannon(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (mainHand == null || !mainHand.hasItemMeta()) return false;
        return mainHand.getItemMeta().getPersistentDataContainer().has(
                SheepCannon.getKey(), org.bukkit.persistence.PersistentDataType.BOOLEAN
        );
    }

    private static void changeFireMode(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        Integer fireModeOrdinal = pdc.get(FIRE_MODE_KEY, PersistentDataType.INTEGER);
        if (fireModeOrdinal == null) return;

        fireModeOrdinal = (fireModeOrdinal + 1) % FireModes.values().length;
        pdc.set(FIRE_MODE_KEY, PersistentDataType.INTEGER, fireModeOrdinal);

        item.setItemMeta(meta);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
    }

    private static void changeVelocity(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        Integer velocityOrdinal = pdc.get(VELOCITY_KEY, PersistentDataType.INTEGER);
        if (velocityOrdinal == null) return;

        velocityOrdinal = (velocityOrdinal + 1) % Velocity.values().length;
        pdc.set(VELOCITY_KEY, PersistentDataType.INTEGER, velocityOrdinal);

        item.setItemMeta(meta);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.5f);
    }

    public static NamespacedKey getKey() {
        return SHEEP_CANNON_KEY;
    }
}

package io.github.InSearchOfName.InSearchOfNamesRandomAdditions.items;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ColoredShears;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Sheep;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ColoredShearsTests {

    private ServerMock server;
    private PlayerMock player;
    private InSearchOfNamesRandomAdditions plugin;

    @BeforeEach
    public void setup() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(InSearchOfNamesRandomAdditions.class);
        player = server.addPlayer();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    // ------------------------------------------------------------
    // Helper methods
    // ------------------------------------------------------------
    private ItemStack createShearsInHand() {
        ItemStack shears = ColoredShears.create();
        player.getInventory().setItemInMainHand(shears);
        player.openInventory(new InventoryMock(player, InventoryType.CRAFTING));
        return shears;
    }

    private PlayerShearEntityEvent simulateShearEvent(Sheep sheep, ItemStack shears) {
        List<ItemStack> drops = new ArrayList<>();
        return new PlayerShearEntityEvent(player, sheep, shears, EquipmentSlot.HAND, drops);
    }

    private EntityDropItemEvent simulateDropEvent(Sheep sheep) {
        ItemStack woolStack = new ItemStack(Material.WHITE_WOOL);
        Item droppedItem = server.getWorlds().getFirst().dropItem(sheep.getLocation(), woolStack);
        return new EntityDropItemEvent(sheep, droppedItem);
    }

    private Sheep spawnSheep() {
        return server.getWorlds().getFirst().spawn(player.getLocation(), Sheep.class);
    }

    // ------------------------------------------------------------
    // Tests for createColoredShears
    // ------------------------------------------------------------
    @Test
    public void createColoredShearsShouldNotBeNullAndHaveCorrectType() {
        ItemStack shears = ColoredShears.create();
        assertNotNull(shears);
        assertEquals(Material.SHEARS, shears.getType());
    }

    @Test
    public void createColoredShearsShouldHaveUnbreakingEnchantment() {
        ItemStack shears = ColoredShears.create();
        assertTrue(shears.getItemMeta().hasEnchant(org.bukkit.enchantments.Enchantment.UNBREAKING));
    }

    @Test
    public void createColoredShearsShouldHaveWhiteColorTag() {
        ItemStack shears = ColoredShears.create();
        Integer colorOrdinal = shears.getItemMeta()
                .getPersistentDataContainer()
                .get(ColoredShears.getShearColorKey(), PersistentDataType.INTEGER);
        assertEquals(DyeColor.WHITE.ordinal(), colorOrdinal);
    }

    // ------------------------------------------------------------
    // Tests for changeColorOfShears
    // ------------------------------------------------------------
    @Test
    public void rightClickShouldIncrementShearsColor() {
        ItemStack shears = createShearsInHand();
        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, shears, null, BlockFace.EAST);
        ColoredShears.changeColorOfShears(event);

        int expected = (DyeColor.WHITE.ordinal() + 1) % DyeColor.values().length;
        int actual = player.getItemInHand().getItemMeta()
                .getPersistentDataContainer()
                .get(ColoredShears.getShearColorKey(), PersistentDataType.INTEGER);
        assertEquals(expected, actual);
    }

    @Test
    public void leftClickShouldDecrementShearsColor() {
        ItemStack shears = createShearsInHand();
        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, shears, null, BlockFace.EAST);
        ColoredShears.changeColorOfShears(event);

        int expected = (DyeColor.WHITE.ordinal() - 1 + DyeColor.values().length) % DyeColor.values().length;
        int actual = player.getItemInHand().getItemMeta()
                .getPersistentDataContainer()
                .get(ColoredShears.getShearColorKey(), PersistentDataType.INTEGER);
        assertEquals(expected, actual);
    }

    // ------------------------------------------------------------
    // Tests for onShear
    // ------------------------------------------------------------
    @Test
    public void onShearShouldCancelVanillaDrop() {
        Sheep sheep = spawnSheep();
        ItemStack shears = createShearsInHand();
        PlayerShearEntityEvent event = simulateShearEvent(sheep, shears);

        ColoredShears.onShear(event);
        assertTrue(event.isCancelled());
    }

    @Test
    public void onShearShouldShearTheSheep() {
        Sheep sheep = spawnSheep();
        ItemStack shears = createShearsInHand();
        PlayerShearEntityEvent event = simulateShearEvent(sheep, shears);

        ColoredShears.onShear(event);
        assertTrue(sheep.isSheared());
    }

    @Test
    public void onShearShouldTrackRecentlyShearedSheep() {
        Sheep sheep = spawnSheep();
        ItemStack shears = createShearsInHand();
        PlayerShearEntityEvent event = simulateShearEvent(sheep, shears);

        ColoredShears.onShear(event);
        assertTrue(ColoredShears.getRecentlySheared().contains(sheep.getUniqueId()));
    }

    @Test
    public void onShearShouldDropWoolMatchingShearsColor() {
        Sheep sheep = spawnSheep();
        sheep.setColor(DyeColor.LIGHT_BLUE);
        ItemStack shears = createShearsInHand(); // pick non-white to ensure variety
        PlayerShearEntityEvent event = simulateShearEvent(sheep, shears);

        ColoredShears.onShear(event);

        String expectedColor = ColoredShears.getColorOfShears(shears).toString();
        boolean woolDropped = server.getWorlds().getFirst()
                .getEntitiesByClass(Item.class)
                .stream()
                .anyMatch(item -> item.getItemStack().getType() == Material.valueOf(expectedColor + "_WOOL"));

        assertTrue(woolDropped);
    }

    // ------------------------------------------------------------
    // Tests for preventNaturallyDroppedWool
    // ------------------------------------------------------------
    @Test
    public void preventNaturallyDroppedWoolShouldCancelEvent() {
        Sheep sheep = spawnSheep();
        ColoredShears.getRecentlySheared().add(sheep.getUniqueId());
        EntityDropItemEvent event = simulateDropEvent(sheep);

        ColoredShears.preventNaturallyDroppedWool(event);
        assertTrue(event.isCancelled());
    }

    @Test
    public void preventNaturallyDroppedWoolShouldClearRecentlyShearedAfterTick() {
        Sheep sheep = spawnSheep();
        ColoredShears.getRecentlySheared().add(sheep.getUniqueId());
        EntityDropItemEvent event = simulateDropEvent(sheep);

        ColoredShears.preventNaturallyDroppedWool(event);
        server.getScheduler().performTicks(1);

        assertFalse(ColoredShears.getRecentlySheared().contains(sheep.getUniqueId()));
    }
}

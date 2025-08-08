package io.github.InSearchOfName.InSearchOfNamesRandomAdditions.items;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ColoredShears;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.entity.SheepMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockbukkit.mockbukkit.MockBukkit.mock;

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

    @Test
    public void testCreateColoredShears() {
        ItemStack shears = ColoredShears.createColoredShears();
        assertNotNull(shears);
        assertEquals(Material.SHEARS, shears.getType());

        ItemMeta meta = shears.getItemMeta();
        assertNotNull(meta);
        assertTrue(meta.hasEnchant(org.bukkit.enchantments.Enchantment.UNBREAKING));

        Integer colorOrdinal = meta.getPersistentDataContainer().get(ColoredShears.getShearColorKey(), PersistentDataType.INTEGER);
        assertNotNull(colorOrdinal);
        assertEquals(DyeColor.WHITE.ordinal(), colorOrdinal);
    }

    @Test
    public void testChangeColorOfShearsRightClick() {
        ItemStack shears = ColoredShears.createColoredShears();

        player.getInventory().setItemInMainHand(shears);
        player.openInventory(new InventoryMock(player, InventoryType.CRAFTING));

        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, shears, null, BlockFace.EAST);
        ColoredShears.changeColorOfShears(event);

        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
        Integer colorOrdinal = meta.getPersistentDataContainer().get(ColoredShears.getShearColorKey(), PersistentDataType.INTEGER);

        int expectedOrdinal = (DyeColor.WHITE.ordinal() + 1) % DyeColor.values().length;
        assertEquals(expectedOrdinal, colorOrdinal);
    }



    @Test
    public void testChangeColorOfShearsLeftClick() {
        ItemStack shears = ColoredShears.createColoredShears();

        player.getInventory().setItemInMainHand(shears);
        player.openInventory(new InventoryMock(player, InventoryType.CRAFTING));

        PlayerInteractEvent event = new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, shears, null, BlockFace.EAST);
        ColoredShears.changeColorOfShears(event);

        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
        Integer colorOrdinal = meta.getPersistentDataContainer().get(ColoredShears.getShearColorKey(), PersistentDataType.INTEGER);

        int expectedOrdinal = (DyeColor.WHITE.ordinal() - 1 + DyeColor.values().length) % DyeColor.values().length;
        assertEquals(expectedOrdinal, colorOrdinal);
    }


    @Test
    public void testOnShearEventDropColoredWool() {

    }

    @Test
    public void testPreventNaturallyDroppedWool() {

    }
}

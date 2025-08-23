package io.github.InSearchOfName.InSearchOfNamesRandomAdditions.commands;

import io.github.InSearchOfName.InSearchOfNamesRandomAdditions.TestHelper;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis.MenuInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuTests {
    private TestHelper helper;
    private ServerMock server;
    private PlayerMock player;
    private InSearchOfNamesRandomAdditions plugin;

    @BeforeEach
    public void setup() {
        helper = TestHelper.start();
        server = helper.server();
        plugin = helper.plugin();
        player = helper.newPlayer();
    }

    @AfterEach
    public void tearDown() { helper.close(); }

    @Test
    public void menuTestIfItOpensMenuInventoryWhenCallingTheCommand() {
        server.dispatchCommand(player, "Menu");
        assertInstanceOf(MenuInventory.class, player.getOpenInventory().getTopInventory().getHolder());
    }

    @Test
    public void menuTestIfYouCannotTakeOutOfMenuInventory() {
        server.dispatchCommand(player, "Menu");
        InventoryClickEvent clickEvent = new InventoryClickEvent(
                player.getOpenInventory(),
                InventoryType.SlotType.CONTAINER,
                0,
                ClickType.LEFT,
                InventoryAction.PICKUP_ONE
        );
        Bukkit.getPluginManager().callEvent(clickEvent);

        assertTrue(clickEvent.isCancelled(), "The click should be cancelled to prevent item pickup.");
    }
}

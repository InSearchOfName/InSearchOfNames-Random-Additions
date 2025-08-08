package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ColoredShears;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class ColoredShearsClickEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        ColoredShears.changeColorOfShears(event);
    }
}

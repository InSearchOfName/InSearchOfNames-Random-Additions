package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class ColoredShearsClickEvent implements Listener {
    private final ColoredShears service;

    @Inject
    public ColoredShearsClickEvent(ColoredShears service) {
        this.service = service;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        service.onClick(event);
    }
}

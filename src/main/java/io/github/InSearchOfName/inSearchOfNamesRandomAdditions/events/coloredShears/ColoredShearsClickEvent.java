package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShearsService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class ColoredShearsClickEvent implements Listener {
    private final ColoredShearsService service;

    @Inject
    public ColoredShearsClickEvent(ColoredShearsService service) {
        this.service = service;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        service.changeColorOfShears(event);
    }
}

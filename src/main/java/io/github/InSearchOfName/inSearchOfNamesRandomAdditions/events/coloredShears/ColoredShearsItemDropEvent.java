package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShearsService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

public class ColoredShearsItemDropEvent implements Listener {
    private final ColoredShearsService service;

    @Inject
    public ColoredShearsItemDropEvent(ColoredShearsService service) {
        this.service = service;
    }

    @EventHandler
    public void coloredShearsItemDropEvent(EntityDropItemEvent event) {
        service.preventNaturallyDroppedWool(event);
    }
}

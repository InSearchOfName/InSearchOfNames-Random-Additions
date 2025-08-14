package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

public class ColoredShearsItemDropEvent implements Listener {

    @EventHandler
    public void coloredShearsItemDropEvent(EntityDropItemEvent event) {
        ColoredShears.preventNaturallyDroppedWool(event);
    }
}

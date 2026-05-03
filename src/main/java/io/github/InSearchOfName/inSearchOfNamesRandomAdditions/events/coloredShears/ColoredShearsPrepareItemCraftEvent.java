package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class ColoredShearsPrepareItemCraftEvent implements Listener {
    private final ColoredShears coloredShears;

    @Inject
    public ColoredShearsPrepareItemCraftEvent(ColoredShears coloredShears) {
        this.coloredShears = coloredShears;
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        coloredShears.onCraft(event);
    }
}

package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ColoredShearsShearEvent implements Listener {
    private final ColoredShears service;

    @Inject
    public ColoredShearsShearEvent(ColoredShears service) {
        this.service = service;
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        service.onShear(event);
    }
}

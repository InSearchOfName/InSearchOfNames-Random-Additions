package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ColoredShears;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ColoredShearsShearEvent implements Listener {

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        ColoredShears.onShear(event);
    }
}

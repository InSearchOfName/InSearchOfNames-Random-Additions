package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.sheepCannon;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SheepCannonClickEvent implements Listener {
    @EventHandler
    public void onSheepCannon(PlayerInteractEvent playerInteractEvent) {
        SheepCannon.onClick(playerInteractEvent);
    }
}

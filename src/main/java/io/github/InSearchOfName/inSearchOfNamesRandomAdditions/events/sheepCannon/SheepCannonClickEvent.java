package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.sheepCannon;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SheepCannonClickEvent implements Listener {
    private final SheepCannon service;

    @Inject
    public SheepCannonClickEvent(SheepCannon service) {
        this.service = service;
    }

    @EventHandler
    public void onSheepCannon(PlayerInteractEvent playerInteractEvent) {
        service.onClick(playerInteractEvent);
    }
}

package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.sheepCannon;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannonService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SheepCannonClickEvent implements Listener {
    private final SheepCannonService service;

    @Inject
    public SheepCannonClickEvent(SheepCannonService service) {
        this.service = service;
    }

    @EventHandler
    public void onSheepCannon(PlayerInteractEvent playerInteractEvent) {
        service.onClick(playerInteractEvent);
    }
}

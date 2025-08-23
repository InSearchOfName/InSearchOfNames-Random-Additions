package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events;

import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsItemDropEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsShearEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.sheepCannon.SheepCannonClickEvent;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    private final InSearchOfNamesRandomAdditions plugin;
    private final MenuInventoryClickEvent menuInventoryClickEvent;
    private final ColoredShearsClickEvent coloredShearsClickEvent;
    private final ColoredShearsShearEvent coloredShearsShearEvent;
    private final ColoredShearsItemDropEvent coloredShearsItemDropEvent;
    private final SheepCannonClickEvent sheepCannonClickEvent;

    @Inject
    public EventManager(InSearchOfNamesRandomAdditions plugin,
                        MenuInventoryClickEvent menuInventoryClickEvent,
                        ColoredShearsClickEvent coloredShearsClickEvent,
                        ColoredShearsShearEvent coloredShearsShearEvent,
                        ColoredShearsItemDropEvent coloredShearsItemDropEvent,
                        SheepCannonClickEvent sheepCannonClickEvent) {
        this.plugin = plugin;
        this.menuInventoryClickEvent = menuInventoryClickEvent;
        this.coloredShearsClickEvent = coloredShearsClickEvent;
        this.coloredShearsShearEvent = coloredShearsShearEvent;
        this.coloredShearsItemDropEvent = coloredShearsItemDropEvent;
        this.sheepCannonClickEvent = sheepCannonClickEvent;
    }

    public void registerEvents() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(menuInventoryClickEvent, plugin);
        pm.registerEvents(coloredShearsClickEvent, plugin);
        pm.registerEvents(coloredShearsShearEvent, plugin);
        pm.registerEvents(coloredShearsItemDropEvent, plugin);
        pm.registerEvents(sheepCannonClickEvent, plugin);
    }
}

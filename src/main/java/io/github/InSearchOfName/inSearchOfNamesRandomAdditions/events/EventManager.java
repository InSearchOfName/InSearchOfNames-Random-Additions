package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsItemDropEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsShearEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.sheepCannon.SheepCannonClickEvent;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public void registerEvents() {
        PluginManager pm = InSearchOfNamesRandomAdditions.getPlugin().getServer().getPluginManager();
        pm.registerEvents(new MenuInventoryClickEvent(), InSearchOfNamesRandomAdditions.getPlugin());
        pm.registerEvents(new ColoredShearsClickEvent(), InSearchOfNamesRandomAdditions.getPlugin());
        pm.registerEvents(new ColoredShearsShearEvent(), InSearchOfNamesRandomAdditions.getPlugin());
        pm.registerEvents(new ColoredShearsItemDropEvent(), InSearchOfNamesRandomAdditions.getPlugin());
        pm.registerEvents(new SheepCannonClickEvent(), InSearchOfNamesRandomAdditions.getPlugin());
    }
}

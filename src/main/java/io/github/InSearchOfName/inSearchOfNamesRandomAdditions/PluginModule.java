package io.github.InSearchOfName.inSearchOfNamesRandomAdditions;

import com.google.inject.AbstractModule;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands.CommandManager;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands.Menu;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.EventManager;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsItemDropEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsPrepareItemCraftEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsShearEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.menu.MenuInventoryClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.sheepCannon.SheepCannonClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.ItemManager;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShears;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShearsImpl;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannon;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannonImpl;


public class PluginModule extends AbstractModule {
    private final InSearchOfNamesRandomAdditions plugin;

    public PluginModule(InSearchOfNamesRandomAdditions plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(InSearchOfNamesRandomAdditions.class).toInstance(plugin);

        // Explicit bindings
        // Managers
        bind(CommandManager.class);
        bind(EventManager.class);
        bind(ItemManager.class);
        // Menu's
        bind(Menu.class);
        // Events
        bind(MenuInventoryClickEvent.class);
        bind(ColoredShearsClickEvent.class);
        bind(ColoredShearsShearEvent.class);
        bind(ColoredShearsItemDropEvent.class);
        bind(ColoredShearsPrepareItemCraftEvent.class);
        bind(SheepCannonClickEvent.class);

        // Item's
        bind(ColoredShears.class).to(ColoredShearsImpl.class);
        bind(SheepCannon.class).to(SheepCannonImpl.class);

    }
}

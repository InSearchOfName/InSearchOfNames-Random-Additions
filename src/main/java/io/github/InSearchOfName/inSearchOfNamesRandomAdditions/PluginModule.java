package io.github.InSearchOfName.inSearchOfNamesRandomAdditions;

import com.google.inject.AbstractModule;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands.CommandManager;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands.Menu;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.menu.MenuInventoryClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsItemDropEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.coloredShears.ColoredShearsShearEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.sheepCannon.SheepCannonClickEvent;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShearsService;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.coloredShears.ColoredShearsServiceImpl;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannonService;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.items.sheepCannon.SheepCannonServiceImpl;


public class PluginModule extends AbstractModule {
    private final InSearchOfNamesRandomAdditions plugin;

    public PluginModule(InSearchOfNamesRandomAdditions plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(InSearchOfNamesRandomAdditions.class).toInstance(plugin);
        // Explicit bindings (optional, Guice can do just-in-time). Keep for clarity.
        bind(CommandManager.class);
        bind(Menu.class);
        bind(MenuInventoryClickEvent.class);
        bind(ColoredShearsClickEvent.class);
        bind(ColoredShearsShearEvent.class);
        bind(ColoredShearsItemDropEvent.class);
        bind(SheepCannonClickEvent.class);

        // Services for item logic
        bind(ColoredShearsService.class).to(ColoredShearsServiceImpl.class);
        bind(SheepCannonService.class).to(SheepCannonServiceImpl.class);

    }
}

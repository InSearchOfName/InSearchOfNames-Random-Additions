package io.github.InSearchOfName.inSearchOfNamesRandomAdditions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands.CommandManager;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.EventManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class InSearchOfNamesRandomAdditions extends JavaPlugin {
    private Injector injector;
    private ConsoleCommandSender console;

    @Override
    public void onEnable() {
        // Plugin startup logic
        injector = Guice.createInjector(new PluginModule(this));
        console = getServer().getConsoleSender();

        // Use DI to construct and register components
        injector.getInstance(CommandManager.class).registerCommands();
        injector.getInstance(EventManager.class).registerEvents();
        console.sendMessage(Component.text("InSearchOfNames Random Additions Enabled"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        console.sendMessage(Component.text("InSearchOfNames Random Additions Disabled"));
    }

    public Injector getInjector() {
        return injector;
    }

}

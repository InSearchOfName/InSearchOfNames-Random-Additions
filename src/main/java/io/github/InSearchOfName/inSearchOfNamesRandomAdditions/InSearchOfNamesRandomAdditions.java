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
    private static InSearchOfNamesRandomAdditions plugin;
    private static ConsoleCommandSender console;

    @Override
    public void onEnable() {
        // Plugin startup logic
        injector = Guice.createInjector(new PluginModule(this));
        plugin = this;
        console = plugin.getServer().getConsoleSender();

        new CommandManager().registerCommands();
        new EventManager().registerEvents();
        console.sendMessage(Component.text("InSearchOfNames Random Additions Enabled"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        console.sendMessage(Component.text("InSearchOfNames Random Additions Disabled"));
    }

    public static InSearchOfNamesRandomAdditions getPlugin() {
        return plugin;
    }

    public static ConsoleCommandSender getConsole() {
        return console;
    }

}

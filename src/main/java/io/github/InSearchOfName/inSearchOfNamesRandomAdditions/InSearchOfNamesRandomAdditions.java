package io.github.InSearchOfName.inSearchOfNamesRandomAdditions;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands.CommandManager;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.events.EventManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class InSearchOfNamesRandomAdditions extends JavaPlugin {
    public static InSearchOfNamesRandomAdditions plugin;
    public static ConsoleCommandSender console;

    public static InSearchOfNamesRandomAdditions getPlugin() {
        return plugin;
    }

    public static ConsoleCommandSender getConsole() {
        return console;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
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
}

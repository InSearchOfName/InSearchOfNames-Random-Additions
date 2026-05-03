package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands;


import com.google.inject.Inject;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;

public class CommandManager {
    private final InSearchOfNamesRandomAdditions plugin;
    private final Menu menu;

    @Inject
    public CommandManager(InSearchOfNamesRandomAdditions plugin, Menu menu) {
        this.plugin = plugin;
        this.menu = menu;
    }

    public void registerCommands() {
        plugin.getCommand("menu").setExecutor(menu);
    }
}

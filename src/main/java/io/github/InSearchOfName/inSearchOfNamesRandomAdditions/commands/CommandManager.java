package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands;


import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;

public class CommandManager {
    public void registerCommands() {
        InSearchOfNamesRandomAdditions.getPlugin().getCommand("menu").setExecutor(new Menu());
    }
}

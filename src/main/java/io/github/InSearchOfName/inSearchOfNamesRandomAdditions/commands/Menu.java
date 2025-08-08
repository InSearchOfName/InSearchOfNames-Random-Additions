package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands;

import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis.MenuInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Menu implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can run this command!"));
            return true;
        }
        MenuInventory menuInventory = new MenuInventory(InSearchOfNamesRandomAdditions.getPlugin());
        player.openInventory(menuInventory.getInventory());
        return true;
    }
}

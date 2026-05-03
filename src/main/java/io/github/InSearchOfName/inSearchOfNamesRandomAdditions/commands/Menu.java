package io.github.InSearchOfName.inSearchOfNamesRandomAdditions.commands;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.guis.MenuInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Menu implements CommandExecutor {
    private final Provider<MenuInventory> menuInventoryProvider;

    @Inject
    public Menu(Provider<MenuInventory> menuInventoryProvider) {
        this.menuInventoryProvider = menuInventoryProvider;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can run this command!"));
            return true;
        }
        player.openInventory(menuInventoryProvider.get().getInventory());
        return true;
    }
}

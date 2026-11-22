package me.jojjjo147.workstations.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be executed by a player!");
            return true;
        }

        if (strings.length > 0 && !commandSender.hasPermission("workstations.open_other_enderchests")) {
            commandSender.sendMessage(ChatColor.RED + "You don't have the permission to open other player's enderchests");
            return true;
        }

        if (strings.length > 1) {
            commandSender.sendMessage("Usage: /enderchest [player]");
            return true;
        }

        Player player = (Player) commandSender;
        Player target = strings.length == 1 ? Bukkit.getPlayerExact(strings[0]) : player;
        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + "Couldn't find target player");
            return true;
        }

        if (target != player && !Bukkit.isOwnedByCurrentRegion(target)) {
            commandSender.sendMessage(ChatColor.RED + "You can only open an ender chest from a player in the same region");
            return true;
        }

        player.openInventory(target.getEnderChest());
        player.playSound(player, Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
        return true;
    }
}

package me.imillusion.blockcracking.commands;

import me.imillusion.blockcracking.CrackManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCrackWand implements CommandExecutor {

    private CrackManager manager;

    public GiveCrackWand(CrackManager manager)
    {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You need to be a player to execute this command,");
            return true;
        }

        if(!sender.hasPermission("blockcracking.givewand"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }

        Player p = (Player) sender;

        if(p.getInventory().firstEmpty() == -1)
        {
            p.sendMessage(ChatColor.RED + "Your inventory is full.");
            return true;
        }

        p.getInventory().addItem(manager.getCrackWand());

        return true;
    }
}

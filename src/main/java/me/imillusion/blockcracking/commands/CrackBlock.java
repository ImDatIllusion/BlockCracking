package me.imillusion.blockcracking.commands;

import de.tr7zw.nbtapi.NBTItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CrackBlock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
            return true;
        }

        if(!sender.hasPermission("blockcracking.crackblock"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }

        Player p = (Player) sender;

        if(args.length != 1 || !StringUtils.isNumeric(args[0]) || Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 1)
        {
            sender.sendMessage(ChatColor.RED + "Invalid arguments, expected a number between 1 and 10");
            return true;
        }

        if(p.getInventory().firstEmpty() == -1)
        {
            p.sendMessage(ChatColor.RED + "Your inventory is full.");
            return true;
        }

        ItemStack item = p.getInventory().getItemInMainHand().clone();

        if(!item.getType().isBlock())
        {
            p.sendMessage(ChatColor.RED + "That item isn't a block.");
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Crack level: " + ChatColor.GOLD + args[0]);
        meta.addEnchant(Enchantment.LUCK, 123, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        NBTItem nbt = new NBTItem(item);
        nbt.setInteger("crack", Integer.valueOf(args[0]));
        p.getInventory().addItem(nbt.getItem());

        p.sendMessage(ChatColor.GREEN + "You have cracked a block.");

        return true;
    }
}

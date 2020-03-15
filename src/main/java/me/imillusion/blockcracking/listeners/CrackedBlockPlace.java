package me.imillusion.blockcracking.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.imillusion.blockcracking.CrackManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CrackedBlockPlace implements Listener {

    private CrackManager manager;

    public CrackedBlockPlace(CrackManager manager)
    {
        this.manager = manager;
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent e)
    {
        NBTItem item = new NBTItem(e.getItemInHand());

        if(!item.hasKey("crack"))
            return;

        int intensity = item.getInteger("crack");
        manager.setCrack(e.getBlock().getLocation(), intensity);
    }

}

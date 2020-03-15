package me.imillusion.blockcracking.listeners;

import me.imillusion.blockcracking.CrackManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CrackBlockWand implements Listener {

    private CrackManager manager;

    public CrackBlockWand(CrackManager manager)
    {
        this.manager = manager;
    }

    @EventHandler
    private void onClick(PlayerInteractEvent e)
    {
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null)
            return;

        if(e.getItem() == null || !e.getItem().isSimilar(manager.getCrackWand()))
            return;

        Location loc = e.getClickedBlock().getLocation();

        if(e.getPlayer().isSneaking())
            manager.setCrack(loc, 0);
        else
            if(manager.getFromLocation(loc) == null)
                manager.setCrack(loc, 1);
            else if(manager.getFromLocation(loc).getPower() < 10)
                manager.setCrack(loc, manager.getFromLocation(loc).getPower() + 1);
            else
                manager.setCrack(loc, 0);

    }

}

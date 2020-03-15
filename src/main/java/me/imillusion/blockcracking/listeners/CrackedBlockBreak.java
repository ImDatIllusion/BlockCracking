package me.imillusion.blockcracking.listeners;

import me.imillusion.blockcracking.CrackManager;
import me.imillusion.blockcracking.data.Crack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CrackedBlockBreak implements Listener {

    private CrackManager manager;

    public CrackedBlockBreak(CrackManager manager)
    {
        this.manager = manager;
    }

    @EventHandler
    private void onBreak(BlockBreakEvent e)
    {
        manager.setCrack(e.getBlock().getLocation(), 0);
    }

}

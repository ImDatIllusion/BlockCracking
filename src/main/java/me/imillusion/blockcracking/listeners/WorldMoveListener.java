package me.imillusion.blockcracking.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.imillusion.blockcracking.CrackManager;
import me.imillusion.blockcracking.data.Crack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.lang.reflect.InvocationTargetException;

public class WorldMoveListener implements Listener {

    private CrackManager manager;

    public WorldMoveListener(CrackManager manager)
    {
        this.manager = manager;
    }

    @EventHandler
    private void onWorldMove(PlayerChangedWorldEvent e)
    {
        for(Crack crack : manager.getCracks())
        {
            if(crack.getLoc().getWorld().equals(e.getPlayer().getWorld()))
                continue;

            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

            packet.getIntegers()
                    .write(0, crack.getId())
                    .write(1, -1);

            packet.getBlockPositionModifier()
                    .write(0, new BlockPosition(crack.getLoc().getBlockX(), crack.getLoc().getBlockY(), crack.getLoc().getBlockZ()));

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(e.getPlayer(), packet);
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

}

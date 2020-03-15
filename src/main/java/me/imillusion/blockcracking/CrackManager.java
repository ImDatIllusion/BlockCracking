package me.imillusion.blockcracking;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import lombok.Getter;
import lombok.Setter;
import me.imillusion.blockcracking.data.Crack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CrackManager {

    @Getter
    private ItemStack crackWand;

    @Getter
    @Setter
    private int currentid = 5000;

    @Getter
    private Set<Crack> cracks = new HashSet<>();

    CrackManager(BlockCrack main) {
        crackWand = new ItemStack(Material.STICK);
        ItemMeta meta = crackWand.getItemMeta();

        meta.addEnchant(Enchantment.LUCK, 123, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setDisplayName(ChatColor.RED + "Cracking wand");
        meta.setLore(Arrays.asList("", "&fRight click a block to crack it"));

        crackWand.setItemMeta(meta);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::update, 100, 100);
    }
    public void update() {
        for (Crack crack : cracks) {
            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

            packet.getIntegers()
                    .write(0, crack.getId())
                    .write(1, crack.getPower() - 1);

            packet.getBlockPositionModifier()
                    .write(0, new BlockPosition(crack.getLoc().getBlockX(), crack.getLoc().getBlockY(), crack.getLoc().getBlockZ()));

            for (Player p : crack.getLoc().getWorld().getPlayers()) {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Crack getFromLocation(Location loc) {
        return cracks.stream().filter(c -> c.getLoc().equals(loc)).findFirst().orElse(null);
    }
    public void setCrack(Location loc, int strength) {

        if (strength == 0 && getFromLocation(loc) != null) {
            getFromLocation(loc).setPower(0);
            update();
            cracks.remove(getFromLocation(loc));
            return;
        } else if (getFromLocation(loc) != null) {
            Crack crack = getFromLocation(loc);
            cracks.remove(crack);
            crack.setPower(strength);
            cracks.add(crack);
        } else
            cracks.add(new Crack(loc, currentid++, strength));

        update();
    }
    public void registerCrack(Crack crack) {
        cracks.add(crack);
    }

}

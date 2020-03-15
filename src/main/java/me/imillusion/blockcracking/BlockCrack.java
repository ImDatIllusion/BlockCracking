package me.imillusion.blockcracking;

import lombok.Getter;
import me.imillusion.blockcracking.commands.CrackBlock;
import me.imillusion.blockcracking.commands.GiveCrackWand;
import me.imillusion.blockcracking.data.Crack;
import me.imillusion.blockcracking.files.FileManager;
import me.imillusion.blockcracking.listeners.CrackBlockWand;
import me.imillusion.blockcracking.listeners.CrackedBlockPlace;
import me.imillusion.blockcracking.listeners.WorldMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockCrack extends JavaPlugin {

    @Getter
    private FileManager fm;
    private CrackManager manager;

    @Override
    public void onEnable() {
        manager = new CrackManager(this);

        ConfigurationSerialization.registerClass(Crack.class);

        fm = new FileManager(this, manager);

        Bukkit.getPluginManager().registerEvents(new CrackBlockWand(manager), this);
        Bukkit.getPluginManager().registerEvents(new CrackedBlockPlace(manager), this);
        Bukkit.getPluginManager().registerEvents(new WorldMoveListener(manager), this);

        getCommand("crackwand").setExecutor(new GiveCrackWand(manager));
        getCommand("crackblock").setExecutor(new CrackBlock());
    }

    @Override
    public void onDisable() {
        fm.saveCracks(manager.getCracks());
    }
}

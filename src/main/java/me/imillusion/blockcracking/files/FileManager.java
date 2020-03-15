package me.imillusion.blockcracking.files;

import me.imillusion.blockcracking.BlockCrack;
import me.imillusion.blockcracking.CrackManager;
import me.imillusion.blockcracking.data.Crack;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FileManager {

    private CrackManager manager;

    private File dataFile;
    private FileConfiguration dataConfig;

    public FileManager(BlockCrack main, CrackManager manager)
    {
        this.manager = manager;

        dataFile = new File(main.getDataFolder(), "data.yml");
        dataConfig = getYML(dataFile);

        loadCracks();
    }

    private FileConfiguration getYML(File file)
    {
        FileConfiguration cfg = new YamlConfiguration();
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            cfg.load(file);
            cfg.addDefault("crackid", 5000);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return cfg;
    }

    public void saveCracks(Set<Crack> cracks)
    {
        int num = 0;

        dataConfig.set("crackid", manager.getCurrentid());

        for(Crack crack : cracks)
            dataConfig.set("cracks." + num++, crack);

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCracks()
    {
        if(dataConfig.contains("cracks"))
            for(String s : dataConfig.getConfigurationSection("cracks").getKeys(false))
            {
                Crack crack = (Crack) dataConfig.get("cracks." + s);
                manager.registerCrack(crack);
            }

        manager.setCurrentid(dataConfig.getInt("crackid"));
    }

}

package io.lightydev.modelenginemerger;

import io.lightydev.modelenginemerger.commands.MEMCommand;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MEMPlugin extends JavaPlugin {

    @Getter private static MEMPlugin instance;
    @Getter private static List<File> modelEngineFolders;
    @Getter private static List<File> itemsAdderFolders;

    @SneakyThrows
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.saveDefaultConfig();

        Bukkit.getConsoleSender().sendMessage("§a[ModelEngineMerger] Enabling plugin...");

        // Check and convert old config format
        if (convertOldConfig()) {
            this.getLogger().info("Old config format detected and successfully converted to the new format.");
        }

        // Ensure the paths list exists in the config.yml
        if (this.getConfig().get("paths") == null || !(this.getConfig().get("paths") instanceof List<?>)) {
            this.getLogger().severe("Please add a 'paths' section with folder pairs in the config.yml!");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        loadFolderPairs();

        // Register the command
        this.getCommand("mem").setExecutor(new MEMCommand());

        Bukkit.getConsoleSender().sendMessage("§a[ModelEngineMerger] Successfully enabled plugin!");

        // Metrics!
        int pluginId = 17326;
        new Metrics(this, pluginId);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("§c[ModelEngineMerger] Disabling plugin...");
    }

    public void reload() {
        this.reloadConfig();
        loadFolderPairs();
    }

    private void loadFolderPairs() {
        modelEngineFolders = new ArrayList<>();
        itemsAdderFolders = new ArrayList<>();
        List<Map<?, ?>> paths = this.getConfig().getMapList("paths");

        if (paths != null && !paths.isEmpty()) {
            for (Map<?, ?> path : paths) {
                String from = (String) path.get("from");
                String to = (String) path.get("to");
                modelEngineFolders.add(new File(getDataFolder().getParentFile().toString() + "/" + from));
                itemsAdderFolders.add(new File(getDataFolder().getParentFile().toString() + "/" + to));
            }
        } else {
            this.getLogger().warning("The 'paths' section is missing or empty in the config.yml!");
        }
    }

    private boolean convertOldConfig() {
        if (this.getConfig().getString("paths.from") != null && this.getConfig().getString("paths.to") != null) {
            // Get the old paths.from and paths.to values
            String oldFrom = this.getConfig().getString("paths.from");
            String oldTo = this.getConfig().getString("paths.to");

            // Remove the old paths.from and paths.to keys
            this.getConfig().set("paths.from", null);
            this.getConfig().set("paths.to", null);

            // Create the new paths list with the old folder pair
            List<Map<String, String>> newPathList = new ArrayList<>();
            Map<String, String> folderPair = new HashMap<>();
            folderPair.put("from", oldFrom);
            folderPair.put("to", oldTo);
            newPathList.add(folderPair);

            // Set the new paths list in the config.yml
            this.getConfig().set("paths", newPathList);

            // Save the updated config.yml
            this.saveConfig();

            return true;
        }
        return false;
    }

}

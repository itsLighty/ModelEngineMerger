package io.lightydev.modelenginemerger;

import io.lightydev.modelenginemerger.commands.MEMCommand;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MEMPlugin extends JavaPlugin {

    @Getter private static MEMPlugin instance;
    @Getter private static File modelEngineFolder;
    @Getter private static File itemsAdderFolder;

    @SneakyThrows
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.saveDefaultConfig();

        Bukkit.getConsoleSender().sendMessage("§a[ModelEngineMerger] Enabling plugin...");

        // Check if config.yml has valid values
        if (this.getConfig().getString("paths.from") == null || this.getConfig().getString("paths.to") == null) {
            this.getLogger().severe("Please set the ModelEngine and ItemsAdder folders in the config.yml!");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        // Setting the paths
        modelEngineFolder = new File(getDataFolder().getParentFile().toString() + "/" + getConfig().getString("paths.from"));
        itemsAdderFolder = new File(getDataFolder().getParentFile().toString() + "/" + getConfig().getString("paths.to"));

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

        modelEngineFolder = new File(getDataFolder().getParentFile().toString() + "/" + getConfig().getString("paths.from"));
        itemsAdderFolder = new File(getDataFolder().getParentFile().toString() + "/" + getConfig().getString("paths.to"));
    }

}

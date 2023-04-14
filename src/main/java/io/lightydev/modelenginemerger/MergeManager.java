package io.lightydev.modelenginemerger;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MergeManager {

    @Getter private static final MergeManager instance = new MergeManager();

    public void mergeFolders() {
        MEMPlugin plugin = MEMPlugin.getInstance();
        List<File> modelEngineFolders = plugin.getModelEngineFolders();
        List<File> itemsAdderFolders = plugin.getItemsAdderFolders();

        for (int i = 0; i < modelEngineFolders.size(); i++) {
            File sourceFolder = modelEngineFolders.get(i);
            File destinationFolder = itemsAdderFolders.get(i);

            if (!sourceFolder.exists()) {
                Bukkit.getConsoleSender().sendMessage("§c[ModelEngineMerger] Source folder doesn't exist: " + sourceFolder.getPath());
                continue;
            }

            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            try {
                copyFolder(sourceFolder, destinationFolder);
                Bukkit.getConsoleSender().sendMessage("§a[ModelEngineMerger] Successfully merged folders: " + sourceFolder.getPath() + " -> " + destinationFolder.getPath());
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage("§c[ModelEngineMerger] Failed to merge folders: " + sourceFolder.getPath() + " -> " + destinationFolder.getPath());
                e.printStackTrace();
            }
        }
    }

    private void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            for (String file : sourceFolder.list()) {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                copyFolder(srcFile, destFile);
            }
        } else {
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}

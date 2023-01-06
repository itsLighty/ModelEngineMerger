package io.lightydev.modelenginemerger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicBoolean;

public class MergeManager {

    public static boolean merge() throws IOException {
        AtomicBoolean success = new AtomicBoolean(true);
        Path modelEngine = MEMPlugin.getModelEngineFolder().toPath();
        Path itemsAdder = MEMPlugin.getItemsAdderFolder().toPath();

        Files.walk(modelEngine)
            .forEach(sourceFile -> {
                try {
                    // Compute the destination file path
                    Path destinationFile = itemsAdder.resolve(modelEngine.relativize(sourceFile));

                    // Copy the file from the source to the destination
                    Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    success.set(true);
                } catch (IOException e) {
                    // Handle the exception
                    success.set(false);
                }
            });
        return success.get();
    }
}

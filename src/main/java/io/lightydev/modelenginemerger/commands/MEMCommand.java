package io.lightydev.modelenginemerger.commands;

import io.lightydev.modelenginemerger.MEMPlugin;
import io.lightydev.modelenginemerger.MergeManager;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MEMCommand implements CommandExecutor {
    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!label.equalsIgnoreCase("mem")) return false;
        if(!sender.hasPermission("modelenginemerger.admin")) {
            sender.sendMessage("§c[ModelEngineMerger] You don't have permission to execute this command!");
            return true;
        }

        // Arguments: /mem merge & /mem reload
        if(args.length == 0) {
            sender.sendMessage("§c[ModelEngineMerger] Please specify a subcommand!");
            sender.sendMessage("§c[ModelEngineMerger] Available subcommands: merge, reload");
            return true;
        }

        // Merge Command
        if(args[0].equalsIgnoreCase("merge")) {
            sender.sendMessage("§a[ModelEngineMerger] Merging ModelEngine and ItemsAdder...");
            boolean success = MergeManager.merge();
            if(success) sender.sendMessage("§a[ModelEngineMerger] Successfully merged ModelEngine and ItemsAdder!");
            else sender.sendMessage("§c[ModelEngineMerger] Error while merging ModelEngine and ItemsAdder, check your paths!");
            return true;
        }

        // Reload Command
        else if(args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("§a[ModelEngineMerger] Reloading config...");
            MEMPlugin.getInstance().reload();
            sender.sendMessage("§a[ModelEngineMerger] Successfully reloaded config!");
            return true;
        }

        // Invalid Command
        else {
            sender.sendMessage("§c[ModelEngineMerger] Unknown subcommand!");
            sender.sendMessage("§c[ModelEngineMerger] Available subcommands: merge, reload");
            return true;
        }
    }
}

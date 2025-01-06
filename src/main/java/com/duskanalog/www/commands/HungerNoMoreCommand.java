package com.duskanalog.www.commands;

import com.duskanalog.www.HungerNoMore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class HungerNoMoreCommand implements CommandExecutor, TabCompleter {
    private final HungerNoMore plugin;

    public HungerNoMoreCommand(HungerNoMore plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin instance cannot be null");
        }
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            // Check if plugin is enabled
            if (!plugin.getConfigManager().isEnabled()) {
                sender.sendMessage(plugin.getConfigManager().getPrefix() + "§cPlugin is currently disabled in config.");
                return true;
            }

            // Check permission
            if (!sender.hasPermission("hungernomore.admin")) {
                sender.sendMessage(plugin.getConfigManager().getNoPermissionMessage());
                return true;
            }

            // Handle reload command
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                handleReload(sender);
                return true;
            }

            // Show help message for invalid or missing arguments
            sendHelpMessage(sender);
            return true;
            
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error executing command: " + e.getMessage(), e);
            sender.sendMessage(plugin.getConfigManager().getPrefix() + "§cAn error occurred while executing the command.");
            return false;
        }
    }

    private void handleReload(CommandSender sender) {
        if (plugin.isDebugEnabled()) {  // Changed from ConfigManager to plugin
            plugin.getLogger().info("Reload command executed by: " + sender.getName());
        }

        if (plugin.reloadPlugin()) {
            sender.sendMessage(plugin.getConfigManager().getPrefix() + 
                             plugin.getConfigManager().getReloadMessage());
        } else {
            sender.sendMessage(plugin.getConfigManager().getPrefix() + 
                             "§cFailed to reload configuration. Check console for details.");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        try {
            // Only suggest if plugin is enabled and sender has permission
            if (!plugin.getConfigManager().isEnabled() || !sender.hasPermission("hungernomore.admin")) {
                return completions;
            }

            // First argument suggestions
            if (args.length == 1) {
                completions.add("reload");
                return filterCompletions(completions, args[0]);
            }

            return completions;
            
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error during tab completion: " + e.getMessage(), e);
            return completions;
        }
    }

    private List<String> filterCompletions(List<String> completions, String partial) {
        List<String> filtered = new ArrayList<>();
        String partialLower = partial.toLowerCase();
        
        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(partialLower)) {
                filtered.add(completion);
            }
        }
        
        return filtered;
    }

    private void sendHelpMessage(CommandSender sender) {
        String prefix = plugin.getConfigManager().getPrefix();
        sender.sendMessage(prefix + "§6HungerNoMore Commands:");
        sender.sendMessage(prefix + "§7/hungernomore reload §f- Reload the configuration");
    }
}

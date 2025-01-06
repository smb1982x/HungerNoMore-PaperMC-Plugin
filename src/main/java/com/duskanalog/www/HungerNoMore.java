package com.duskanalog.www;

import com.duskanalog.www.commands.HungerNoMoreCommand;
import com.duskanalog.www.listeners.ConsumableListener;
import com.duskanalog.www.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerNoMore extends JavaPlugin {
    private ConfigManager configManager;
    private boolean debugMode = false;  // Added to support debug logging

    @Override
    public void onEnable() {
        // Initialize config manager
        this.configManager = new ConfigManager(this);
        
        // Register command
        HungerNoMoreCommand command = new HungerNoMoreCommand(this);
        getCommand("hungernomore").setExecutor(command);
        getCommand("hungernomore").setTabCompleter(command);
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new ConsumableListener(this), this);
        
        getLogger().info("HungerNoMore has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HungerNoMore has been disabled!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public boolean reloadPlugin() {
        try {
            if (debugMode) {
                getLogger().info("Attempting to reload configuration...");
            }
            configManager.loadConfig();
            return true;
        } catch (Exception e) {
            getLogger().severe("Failed to reload plugin: " + e.getMessage());
            return false;
        }
    }

    public boolean isDebugEnabled() {
        return debugMode;
    }
}

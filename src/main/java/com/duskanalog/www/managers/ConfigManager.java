package com.duskanalog.www.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.duskanalog.www.HungerNoMore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ConfigManager {
    private final HungerNoMore plugin;
    private FileConfiguration config;
    private final File configFile;

    public ConfigManager(HungerNoMore plugin) {
        this.plugin = plugin;
        // Ensure correct path separator for OS
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        loadConfig();
    }

    public void loadConfig() {
        try {
            File parentDir = configFile.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new IOException("Failed to create directory: " + parentDir);
            }

            if (!configFile.exists()) {
                // First, save default config to temp file
                File tempFile = new File(plugin.getDataFolder(), "config.yml");
                plugin.saveResource("config.yml", true);
                
                // Then move it to correct location
                Files.move(tempFile.toPath(), configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            this.config = YamlConfiguration.loadConfiguration(configFile);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load config: " + e.getMessage());
            // Create empty config to prevent NPEs
            this.config = new YamlConfiguration();
        }
    }

    public boolean keepHungerFull() {
        return config.getBoolean("hunger.keep-full", true);
    }

    public boolean disableNaturalRegen() {
        return config.getBoolean("hunger.disable-natural-regen", true);
    }

    public double getHungerhealValue() {
        return config.getDouble("health.hungerhealValue", 1.0);
    }

    public double getDamageValue() {
        return config.getDouble("health.damageValue", 1.0);
    }

    public double getCustomPercent() {
        return config.getDouble("health.customPercent", 1.0);
    }

    private String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String getPrefix() {
        return translate(config.getString("messages.prefix", "&7[&6HungerNoMore&7]&r "));
    }

    public String getHealMessage(double amount, String item) {
        String msg = config.getString("messages.heal", "&aYou gained &2{amount}&a health from &2{item}&a!");
        return translate(msg
            .replace("{amount}", String.format("%.1f", amount))
            .replace("{item}", item));
    }

    public String getDamageMessage(double amount, String item) {
        String msg = config.getString("messages.damage", "&cYou lost &4{amount}&c health from &4{item}&c!");
        return translate(msg
            .replace("{amount}", String.format("%.1f", amount))
            .replace("{item}", item));
    }

    public String getReloadMessage() {
        return translate(config.getString("messages.reload", "&aConfiguration reloaded"));
    }

    public String getNoPermissionMessage() {
        return translate(config.getString("messages.no-permission", "&cNo permission"));
    }

    public boolean isEnabled() {
        return true; // Plugin is always enabled as per original spec
    }
}

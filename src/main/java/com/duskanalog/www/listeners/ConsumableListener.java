package com.duskanalog.www.listeners;

import com.duskanalog.www.HungerNoMore;
import com.duskanalog.www.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConsumableListener implements Listener {
    private final ConfigManager config;

    public ConsumableListener(HungerNoMore plugin) {
        this.config = plugin.getConfigManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (config.keepHungerFull()) {
            Player player = event.getPlayer();
            player.setFoodLevel(19);
            player.setSaturation(19f);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (config.keepHungerFull()) {
            event.setFoodLevel(19);
            player.setSaturation(19f);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHealthRegain(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if (config.disableNaturalRegen() && 
            event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem() == null || !event.getItem().getType().isEdible()) return;
        
        Player player = event.getPlayer();
        Material foodType = event.getItem().getType();
        
        // Skip if player has bypass permission
        if (player.hasPermission("hungernomore.bypass")) return;

        // Get the multiplier for this food type
        double multiplier = getFoodMultiplier(foodType);
        
        if (multiplier < 0) {
            // Apply damage for harmful foods
            double damageAmount = config.getDamageValue() * config.getCustomPercent();
            double finalHealth = Math.max(player.getHealth() - damageAmount, 0.0);
            player.setHealth(finalHealth);
            player.sendMessage(config.getDamageMessage(damageAmount, foodType.toString().toLowerCase()));
        } else {
            // Apply healing with appropriate multiplier
            double healAmount = config.getHungerhealValue() * config.getCustomPercent() * multiplier;
            double afterHeal = Math.min(player.getHealth() + healAmount, player.getMaxHealth());
            player.setHealth(afterHeal);
            player.sendMessage(config.getHealMessage(healAmount, foodType.toString().toLowerCase()));
        }
    }

	private double getFoodMultiplier(Material foodType) {
		switch (foodType) {
			// "Harmful" foods
			case ROTTEN_FLESH:
			case SPIDER_EYE:
			case POISONOUS_POTATO:
			case PUFFERFISH:
			case CHICKEN:
				return -1.0; 
				
			// "Raw" foods that heal half
			case MUTTON:
			case BEEF:
			case PORKCHOP:
			case RABBIT:
			case COD:
			case SALMON:
				return 0.5;
				
			// Everything else is default 1.0
			default:
				return 1.0;
		}
	}
}

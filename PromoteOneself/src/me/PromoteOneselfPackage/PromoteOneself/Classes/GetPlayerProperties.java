package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GetPlayerProperties {
	private static PromoteOneselfMainClass plugin; 
	private static LoggingClass logger; 
	public GetPlayerProperties(PromoteOneselfMainClass instance, LoggingClass log) {
		plugin = instance; 
		logger = log; 
	}
	
	public Boolean getInventoryItem(ItemStack item, int amount, Player player) {
		Boolean isThere = null; 
		PlayerInventory inv = player.getInventory(); 
		if (inv.containsAtLeast(item, amount)) {
			isThere = true; 
		}
		else {
			isThere = false; 
		}
		return isThere; 
	}
	public Boolean getPlayerBalance(double neededMoney, Player player) {
		if (plugin.econExists == true) {
			Boolean hasMoney = null; 
			@SuppressWarnings("deprecation")
			double playerMoney = plugin.econ.getBalance(player.getName()); 
			if (playerMoney >= neededMoney) {
				hasMoney = true; 
			}
			else {
				hasMoney = false; 
			}
			return hasMoney; 
		}
		else {
			logger.warning("custom", "There is an aim that needs players to have a certain balance even though this plugin cannot detect vault "); 
			return false; 
		}
	}
	public Boolean getPlayerPrimaryGroup(String group, Player player) {
		if (plugin.permsExist == true) {
			Boolean inGroup = null; 
			String playerGroup = plugin.perms.getPrimaryGroup(player); 
			if (playerGroup.equalsIgnoreCase(group)) {
				inGroup = true; 
			}
			else {
				inGroup = false; 
			}
			return inGroup; 
		}
		else {
			logger.warning("custom", "There is an aim that needs players to have a certain primary group even though this plugin cannot detect vault "); 
			return false; 
		}
	}
	public Boolean getPlayerGroups(String group, Player player) {
		if (plugin.permsExist == true) {
			Boolean inGroup = false; 
			String[] playerGroups = plugin.perms.getPlayerGroups(player); 
			for (String i : playerGroups) {
				if (i.equalsIgnoreCase(group)) {
					inGroup = true; 
					break; 
				}
			}
			return inGroup; 
		}
		else {
			logger.warning("custom", "There is an aim that needs players to be in a certain group even though this plugin cannot detect vault "); 
			return false; 
		}
	}
	public Boolean getPlayerPermissions(String permission, Player player) {
		if (plugin.permsExist == true) {
			return plugin.perms.has(player, permission); 
		}
		else {
			logger.warning("custom", "There is an aim that needs players to have a certain general permission even though this plugin cannot detect vault "); 
			return false; 
		}
	}
	public Boolean getPlayerPoints(int points, Player player) {
		if (plugin.playerPointsExists == true) {
			Boolean hasPoints = null; 
			@SuppressWarnings("deprecation")
			int currentPlayerPoints = plugin.playerPointsPlugin.getAPI().look(player.getName()); 
			if (currentPlayerPoints >= points) {
				hasPoints = true; 
			}
			else {
				hasPoints = false; 
			}
			return hasPoints; 
		}
		else {
			logger.warning("custom", "There is an aim that needs players to have a certain number of player points even though this plugin cannot detect PlayerPoints "); 
			return false; 
		}
	}
	
	public Boolean getGroupPermissions(String permission, String group, String world) {
		if (plugin.yc.configuration.getString("checkLowestRankThatCanManuallyApproveAims") == null) {
			logger.warning("custom", "The 'checkLowestRankThatCanManuallyApproveAims' config field could not be found "); 
			return true; 
		}
		else if ((plugin.permsExist == true) || (!(plugin.yc.configuration.getString("checkLowestRankThatCanManuallyApproveAims").equalsIgnoreCase("never")))) {
			return plugin.perms.groupHas(world, group, permission); 
		}
		else if ((plugin.permsExist == false) && ((plugin.yc.configuration.getString("checkLowestRankThatCanManuallyApproveAims").equalsIgnoreCase("addwarn")) || (plugin.yc.configuration.getString("checkLowestRankThatCanManuallyApproveAims").equalsIgnoreCase("checkwarn")))) {
			logger.warning("custom", "Vault cannot be found so the permission of the lowest group that can manually approve aims cannot be verified "); 
			return false; 
		}
		else {
			return true; 
		}
	}
	
	public String getConfigPlayerspId(String playername, String configRoot) {
		String configspId = null; 
		if (plugin.yd.configuration.getConfigurationSection(configRoot) != null) {
			Set<String> players = plugin.yd.configuration.getConfigurationSection(configRoot).getKeys(false); 
			for (String i : players) {
				if (plugin.yd.configuration.contains(configRoot + "." + i + ".lastUsername")) {
					if (plugin.yd.configuration.getString(configRoot + "." + i + ".lastUsername").equals(playername)) {
						configspId = i; 
						break; 
					}
				}
			}
		}
		return configspId; 
	}
}

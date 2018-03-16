package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyPlayerListener implements Listener{ 
	private static PromoteOneselfMainClass plugin; 
	private static UpdateAims ua; 
	private static List<String> commands = new ArrayList<String>(); 
	public MyPlayerListener(PromoteOneselfMainClass instance, UpdateAims uai) {
		plugin = instance; 
		ua = uai; 
		updateCommandsList(); 
	}
	public static void updateCommandsList() {
		commands.addAll(plugin.yc.configuration.getStringList("commands")); 
		plugin.logger.info("custom", commands.toString()); 
		plugin.logger.info("custom", Integer.toString(commands.size())); 
		
	}
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (plugin.yc.configuration.getBoolean("startInPromotionTree") == true) {
			UUID rpId = event.getPlayer().getUniqueId(); 
			String spId = rpId.toString(); 
			if ((plugin.yd.configuration.contains("exempt." + spId + ".exempt") == true) && (plugin.yd.configuration.getString("exempt." + spId + ".exempt").equalsIgnoreCase("true")) && (plugin.yc.configuration.getBoolean("updateUsernames") == false)) {
				plugin.num = 1; 
			}
			else if ((plugin.yd.configuration.contains("exempt." + spId + ".exempt") == true) && (plugin.yd.configuration.getString("exempt." + spId + ".exempt").equalsIgnoreCase("true")) && (plugin.yc.configuration.getBoolean("updateUsernames") == true)) {
				plugin.yd.configuration.set("exempt." + spId + ".lastUsername", Bukkit.getPlayer(rpId).getName()); 
				plugin.yd.save(); 
				plugin.num = 2; 
			}
			else if ((plugin.yd.configuration.contains("players." + spId + ".finished") == true) && (plugin.yc.configuration.getBoolean("updateUsernames") == false)) {
				plugin.num = 3; 
			}
			else if ((plugin.yd.configuration.contains("players." + spId + ".finished") == true) && (plugin.yc.configuration.getBoolean("updateUsernames") == true)) { 
				plugin.yd.configuration.set("players." + spId + ".lastUsername", Bukkit.getPlayer(rpId).getName()); 
				plugin.yd.save(); 
				plugin.num = 4; 
			}
			else {
				ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, false); 
				plugin.num = 5; 
				if ((plugin.yd.configuration.contains("exempt." + spId + ".exempt") == true) && (plugin.yd.configuration.getString("exempt." + spId + ".exempt").equalsIgnoreCase("add"))) {
					plugin.yd.configuration.set("exempt." + spId, null); 
					plugin.saveFiles(); 
					plugin.num = 6; 
				}
			}
		}
	}
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (plugin.yc.configuration.getBoolean("detectKills") == true) {
			try {
				UUID rpId = event.getEntity().getKiller().getUniqueId(); 
				String spId = rpId.toString(); 
				if ((plugin.yd.configuration.contains("players." + spId + ".finished")) && (event.getEntity().getKiller() instanceof Player)) {
					int kills = plugin.yd.configuration.getInt("players." + spId + ".data.kills"); 
					kills += 1; 
					plugin.yd.configuration.set("players." + spId + ".data.kills", kills); 
					plugin.saveFiles(); 
				}
			}
			catch (Exception e) {
				plugin.logger.warning("custom", "Play death error: " + e.toString()); 
				plugin.logger.exception("death of player error: ", e); 
			}
		}
	}
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		plugin.num2 = 1; 
		if (plugin.yc.configuration.getBoolean("watchCommands") == true) {
			plugin.num2 = 2; 
			plugin.logger.info("custom", Integer.toString(commands.size())); 
			if ((commands.isEmpty() == false) || ((commands.size() > 0) && (!(commands.get(0).equalsIgnoreCase("none"))))) {
				plugin.num2 = 3; 
				for (String i : commands) {
					if (plugin.num2 < 4) {
						plugin.num2 = 4; 
					}
					plugin.logger.info("custom", "Event message: " + event.getMessage()); 
					plugin.logger.info("custom", "command message: " + i); 
					if ((event.getMessage().startsWith("/" + i) == true) || (event.getMessage().startsWith(i) == true)) {
						plugin.num2 = 5; 
						UUID rpId = event.getPlayer().getUniqueId(); 
						String spId = rpId.toString(); 
						for (String j : plugin.yd.configuration.getStringList("players." + spId + ".data.commands")) {
							if (plugin.num2 < 6) {
								plugin.num2 = 6; 
							}
							if ((plugin.yc.configuration.getString("aims." + j + ".achieve").equalsIgnoreCase(i)) || (("/" + plugin.yc.configuration.getString("aims." + j + ".achieve")).equalsIgnoreCase(i))) {
								plugin.num2 = 7; 
								plugin.yd.configuration.set("players." + spId + ".aims." + j, true); 
								event.getPlayer().sendMessage("You have now achieved aim " + j + " "); 
								plugin.saveFiles(); 
							}
						}
					}
				}
			}
		}
	}
}

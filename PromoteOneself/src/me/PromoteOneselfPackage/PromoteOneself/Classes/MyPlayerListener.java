package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		if (plugin.yc.configuration.getBoolean("watchCommands") == true) {
			commands.addAll(plugin.yc.configuration.getStringList("commands")); 
		}
		plugin.logger.info("custom", "Commands being watched: " + commands.toString()); 
	}
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerJoin(PlayerJoinEvent event) {
		UUID rpId = event.getPlayer().getUniqueId(); 
		String spId = rpId.toString(); 
		Boolean updateUsername = plugin.yc.configuration.getBoolean("updateUsernames"); 
		String defaultFirstTarget = plugin.yc.configuration.getString("defaultTarget"); 
		if (plugin.yd.configuration.contains("exempt." + spId) == true) {
			if (plugin.yd.configuration.getString("exempt." + spId + ".exempt").equalsIgnoreCase("true")) {
				if (updateUsername == true) {
					plugin.yd.configuration.set("exempt." + spId + ".lastUsername", event.getPlayer().getName()); 
					plugin.yd.save(); 
				}
			}
			else {
				plugin.yd.configuration.set("exempt." + spId, null); 
				if (plugin.yd.configuration.contains("players." + spId) == false) {
					ua.addPlayer(defaultFirstTarget, spId, rpId, true); 
				}
				plugin.yd.save(); 
			}
		}
		if (plugin.yd.configuration.contains("players." + spId)) {
			if (updateUsername == true) {
				plugin.yd.configuration.set("players." + spId + ".lastUsername", event.getPlayer().getName()); 
				plugin.yd.save(); 
			}
		}
		else if (plugin.yc.configuration.getBoolean("startInPromotionTree") == true) {
			ua.addPlayer(defaultFirstTarget, spId, rpId, true); 
		}
	}
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (plugin.yc.configuration.getBoolean("detectKills") == true) {
			try {
				UUID rpId = event.getEntity().getKiller().getUniqueId(); 
				String spId = rpId.toString(); 
				if ((plugin.yd.configuration.contains("players." + spId)) && (event.getEntity().getKiller() instanceof Player)) {
					int kills = plugin.yd.configuration.getInt("players." + spId + ".data.kills"); 
					kills += 1; 
					plugin.yd.configuration.set("players." + spId + ".data.kills", kills); 
					plugin.saveFiles(); 
				}
			}
			catch (Exception e) {
				plugin.logger.warning("custom", "Player death error: " + e.toString()); 
				plugin.logger.exception("death of player error: ", e); 
			}
		}
	}
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (plugin.yc.configuration.getBoolean("watchCommands") == true) {
			if ((commands.isEmpty() == false) || ((commands.size() > 0) && (!(commands.get(0).equalsIgnoreCase("none"))))) {
				for (String i : commands) {
					//plugin.logger.info("custom", "Event message: " + event.getMessage()); 
					//plugin.logger.info("custom", "command message: " + i); 
					if ((event.getMessage().startsWith("/" + i) == true) || (event.getMessage().startsWith(i) == true)) {
						UUID rpId = event.getPlayer().getUniqueId(); 
						String spId = rpId.toString(); 
						for (String j : plugin.yd.configuration.getStringList("players." + spId + ".data.commands")) {
							if ((plugin.yc.configuration.getString("aims." + j + ".achieve").equalsIgnoreCase(i)) || (("/" + plugin.yc.configuration.getString("aims." + j + ".achieve")).equalsIgnoreCase(i))) {
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

package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpdateAims {
	private static PromoteOneselfMainClass plugin; 
	private static LoggingClass logger; 
	private static  GetPlayerProperties props; 
	public UpdateAims(PromoteOneselfMainClass instance, LoggingClass log, GetPlayerProperties pp) {
		plugin = instance; 
		logger = log; 
		props = pp; 
	}
	
	public void addPlayer(String target, String spId, UUID rpId, Boolean isNew) {
		if (isNew == true) {
			plugin.yd.configuration.createSection("players." + spId); 
			plugin.yd.configuration.set("players." + spId + ".lastUsername", Bukkit.getPlayer(rpId).getName()); 
		}
		plugin.yd.configuration.set("players." + spId + ".target", target); 
		plugin.yd.configuration.set("players." + spId + ".finished", false); 
		plugin.yd.configuration.createSection("players." + spId + ".data.password"); 
		plugin.yd.configuration.set("players." + spId + ".data.points", plugin.yc.configuration.getInt("defaultPoints")); 
		plugin.yd.configuration.set("players." + spId + ".data.kills", 0); 
		plugin.yd.configuration.createSection("players." + spId + ".aims"); 
		List<String> commandAims = new ArrayList<String>();  
		List<String> targetAims = plugin.yc.configuration.getStringList("targets." + target + ".aims"); 
		for (String i : targetAims) {
			plugin.yd.configuration.set("players." + spId + ".aims." + i, false); 
			if (plugin.yc.configuration.getString("aims." + i + ".type").equalsIgnoreCase("password")) {
				plugin.yd.configuration.set("players." + spId + ".data.password." + i, "none"); 
			}
			else if (plugin.yc.configuration.getString("aims." + i + ".type").equalsIgnoreCase("command")) {
				commandAims.add(i); 
			}
		}
		plugin.yd.configuration.set("players." + spId + ".data.commands", commandAims); 
		plugin.yd.configuration.createSection("players." +  spId + ".data.signs"); 
		try {
			Set<String> rawSigns = plugin.ys.configuration.getConfigurationSection("signs").getKeys(false); 
			List<String> signs = new ArrayList<String>(rawSigns); 
			for (String j : signs) {
				plugin.yd.configuration.set("players." + spId + ".data.signs." + j, 0); 
			}
		}
		catch (NullPointerException e) {
			// No action required 
		}
		Bukkit.getPlayer(rpId).sendMessage(ChatColor.AQUA + logger.getName(true) + "Your target is now: " + target); 
		plugin.saveFiles(); 
	}
	public void password (CommandSender sender, String[] args) {
		if (args.length == 3)  {
			if ((sender instanceof Player) && (args[1].equalsIgnoreCase("get"))) {
				Player player = (Player) sender; 
				UUID rpId = player.getUniqueId(); 
				String spId = rpId.toString(); 
				if (plugin.yd.configuration.contains("players." + spId)) {
					if (plugin.yd.configuration.contains("players." + spId + ".data.password." + args[2])) {
						if (sender.hasPermission("pos.password.get")) {
							sender.sendMessage(plugin.yd.configuration.getString("players." + spId + ".data.password." + args[2])); 
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else {
						logger.messageSender(sender, "noaim", "as its entry cannot be found amongst the your stored data "); 
					}
				}
				else {
					logger.messageSender(sender, "noplayer", null); 
				}
			}
			else if ((sender instanceof Player) && (!(args[1].equalsIgnoreCase("get")))) {
				logger.messageSender(sender, "custom", ChatColor.RED + "This command with this arrangement of arguments must be to get a password, not to set it "); 
			}
			else {
				logger.messageSender(sender, "wrongarrangement", null); 
			}
		}
		else if (args.length == 4) {
			if (sender instanceof Player && args[1].equalsIgnoreCase("set")) {
				Player player = (Player) sender; 
				UUID rpId = player.getUniqueId(); 
				String spId = rpId.toString(); 
				if (plugin.yd.configuration.contains("players." + spId + ".data.password." + args[3])) {
					if (plugin.yd.configuration.contains("players." + spId + ".data.password." + args[3])) {
						if (sender.hasPermission("pos.password.set")) {
							plugin.yd.configuration.set("players." + spId + ".data.password." + args[3], args[2]); 
							sender.sendMessage("Your password is now " + args[2] + " "); 
							plugin.saveFiles(); 
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else {
						logger.messageSender(sender, "noaim", "as its entry cannot be found amongst the your stored data "); 
					}
				}
				else {
					logger.messageSender(sender, "noplayer", null); 
				}
			}
			else if (args[1].equalsIgnoreCase("get")) {
				if (sender.hasPermission("pos.password.get.others")) {
					String spId = props.getConfigPlayerspId(args[2], "players"); 
					if (spId != null) {
						if (plugin.yd.configuration.contains("players." + spId + ".data.password." + args[3])) {
							sender.sendMessage(plugin.yd.configuration.getString("players." + spId + ".data.password." + args[3])); 
						}
						else {
							logger.messageSender(sender, "custom", "The given aim does not have a password slot listed with the given player "); 
						}
					}
					else {
						logger.messageSender(sender, "offline", " as no data about it could be found in the config file ");  
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else {
				logger.messageSender(sender, "help", null); 
			}
		}
		else if (args.length == 5) {
			if (args[1].equalsIgnoreCase("set")) {
				if (sender.hasPermission("pos.password.set.others") || sender.hasPermission("pos.set.player.password")) {
					String spId = props.getConfigPlayerspId(args[2], "players"); 
					if (spId != null) {
						if (plugin.yd.configuration.contains("players." + spId + ".data.password." + args[4])) {
							plugin.yd.configuration.set("players." + spId + ".data.password." + args[4], args[3]); 
							sender.sendMessage(args[2] + "'s password is now " + args[3] + " "); 
							UUID rpId = UUID.fromString(spId); 
							Player player = Bukkit.getPlayer(rpId); 
							if (player != null) {
								player.sendMessage("Your password is now " + args[3] + " "); 
							}
							plugin.saveFiles(); 
						}
						else {
							logger.messageSender(sender, "custom", "The given aim does not have a password slot listed with the given player "); 
						}
					}
					else {
						logger.messageSender(sender, "offline", " as no data about it could be found in the config file ");  
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else {
				logger.messageSender(sender, "help", null); 
			}
		}
		else {
			logger.messageSender(sender, "help", null); 
		}
	}
	public void updatePlayer(CommandSender sender, String[] args) {
		updatePlayer(sender, args, false); 
	}
	public void updatePlayer(CommandSender sender, String[] args, Boolean signUser) {
		if (args.length == 1) { 
			if (sender instanceof Player) { 
				Player player = (Player) sender; 
				UUID rpId = player.getUniqueId(); 
				String spId = rpId.toString(); 
				if (plugin.yd.configuration.contains("exempt." + spId + ".exempt")) {
					logger.messageSender(sender, "exemptplayer", null); 
				}
				else if (plugin.yd.configuration.contains("players." + spId) != true) { 
					if (plugin.yc.configuration.getBoolean("startInPromotionTree") == true) {
						sender.sendMessage(ChatColor.RED + "There are no data stored about you "); 
						logger.warning("noPlayer", player.getName()); 
					}
					else {
						if (sender.hasPermission("pos.update.add")) {
							addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, true); 
							player.sendMessage("You have added yourself to the promotion tree "); 
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
 					}
				}
				else if (plugin.yd.configuration.getString("players." + spId + ".finished").equalsIgnoreCase("true")) {
					sender.sendMessage(ChatColor.RED + "You have already reached the highest possible target "); 
				}
				else {
					if (sender.hasPermission("pos.update")) {
						Boolean progress = updatePlayerAims(spId, player, false, args); 
						logger.messageSender(sender, "aimupdate", null); 
						if (progress == true) {
							String nextTarget = plugin.yc.configuration.getString("targets." + plugin.yd.configuration.getString("players." + spId + ".target") + ".defaultNextTarget"); 
							updatePlayerTargets(spId, rpId, nextTarget, player, sender); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
			}
			else {
				logger.messageSender(sender, "wrongArrangement", null); 
			}
		}
		else if (args.length == 2) {
			if (sender instanceof Player) { 
				Player player = (Player) sender; 
				UUID rpId = player.getUniqueId(); 
				String spId = rpId.toString(); 
				if (plugin.yd.configuration.contains("exempt." + spId + ".exempt")) {
					logger.messageSender(sender, "exemptplayer", null); 
				}
				else if (plugin.yd.configuration.contains("players." + spId) != true) {
					if (plugin.yc.configuration.getBoolean("startInPromotionTree") == true) {
						sender.sendMessage(ChatColor.RED + "There are no data stored about you "); 
						logger.warning("noPlayer", player.getName()); 
					}
					else {
						if ((sender.hasPermission("pos.update.add") && sender.hasPermission("pos.update.target")) || ((signUser == true) && sender.hasPermission("pos.sign.update.use.add"))) {
							addPlayer(args[1], spId, rpId, true); 
							player.sendMessage("You have added yourself to the promotion tree "); 
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
 					}
				}
				else if (plugin.yd.configuration.getString("players." + spId + ".finished").equalsIgnoreCase("true")) {
					sender.sendMessage(ChatColor.RED + player.getName() + " has already reached the highest possible target "); 
				}
				else if (plugin.yc.configuration.contains("targets." + args[1]) != true && !(args[1].equalsIgnoreCase("none"))) { 
					sender.sendMessage(ChatColor.RED + "The target you specified does not exist "); 
				}
				else {
					if (sender.hasPermission("pos.update.target") || (signUser == true)) {
						Boolean progress = updatePlayerAims(spId, player, false, args); 
						logger.messageSender(sender, "aimupdate", null); 
						if (progress == true) {
							updatePlayerTargets(spId, rpId, args[1], player, sender); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
			}
			else {
				logger.messageSender(sender, "wrongArrangement", null); 
			}
		}
		else if (args.length == 3) {
			@SuppressWarnings("deprecation")
			Player player = Bukkit.getPlayer(args[2]); 
			if (player != null) {
				UUID rpId = player.getUniqueId(); 
				String spId = rpId.toString(); 
				if (plugin.yd.configuration.contains("exempt." + spId + ".exempt")) {
					logger.messageSender(sender, "exemptplayer", null); 
				}
				else if (plugin.yd.configuration.contains("players." + spId) != true) {
					if (plugin.yc.configuration.getBoolean("startInPromotionTree") == true) {
						sender.sendMessage(ChatColor.RED + "There are no data stored about the specified player "); 
						logger.warning("noPlayer", player.getName()); 
					}
					else {
						if (sender.hasPermission("pos.update.add.others") && sender.hasPermission("pos.update.target.others")) {
							addPlayer(args[1], spId, rpId, true); 
							sender.sendMessage(args[2] + " has been added to the promotion tree "); 
							player.sendMessage("You have been added to the promotion tree on target " + args[1]); 
						}
						else if (sender.hasPermission("pos.update.add.others") && args[1].equalsIgnoreCase(plugin.yc.configuration.getString("defaultTarget"))) {
							addPlayer(args[1], spId, rpId, true); 
							sender.sendMessage(args[2] + " has been added to the promotion tree "); 
							player.sendMessage("You have been added to the promotion tree on target " + args[1]); 
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
 					}
				}
				else if (plugin.yd.configuration.getString("players." + spId + ".finished").equalsIgnoreCase("true")) {
					sender.sendMessage(ChatColor.RED + player.getName() + " has already reached the highest possible target "); 
				}
				else if (plugin.yc.configuration.contains("targets." + args[1]) != true && !(args[1].equalsIgnoreCase("none"))) {
					sender.sendMessage(ChatColor.RED + "The target you specified does not exist "); 
				}
				else {
					if (sender.hasPermission("pos.update.target.others") && sender.hasPermission("pos.update.others")) {
						Boolean progress = updatePlayerAims(spId, player, false, args); 
						logger.messageSender(sender, "aimupdate", null); 
						if (progress == true) {
							updatePlayerTargets(spId, rpId, args[1], player, sender); 
						}
					}
					else if (sender.hasPermission("pos.update.others") && args[1].equalsIgnoreCase(plugin.yc.configuration.getString("defaultNextTarget"))) {
						Boolean progress = updatePlayerAims(spId, player, false, args); 
						logger.messageSender(sender, "aimupdate", null); 
						if (progress == true) {
							updatePlayerTargets(spId, rpId, args[1], player, sender); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
			}
			else {
				logger.messageSender(sender, "offline", ", it could be offline "); 
			}
		}
		else if (args.length == 4) {
			@SuppressWarnings("deprecation")
			Player player = Bukkit.getPlayer(args[2]); 
			if (player != null) {
				UUID rpId = player.getUniqueId(); 
				String spId = rpId.toString(); 
				if (plugin.yd.configuration.contains("exempt." + spId + ".exempt")) {
					logger.messageSender(sender, "exemptplayer", null); 
				}
				else if (plugin.yd.configuration.contains("players." + spId) != true) {
					if (plugin.yc.configuration.getBoolean("startInPromotionTree") == true) {
						sender.sendMessage(ChatColor.RED + "There is no data stored about the specified player "); 
					}
					else {
						sender.sendMessage(ChatColor.RED + "The specified player must already be on the promotion tree for this command with this arrangement of arguments to work "); 
 					}
				}
				else if (plugin.yd.configuration.getString("players." + spId + ".finished").equalsIgnoreCase("true")) {
					sender.sendMessage(ChatColor.RED + player.getName() + " has already reached the highest possible target "); 
				}
				else if (plugin.yc.configuration.contains("targets." + args[1]) != true && !(args[1].equalsIgnoreCase("none"))) {
					sender.sendMessage(ChatColor.RED + "The target you specified does not exist "); 
				}
				else if (plugin.yc.configuration.contains("aims." + args[3]) != true) {
					sender.sendMessage(ChatColor.RED + "The aim you specified does not exist "); 
				}
				else {
					if (sender.hasPermission("pos.update.aim.others")) {
						updatePlayerAims(spId, player, true, args); 
						logger.messageSender(sender, "aimupdate", null); 
					}
					else if ((sender instanceof Player) && ((Player) sender == player) && (sender.hasPermission("pos.update.aim"))) {
						updatePlayerAims(spId, player, true, args); 
						logger.messageSender(sender, "aimupdate", null); 
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
			}
			else {
				logger.messageSender(sender, "offline", ", it could be offline "); 
			}
		}
		else {
			logger.messageSender(sender, "argserror", null); 
			logger.warning("updatePlayerError", null); 
		}
	}
	public void listValues(CommandSender sender, String[] args) {
		if (args[1].equalsIgnoreCase("targets")) {
			if (sender.hasPermission("pos.list.targets")) {
				if (plugin.yc.configuration.getConfigurationSection("targets") != null) {
					Set<String> targets = plugin.yc.configuration.getConfigurationSection("targets").getKeys(false); 
					if (targets == null || targets.size() == 0 || targets.isEmpty()) {
						logger.messageSender(sender, "custom", "There are no targets "); 
					}
					else {
						logger.messageSender(sender, "custom", targets.toString()); 
					}
				}
				else {
					logger.messageSender(sender, "custom", "There are no targets "); 
				}
			}
			else {
				logger.messageSender(sender, "nopermission", null); 
			}
		}
		else if (args[1].equalsIgnoreCase("aims")) {
			if (sender.hasPermission("pos.list.aims")) {
				if (plugin.yc.configuration.getConfigurationSection("aims") != null) {
					Set<String> aims = plugin.yc.configuration.getConfigurationSection("aims").getKeys(false); 
					if (aims == null || aims.size() == 0 || aims.isEmpty()) {
						logger.messageSender(sender, "custom", "There are no aims "); 
					}
					else {
						sender.sendMessage(aims.toString()); 
					}
				}
				else {
					logger.messageSender(sender, "custom", "There are no aims "); 
				}
			}
			else {
				logger.messageSender(sender, "nopermission", null); 
			}
		}
		else if (args[1].equalsIgnoreCase("players")) {
			if (sender.hasPermission("pos.list.players")) {
				if (plugin.yd.configuration.getConfigurationSection("players") != null) {
					Set<String> players = plugin.yd.configuration.getConfigurationSection("players").getKeys(false); 
					List<String> playerUsernames = new ArrayList<String>(); 
					for (String i : players) {
						playerUsernames.add(plugin.yd.configuration.getString("players." + i + ".lastUsername")); 
					}
					if (playerUsernames == null || playerUsernames.size() == 0 || playerUsernames.isEmpty()) {
						logger.messageSender(sender, "custom", "There are no players "); 
					}
					else {
						sender.sendMessage(playerUsernames.toString()); 
					}
				}
				else {
					logger.messageSender(sender, "custom", "There are no players "); 
				}
			}
			else {
				logger.messageSender(sender, "nopermission", null); 
			}
		}
		else if (args[1].equalsIgnoreCase("exempt")) {
			if (sender.hasPermission("pos.list.exempt")) {
				if (plugin.yd.configuration.getConfigurationSection("exempt") != null) {
					Set<String> players = plugin.yd.configuration.getConfigurationSection("exempt").getKeys(false); 
					List<String> playerUsernames = new ArrayList<String>(); 
					for (String i : players) {
						playerUsernames.add(plugin.yd.configuration.getString("exempt." + i + ".lastUsername")); 
					}
					if (playerUsernames == null || playerUsernames.size() == 0 || playerUsernames.isEmpty()) {
						logger.messageSender(sender, "custom", "There are no exempt players "); 
					}
					else {
						sender.sendMessage(playerUsernames.toString()); 
					}
				}
				else {
					logger.messageSender(sender, "custom", "There are no exempt players "); 
				}
			}
			else {
				logger.messageSender(sender, "nopermission", null); 
			}
		}
		else if (args[1].equalsIgnoreCase("signs")) {
			if (sender.hasPermission("pos.list.signs")) {
				if (plugin.ys.configuration.getConfigurationSection("signs") != null) {
					Set<String> signs = plugin.ys.configuration.getConfigurationSection("signs").getKeys(false); 
					if (signs == null || signs.size() == 0 || signs.isEmpty()) {
						logger.messageSender(sender, "custom", "There are no signs "); 
					}
					else {
						sender.sendMessage(signs.toString()); 
					}
				}
				else {
					logger.messageSender(sender, "custom", "There are no signs "); 
				}
			}
			else {
				logger.messageSender(sender, "nopermission", null); 
			}
		}
		else if (args[1].equalsIgnoreCase("configs")) {
			if (sender.hasPermission("pos.list.configs")) {
				sender.sendMessage("The config files are: config.yml, players.yml and signs.yml"); 
			}
			else {
				logger.messageSender(sender, "nopermission", null); 
			}
		}
		else {
			logger.messageSender(sender, "custom", "The object type requested is not valid; it should be one of players, exempt, signs, configs, targets or aims"); 
		}
	}
	private Boolean updatePlayerAims(String spId, Player player, Boolean aimSpecific, String[] args) {
		Boolean result = true; 
		if (aimSpecific == false) {
			List<Boolean> aimsAchieved = new ArrayList<Boolean>(); 
			String target = plugin.yd.configuration.getString("players." + spId + ".target"); 
			List<String> aims = plugin.yc.configuration.getStringList("targets." + target + ".aims"); 
			for (String i : aims) {
				String aimType = plugin.yc.configuration.getString("aims." + i + ".type"); 
				Boolean changed = false; 
				changed = checkPlayerAims(aimType, i, player, spId); 
				if (changed == false) {
					aimsAchieved.add(plugin.yd.configuration.getBoolean("players." + spId + ".aims." + i)); 
				}
				else {
					aimsAchieved.add(changed); 
				}
			}
			plugin.saveFiles(); 
			for (Boolean j : aimsAchieved) {
				if (j != true) {
					result = false; 
				}
			}
			return result; 
		}
		else if (aimSpecific == true) {
			String aimType = plugin.yc.configuration.getString("aims." + args[3] + ".type"); 
			checkPlayerAims(aimType, args[3], player, spId); 
			plugin.saveFiles(); 
			return false; 
		}
		else {
			return false; 
		}
	}
	@SuppressWarnings("deprecation")
	private Boolean checkPlayerAims(String aimType, String aim, Player player, String spId) {
		Boolean changed = false; 
		if (aimType.equalsIgnoreCase("none")) {
			// No action required 
		}
		else if (aimType.equalsIgnoreCase("xp")) {
			int aimGoal = plugin.yc.configuration.getInt("aims." + aim + ".achieve"); 
			if (player.getTotalExperience() >= aimGoal) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("xpl")) {
			int aimGoal = plugin.yc.configuration.getInt("aims." + aim + ".achieve"); 
			if (player.getExpToLevel() >= aimGoal) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("item") || aimType.equalsIgnoreCase("itemid")) {
			String itemAndAmount = plugin.yc.configuration.getString("aims." + aim + ".achieve"); 
			String[] itemAmountArray = itemAndAmount.split(";"); 
			if (itemAmountArray.length == 2) {
				Material material = null; 
				Boolean success = true; 
				if (aimType.equalsIgnoreCase("item")) {
					material = Material.getMaterial(itemAmountArray[1]); 
				}
				else {
					int id = 0; 
					try {
						id = Integer.parseInt(itemAmountArray[1]); 
					}
					catch (NumberFormatException e) {
						success = false; 
						logger.warning("custom", aim + " is an 'itemid' type aim that uses a string to specify the item instead of a number "); 
					}
					if (success == true) {
						material = Material.getMaterial(id); 
					}
				}
				int amount = 1; 
				try {
					amount = Integer.parseInt(itemAmountArray[0]); 
				}
				catch (NumberFormatException e) {
					success = false; 
					logger.warning("custom", aim + " is an 'item' type aim that uses a string to specify the number of items required rather than a number "); 
				}
				if (material == null) {
					success = false; 
					logger.warning("custom", "Aim " + aim + " seems to contain an invalid material value "); 
				}
				if (success == true) {
					ItemStack item = new ItemStack(material); 
					Boolean isThere = props.getInventoryItem(item, amount, player); 
					if (isThere == true) {
						changed = true; 
						plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
					}
				}
				else {
					logger.messageSender((CommandSender)player, "custom", "There was an error parsing the data for aim " + aim); 
				}
			}
			else {
				logger.warning("custom", aim + " does not use a ';' to separate its arguments for its goal "); 
			}
		}
		else if (aimType.equalsIgnoreCase("password")) {
			String aimPassword = plugin.yc.configuration.getString("aims." + aim + ".achieve"); 
			String playerPassword = plugin.yd.configuration.getString("players." + spId + ".data.password." + aim); 
			if (aimPassword.equals(playerPassword)) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
				plugin.yd.configuration.set("players." + spId + ".data.password." + aim, "none"); 
			}
		}
		else if (aimType.equalsIgnoreCase("points")) {
			int aimPoints = plugin.yc.configuration.getInt("aims." + aim + ".achieve"); 
			int playerPoints = plugin.yd.configuration.getInt("players." + spId + ".data.points"); 
			if (playerPoints >= aimPoints) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
				if (plugin.yc.configuration.getBoolean("resetPointsAfterEachPromotion") == true)  {
					plugin.yd.configuration.set("players." + spId + ".data.points", 0); 
				}
			}
		}
		else if (aimType.equalsIgnoreCase("playerpoints")) {
			int aimPoins = plugin.yc.configuration.getInt("aims." + aim + ".achieve"); 
			Boolean hasPoints = props.getPlayerPoints(aimPoins, player); 
			if (hasPoints == true) {
				changed  = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("kills")) {
			int aimKills = plugin.yc.configuration.getInt("aims." + aim + ".achieve"); 
			int playerKills = plugin.yd.configuration.getInt("players." + spId + ".data.kills"); 
			if (playerKills >= aimKills) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("economy")) {
			double aimMoney = plugin.yc.configuration.getDouble("aims." + aim + ".achieve"); 
			Boolean hasMoney = props.getPlayerBalance(aimMoney, player); 
			if (hasMoney == true) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("group")) {
			String groupName = plugin.yc.configuration.getString("aims." + aim + ".achieve"); 
			Boolean isMember = props.getPlayerGroups(groupName, player); 
			if (isMember == true) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("pgroup")) {
			String groupName = plugin.yc.configuration.getString("aims." + aim + ".achieve"); 
			Boolean isMember = props.getPlayerPrimaryGroup(groupName, player); 
			if (isMember == true) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("permission")) {
			String target = plugin.yc.configuration.getString("aims." + aim + ".achieve"); 
			if (target.equalsIgnoreCase(plugin.yd.configuration.getString("players." + spId + ".target"))) { 
				if ((player.hasPermission("pos.promote." + target)) || (player.hasPermission("pos.promote.*"))) {
					changed = true; 
					plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
				}
			}
		}
		else if (aimType.equalsIgnoreCase("generalpermission")) {
			String permission = plugin.yc.configuration.getString("aims." + aim + ".achieve"); 
			Boolean hasPermission = props.getPlayerPermissions(permission, player); 
			if (hasPermission == true) {
				changed = true; 
				plugin.yd.configuration.set("players." + spId + ".aims." + aim, changed); 
			}
		}
		else if (aimType.equalsIgnoreCase("command")) {
			//Value set upon command 
		}
		else if (aimType.equalsIgnoreCase("sign")) {
			//Value set upon sign click 
		}
		else {
			player.sendMessage("Aim type unrecognised"); 
			logger.warning("aimType", aimType); 
		}
		return changed; 
	}
	private void updatePlayerTargets(String spId, UUID rpId, String nextTarget, Player player, CommandSender sender) {
		Boolean runTheCommands = true; 
		String target = plugin.yd.configuration.getString("players." + spId + ".target"); 
		Boolean doesLeadTo = false; 
		List<String> targetLeadsTo = plugin.yc.configuration.getStringList("targets." + target + ".leadsTo"); 
		for (String k : targetLeadsTo) {
			if (k.equalsIgnoreCase(nextTarget)) {
				doesLeadTo = true; 
			}
		}
		if (doesLeadTo == true) {
			if (nextTarget.equalsIgnoreCase("none")) { 
				if (targetLeadsTo.get(0).equalsIgnoreCase("none") || plugin.yc.configuration.getString("targets." + target + ".leadsTo").equalsIgnoreCase("none")) {
					player.sendMessage(ChatColor.GREEN + "You have reached the highest self-promotion possible "); 
					plugin.yd.configuration.set("players." + spId + ".finished", true); 
					logger.info("custom", ChatColor.GREEN + Bukkit.getPlayer(rpId).getName() + " has reached the highest self-promotion possible "); 
				}
				else {
					sender.sendMessage(ChatColor.RED + "A target to progress to must be specified "); 
					runTheCommands = false; 
				}
			}
			else {
				plugin.yd.configuration.set("players." + spId + ".target", nextTarget); 
				plugin.yd.configuration.set("players." + spId + ".data.password", null); 
				plugin.yd.configuration.set("players." + spId + ".data.commands", null); 
				List<String> commands = new ArrayList<String>(); 
				List<String> newAims = plugin.yc.configuration.getStringList("targets." + nextTarget + ".aims"); 
				plugin.yd.configuration.set("players." + spId + ".aims", null); 
				for (String i : newAims) {
					plugin.yd.configuration.set("players." + spId + ".aims." + i, false); 
					if (plugin.yc.configuration.getString("aims." + i + ".type").equalsIgnoreCase("password")) {
						plugin.yd.configuration.set("players." + spId + ".data.password." + i, "none"); 
					}
					else if (plugin.yc.configuration.getString("aims." + i + ".type").equalsIgnoreCase("command")) {
						commands.add(i); 
					}
				}
				plugin.yd.configuration.set("players." + spId + ".data.commands", commands); 
				player.sendMessage("Your target is now " + nextTarget); 
			}
			if (runTheCommands == false) {
				// No action required 
			}
			else if (plugin.yc.configuration.getString("targets." + target + ".commands").equalsIgnoreCase("none")) {
				// No action required 
			}
			else if (plugin.yc.configuration.getStringList("targets." + target + ".commands").get(0).equalsIgnoreCase("none")) { 
				// No action required 
			} 
			else {
				for (String j : plugin.yc.configuration.getStringList("targets." + target + ".commands")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), j); 
				}
			}
			plugin.saveFiles(); 
		}
		else {
			sender.sendMessage(ChatColor.RED + "The specified target is not a valid target to progress to "); 
		}
	}
	
	public void promoteCommand(CommandSender sender, String playerName) {
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName);  
		if (player == null) {
			logger.messageSender(sender, "offline", ", it could be offline "); 
		}
		else {
			UUID rpId = player.getUniqueId(); 
			String spId = rpId.toString();
			if (plugin.yc.configuration.contains("players." + spId)) {
				String nextTarget = plugin.yc.configuration.getString("targets." + plugin.yd.configuration.getString("players." + spId + ".target") + ".defaultNextTarget"); 
				String[] args = { "update", nextTarget, playerName }; 
				updatePlayer(sender, args); 
			}
			else {
				logger.messageSender(sender, "noplayer", playerName); 
			}
		}
	}
}

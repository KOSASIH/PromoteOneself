package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckPlayers {

	private static PromoteOneselfMainClass plugin; 
	private static LoggingClass logger; 
	private static UpdateAims ua; 
	public CheckPlayers(PromoteOneselfMainClass instance, LoggingClass log, UpdateAims uai) {
		plugin = instance; 
		logger = log; 
		ua = uai; 
	}
	
	public void exemptPlayer (CommandSender sender, String[] args) {
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(args[1]); 
		if (player != null) {
			UUID rpId = player.getUniqueId(); 
			String spId = rpId.toString(); 
			if (args[2].equalsIgnoreCase("true")) {
				if (sender.hasPermission("pos.exempt.true")) {
					plugin.yd.configuration.set("exempt." + spId + ".lastUsername", args[1]); 
					plugin.yd.configuration.set("exempt." + spId + ".exempt", "true"); 
					plugin.yd.configuration.set("players." + spId, null); 
					sender.sendMessage(args[1] + " is now exempt from the promotion tree "); 
					player.sendMessage("You are now exempt from the promotion tree "); 
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
 			}
			else if (args[2].equalsIgnoreCase("temp")) {
				if (sender.hasPermission("pos.exempt.temp")) {
					plugin.yd.configuration.set("exempt." + spId + ".lastUsername", args[1]); 
					plugin.yd.configuration.set("exempt." + spId + ".exempt", "true"); 
					sender.sendMessage(args[1] + " is now exempt from the promotion tree "); 
					player.sendMessage("You are now exempt from the promotion tree "); 
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else if (args[2].equalsIgnoreCase("add")) {
				if (plugin.yd.configuration.contains("exempt." + spId + ".exempt")) {
					if (sender.hasPermission("pos.exempt.add")) {
						plugin.yd.configuration.set("exempt." + spId + ".exempt", "add"); 
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "The specified player is not currently exempt "); 
				}
			}
			else if (args[2].equalsIgnoreCase("join")) {
				if (plugin.yd.configuration.contains("exempt." + spId + ".exempt")) {
					if (sender.hasPermission("pos.exempt.join")) {
						if (plugin.yd.configuration.contains("players." + spId)) {
						}
						else {
							ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, true); 
						}
						plugin.yd.configuration.set("exempt." + spId, null); 
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "The specified player is not currently exempt "); 
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "The exmption command can accept the following parameters after the name of the player: true, add, join "); 
			}
		}
		else {
			logger.messageSender(sender, "offline", ", it could be offline "); 
		}
		plugin.saveFiles(); 
	}
	public void playerAddRemove(CommandSender sender, String[] args) {
		if (args[1].equalsIgnoreCase("reset")) {
			if (args.length == 2) {
				if (sender instanceof Player) {
					if (sender.hasPermission("pos.player.reset")) {
						Player player = (Player) sender; 
						UUID rpId = player.getUniqueId(); 
						String spId = rpId.toString(); 
						if (plugin.yd.configuration.contains("players." + spId)) {
							ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, false); 
						}
						else {
							if (sender.hasPermission("pos.update.add")) {
								sender.sendMessage(plugin.yc.configuration.getString("defaultTarget")); 
								ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, true); 
							}
							else {
								logger.messageSender(sender, "noplayer", null); 
							}
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else {
					logger.messageSender(sender, "wrongarrangement", null); 
				}
			}
			else if (args.length == 3) {
				if (sender.hasPermission("pos.player.reset.others")) {
					@SuppressWarnings("deprecation")
					Player player = Bukkit.getPlayer(args[2]); 
					if (player != null) {
						UUID rpId = player.getUniqueId(); 
						String spId = rpId.toString(); 
						if (plugin.yd.configuration.contains("players." + spId)) {
							ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, false); 
						}
						else {
							if (sender.hasPermission("pos.update.add.others")) {
								ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, true); 
							}
							else {
								sender.sendMessage(ChatColor.RED + "The specified player is not yet in the promotion tree "); 
							}
						}
					}
					else {
						logger.messageSender(sender, "offline", ", it could be offline "); 
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else {
				logger.messageSender(sender, "argserror", null); 
				logger.warning("updatePlayerError", null); 
			}
		}
		else if (args[1].equalsIgnoreCase("delete")) {
			if (args.length == 2) {
				if (sender instanceof Player) {
					if (sender.hasPermission("pos.player.delete")) {
						Player player = (Player) sender; 
						UUID rpId = player.getUniqueId(); 
						String spId = rpId.toString(); 
						plugin.yd.configuration.set("players." + spId, null); 
						player.sendMessage("You are no longer in the promotion tree "); 
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else {
					logger.messageSender(sender, "wrongarrangement", null); 
				}
			}
			else if (args.length == 3) {
				if (sender.hasPermission("pos.player.delete.others")) {
					@SuppressWarnings("deprecation")
					Player player = Bukkit.getPlayer(args[2]); 
					if (player != null) {
						UUID rpId = player.getUniqueId(); 
						String spId = rpId.toString(); 
						plugin.yd.configuration.set("players." + spId, null); 
						player.sendMessage("You are no longer in the promotion tree "); 
					}
					else {
						logger.messageSender(sender, "offline", ", it could be offline "); 
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else {
				logger.messageSender(sender, "argserror", null); 
				logger.warning("updatePlayerError", null); 
			}
		}
		else if (args[1].equalsIgnoreCase("add")) {
			if (args.length == 2) {
				if (sender instanceof Player) {
					if (sender.hasPermission("pos.update.add")) {
						Player player = (Player) sender; 
						UUID rpId = player.getUniqueId(); 
						String spId = rpId.toString(); 
						if (plugin.yd.configuration.contains("players." + spId)) {
							sender.sendMessage(ChatColor.RED + "There are already data stored about this player ");
						}
						else {
							ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, true); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else {
					logger.messageSender(sender, "wrongarrangement", null); 
				}
			}
			else if (args.length == 3) {
				if (sender.hasPermission("pos.update.add.others")) {
					@SuppressWarnings("deprecation")
					Player player = Bukkit.getPlayer(args[2]); 
					if (player != null) {
						UUID rpId = player.getUniqueId(); 
						String spId = rpId.toString(); 
						if (plugin.yd.configuration.contains("players." + spId)) {
							sender.sendMessage(ChatColor.RED + "There are already data stored about this player ");
						}
						else {
							ua.addPlayer(plugin.yc.configuration.getString("defaultTarget"), spId, rpId, true); 
						}
					}
					else {
						logger.messageSender(sender, "offline", ", it could be offline "); 
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else {
				logger.messageSender(sender, "argserror", null); 
				logger.warning("updatePlayerError", null); 
			}
		}
		else {
			logger.messageSender(sender, "help", null); 
			sender.sendMessage(ChatColor.RED + "You can only do these actions to a player with this command: add, remove, reset "); 
		}
		plugin.saveFiles(); 
	}
	public void setObjects (CommandSender sender, String[] args) {
		String configPlace; 
		if (args[1].equalsIgnoreCase("player")) {
			@SuppressWarnings("deprecation")
			Player player = Bukkit.getPlayer(args[2]); 
			if (player != null) {
				UUID rpId = player.getUniqueId(); 
				String spId = rpId.toString(); 
				configPlace = "players." + spId; 
				if (plugin.yd.configuration.contains(configPlace) == true) {
					if ((args.length == 6) && ((args[3].equalsIgnoreCase("aims")) || (args[3].equalsIgnoreCase("password")) || (args[3].equalsIgnoreCase("points")) || (args[3].equalsIgnoreCase("sign")))) {
						if (args[3].equalsIgnoreCase("aims")) {
							if (plugin.yc.configuration.contains("aims." + args[4])) {
								configPlace += "." + "aims"; 
								if ((sender.hasPermission("pos.set.player.aim")) || (sender.hasPermission("pos.set.player.aim.none") && plugin.yc.configuration.getString("aims." + args[4] + ".type").equalsIgnoreCase("none"))) {
									if (plugin.yd.configuration.contains(configPlace + "." + args[4]) == true) {
										configPlace += "." + args[4]; 
										if (args[5].equalsIgnoreCase("true")) {
											plugin.yd.configuration.set(configPlace, true); 
											sender.sendMessage(args[4] + " is now " + args[5] + " "); 
											Bukkit.getPlayer(rpId).sendMessage(ChatColor.AQUA + logger.getName(true) + "You have completed aim " + args[4]); 
										}
										else if (args[5].equalsIgnoreCase("false")) {
											plugin.yd.configuration.set(configPlace, false); 
											sender.sendMessage(args[4] + " is now " + args[5] + " "); 
											Bukkit.getPlayer(rpId).sendMessage(ChatColor.RED + logger.getName(true) + "You have no longer completed aim " + args[4]); 
										}
										else {
											sender.sendMessage(ChatColor.RED + "A player's aim completion status must be either 'true' or 'false' "); 
										}
									}
									else {
										sender.sendMessage(ChatColor.RED + "The specified aim is not an aim of the target that the player currently is on "); 
									}
								}
								else {
									logger.messageSender(sender, "nopermission", null); 
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "The aim specified does not exist in the config.yml file "); 
							}
						}
						else if (args[3].equalsIgnoreCase("password")) {
							configPlace += ".data." + "password"; 
							if (sender.hasPermission("pos.password.set.others") || sender.hasPermission("pos.set.player.password")) {
								if (plugin.yd.configuration.contains(configPlace + "." + args[4])) {
									configPlace += "." + args[4]; 
									plugin.yd.configuration.set(configPlace, args[5]); 
									sender.sendMessage(args[2] + "'s password is now " + args[5] + " "); 
									player.sendMessage("your password is now " + args[5] + " "); 
								}
								else {
									sender.sendMessage(ChatColor.RED + "The given aim is not a 'password' aim type "); 
								}
							}
							else {
								logger.messageSender(sender, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("points")) {
							configPlace += ".data." + "points"; 
							if (sender.hasPermission("pos.set.player.points")) {
								Boolean success = true; 
								int value = 0; 
								try {
									value = Integer.parseInt(args[5]); 
								}
								catch (NumberFormatException e) {
									success = false; 
									sender.sendMessage(ChatColor.RED + "The aim goal must be an integer "); 
								}
								if (success == true) {
									if (args[4].equalsIgnoreCase("set")) {
										plugin.yd.configuration.set(configPlace, value); 
										sender.sendMessage(args[2] + " now has " + args[5] + " points "); 
									}
									else if (args[4].equalsIgnoreCase("add")) {
										int currentPoints = plugin.yd.configuration.getInt(configPlace); 
										currentPoints += value; 
										plugin.yd.configuration.set(configPlace, currentPoints); 
										sender.sendMessage(args[2] + " now has " + Integer.toString(currentPoints) + " points "); 
									}
									else if (args[4].equalsIgnoreCase("remove")) {
										int currentPoints = plugin.yd.configuration.getInt(configPlace); 
										currentPoints -= value; 
										plugin.yd.configuration.set(configPlace, currentPoints); 
										sender.sendMessage(args[2] + " now has " + Integer.toString(currentPoints) + " points "); 
									}
									else {
										sender.sendMessage(ChatColor.RED + "You can only 'set', 'add' and 'remove' a player's points "); 
									}
								}
							}
							else {
								logger.messageSender(sender, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("sign")) {
							if (plugin.ys.configuration.contains("signs." + args[4] + ".usage")) {
								configPlace += ".data.signs." + args[4]; 
								Boolean success = true; 
								int value = 0; 
								try {
									value = Integer.parseInt(args[5]); 
								}
								catch (NumberFormatException e) {
									success = false; 
									sender.sendMessage(ChatColor.RED + "The sign usage count must be an integer "); 
								}
								if (success == true) {
									plugin.yd.configuration.set(configPlace, value); 
									sender.sendMessage(args[2] + " has now used the sign with the id " + args[4] + " " + args[5] + " times "); 
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "The sign-id specified does not exist in the sign.yml file "); 
							}
						}
						else {
							logger.messageSender(sender, "argserror", null); 
							logger.warning("updateplayererror", null); 
						}
					}
					else if (args.length == 5 && !(args[3].equalsIgnoreCase("aims")) && !(args[3].equalsIgnoreCase("password")) && !args[3].equalsIgnoreCase("points") && !(args[3].equalsIgnoreCase("lastUsername"))) {
						if (args[3].equalsIgnoreCase("finished")) {
							configPlace += "." + "finished"; 
							if (sender.hasPermission("pos.set.player.finished")) {
								if (args[4].equalsIgnoreCase("true")) {
									plugin.yd.configuration.set(configPlace, true); 
									player.sendMessage("Your promotion tree completion status is now " + args[4] + " "); 
								}
								else if (args[4].equalsIgnoreCase("false")) {
									plugin.yd.configuration.set(configPlace, false); 
									player.sendMessage("Your promotion tree completion status is now " + args[4] + " "); 
								}
								else {
									sender.sendMessage(ChatColor.RED + "A player's promotion tree completion status must be either 'true' or 'false' ");  
								}
							}
							else {
								logger.messageSender(sender, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("target")) {
							configPlace += "." + "target"; 
							if (sender.hasPermission("pos.set.player.target")) {
								if (plugin.yc.configuration.contains("targets." + args[4]) == true) {
									plugin.yd.configuration.set(configPlace, args[4]); 
									plugin.saveFiles(); 
									YamlFiles.updatePlayerTargets(spId, plugin.yc, plugin.yd, plugin.ys); 
									player.sendMessage("Your target is now " + args[4] + " "); 
								}
								else {
									sender.sendMessage(ChatColor.RED + "There is no target with the name " + args[4] + " "); 
								}
							}
							else {
								logger.messageSender(sender, "nopermission", null); 
							}
						}
						else if (args[3].equalsIgnoreCase("kills")) {
							configPlace += ".data." + "kills"; 
							if (sender.hasPermission("pos.set.player.kills")) {
								try {
									int value = Integer.parseInt(args[4]); 
									plugin.yd.configuration.set(configPlace, value); 
									sender.sendMessage(args[2] + " now has " + args[4] + " kills "); 
								}
								catch (NumberFormatException e) {
									sender.sendMessage(ChatColor.RED + "The aim goal must be an integer "); 
								}
								
							}
							else {
								logger.messageSender(sender, "nopermission", null); 
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "For the 'player' setting type, only the settings 'finished', 'aims', 'lastUsername', 'kills', 'password', 'points' and 'target' may be altered "); 
						}
					}
					else if (args.length == 4 && args[3].equalsIgnoreCase("lastUsername")) {
						configPlace += "." +"lastUsername"; 
						if (sender.hasPermission("pos.set.player.player")) {
							plugin.yd.configuration.set(configPlace, player.getName()); 
							sender.sendMessage(args[4] + "'s last known username is now " + player.getName() + " "); 
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else {
						logger.messageSender(sender, "help", null); 
						sender.sendMessage(ChatColor.RED + "A sub-setting is only required by the 'aim' setting type "); 
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "This player does not yet have any data stored about it "); 
				}
			}
			else {
				logger.messageSender(sender, "offline", ", it could be offline "); 
			}
		}
		else if (args[1].equalsIgnoreCase("target")) {
			configPlace = "targets." + args[2]; 
			if (plugin.yc.configuration.contains(configPlace)) {
				if (args.length == 6 && (args[3].equalsIgnoreCase("aims") || (args[3].equalsIgnoreCase("leadsTo") && (args[4].equalsIgnoreCase("add") || args[4].equalsIgnoreCase("remove"))))) {
					if (args[3].equalsIgnoreCase("aims")) {
						configPlace += "." + "aims"; 
						if (sender.hasPermission("pos.set.target.aims")) {
							if (args[4].equalsIgnoreCase("add")) {
								if (plugin.yc.configuration.contains("aims." + args[5])) {
									List<String> aims = plugin.yc.configuration.getStringList(configPlace); 
									plugin.yc.configuration.set(configPlace, null); 
									aims.add(args[5]); 
									plugin.yc.configuration.set(configPlace, aims); 
									sender.sendMessage(args[5] + " added to the target's aim list "); 
								}
								else {
									sender.sendMessage(ChatColor.RED + "There is no aim with the name " + args[5] + " "); 
								}
							}
							else if (args[4].equalsIgnoreCase("remove")) {
								List<String> aims = plugin.yc.configuration.getStringList(configPlace); 
								if (aims.size() > 1) {
									plugin.yc.configuration.set(configPlace, null); 
									aims.remove(args[5]); 
									plugin.yc.configuration.set(configPlace, aims); 
									sender.sendMessage(args[5] + " was removed from the target's aim list "); 
								}
								else {
									sender.sendMessage(ChatColor.RED + "There must be at least one aim for each taregt "); 
								}
							}
							else {
								logger.messageSender(sender, "help", null); 
								sender.sendMessage(ChatColor.RED + "You can only 'add' or 'remove' aims to or from targets "); 
							}
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else if (args[3].equalsIgnoreCase("leadsTo")) {
						configPlace += "." + "leadsTo"; 
						if (sender.hasPermission("pos.set.target.leadsto")) {
							if (args[4].equalsIgnoreCase("add")) {
								if (plugin.yc.configuration.contains("targets." + args[5])) {
									List<String> targets = plugin.yc.configuration.getStringList(configPlace); 
									plugin.yc.configuration.set(configPlace, null); 
									targets.add(args[5]); 
									targets.remove("none"); 
									plugin.yc.configuration.set(configPlace, targets); 
									sender.sendMessage(args[5] + " was added to the target's leads to list "); 
								}
								else {
									sender.sendMessage(ChatColor.RED + "There is no target with the name " + args[5] + " "); 
								}
							}
							else if (args[4].equalsIgnoreCase("remove")) {
								List<String> targets = plugin.yc.configuration.getStringList(configPlace); 
								int amount = targets.size(); 
								plugin.yc.configuration.set(configPlace, null); 
								if ((amount < 2) && (!(targets.contains("none")))) {
									targets.add("none"); 
								}
								targets.remove(args[5]); 
								plugin.yc.configuration.set(configPlace, targets); 
								sender.sendMessage(args[5] + " was removed from the target's leads to list "); 
							}
							else {
								logger.messageSender(sender, "help", null); 
								sender.sendMessage(ChatColor.RED + "You can only add, remove or nullify a target's leads to list "); 
							}
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else {
						logger.messageSender(sender, "argserror", null); 
						logger.warning("custom", "There was an error with the /posset set ... command"); 
					}
				}
				else if (args.length == 5 && (args[3].equalsIgnoreCase("defaultNextTarget") || (args[3].equalsIgnoreCase("leadsTo") && args[4].equalsIgnoreCase("nullify"))))  {
					if (args[3].equalsIgnoreCase("defaultNextTarget")) {
						configPlace += "." + "defaultNextTarget"; 
						if (sender.hasPermission("pos.set.target.defaultnexttarget")) {
							if (plugin.yc.configuration.contains("targets." + args[4])) {
								plugin.yc.configuration.set(configPlace, args[4]); 
								sender.sendMessage("The default next target of " + args[2] + " has been set to " + args[4] + " "); 
							}
							else {
								sender.sendMessage(ChatColor.RED + "There is no target with the name " + args[4] + " "); 
							}
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else if (args[3].equalsIgnoreCase("leadsTo")) {
						configPlace += "." + "leadsTo"; 
						if (sender.hasPermission("pos.set.target.leadsto")) {
							List<String> none = Arrays.asList("none"); 
							plugin.yc.configuration.set(configPlace, none); 
							sender.sendMessage("Target " + args[2] + " had its leads to list nullified "); 
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else {
						logger.messageSender(sender, "argserror", null); 
						logger.warning("custom", "There was an error with the /posset set ... command"); 
					}
				}
				else {
					logger.messageSender(sender, "help", null); 
					sender.sendMessage(ChatColor.RED + "Only setting types 'aims' and 'leadsTo' (not 'defaultNextTarget') for the 'target' setting type require sub-settings "); 
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "There is no target with the name " + args[2] + " "); 
			}
		}
		else if (args[1].equalsIgnoreCase("aim")) {
			if (args.length == 5) {
				configPlace = "aims." + args[2]; 
				if (plugin.yc.configuration.contains(configPlace) == true) {
					if (args[3].equalsIgnoreCase("type")) {
						if (sender.hasPermission("pos.set.aim.type")) {
							if (args[4].equalsIgnoreCase("command")) {
								sender.sendMessage(ChatColor.RED + "You cannot change 'command' aim types with this command "); 
							}
							else if (args[4].equalsIgnoreCase("xp") || args[4].equalsIgnoreCase("xpl") || args[4].equalsIgnoreCase("item") || args[4].equalsIgnoreCase("password") || args[4].equalsIgnoreCase("points") || args[4].equalsIgnoreCase("kills") || args[4].equalsIgnoreCase("group") || args[4].equalsIgnoreCase("pgroup") || args[4].equalsIgnoreCase("permission") || args[4].equalsIgnoreCase("sign") || args[4].equalsIgnoreCase("command") || args[4].equalsIgnoreCase("none") || args[4].equalsIgnoreCase("economy")) {
								configPlace += "." + "type"; 
								plugin.yc.configuration.set(configPlace, args[4]); 
								if ((plugin.yc.configuration.getString("aims." + args[2] + ".type").equalsIgnoreCase("sign")) || (plugin.yc.configuration.getString("aims." + args[2] + ".type").equalsIgnoreCase("none"))) {
									plugin.yc.configuration.set("aims." + args[2] + ".achieve", "none"); 
									sender.sendMessage(ChatColor.RED + "Aim goal set to none due to restrictions imposed by the aim type "); 
								}
								sender.sendMessage("Aim type set to " + args[4] + " "); 
							}
							else {
								sender.sendMessage(ChatColor.RED + "The specified aim type is not a valid aim type"); 
							}
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else if (args[3].equalsIgnoreCase("achieve")) {
						if (sender.hasPermission("pos.set.aim.achieve")) {
							if (plugin.yc.configuration.getString("aims." + args[2] + ".type").equalsIgnoreCase("sign")) {
								sender.sendMessage(ChatColor.RED + "'Sign' aim types must have an aim goal of 'none' "); 
							}
							else if (plugin.yc.configuration.getString("aims." + args[2] + ".type").equalsIgnoreCase("command")) {
								sender.sendMessage(ChatColor.RED + "You cannot change 'command' aim types with this command "); 
							}
							else {
								configPlace += "." + "achieve"; 
								try {
									int aimGoal = Integer.parseInt(args[4], 10); 
									plugin.yc.configuration.set(configPlace, aimGoal); 
									logger.info("custom", Integer.toString(aimGoal)); 
									sender.sendMessage("The goal of aim " + args[3] + " has been set to " + args[4]  + " "); 
								}
								catch (NumberFormatException e1) {
									try {
										double aimGoal = Double.parseDouble(args[4]); 
										plugin.yc.configuration.set(configPlace, aimGoal); 
										sender.sendMessage("The goal of aim " + args[3] + " has been set to " + args[4]  + " "); 
									}
									catch (NumberFormatException e2) {
										plugin.yc.configuration.set(configPlace, args[4]); 
										sender.sendMessage("The goal of aim " + args[3] + " has been set to " + args[4]  + " "); 
									}
								}
							}
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else {
						logger.messageSender(sender, "help", null); 
						sender.sendMessage(ChatColor.RED + "For the 'aim' setting type, only the settings 'type' and 'achieve' may be altered "); 
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "There is no aim with the name " + args[2] + " "); 
				}
			}
			else {
				logger.messageSender(sender, "help", null); 
				sender.sendMessage(ChatColor.RED + "'aim' setting types never need sub-settings "); 
			}
		}
		else if (args[1].equalsIgnoreCase("setting")) {
			if (args.length == 4) {
				if (args[2].equalsIgnoreCase("detectKills")) {
					if (sender.hasPermission("pos.set.setting.detectkills")) {
						configPlace = "detectKills"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("watchCommands")) {
					if (sender.hasPermission("pos.set.setting.watchCommands")) {
						configPlace = "watchCommands"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("allowSigns")) {
					if (sender.hasPermission("pos.set.setting.allowsigns")) {
						configPlace = "allowSigns"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("defaultTarget")) {
					if (sender.hasPermission("pos.set.setting.defaulttarget")) {
						configPlace = "defaultTarget"; 
						if (plugin.yc.configuration.contains("targets." + args[3])) {
							plugin.yc.configuration.set(configPlace, args[3]); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "There is no target with the name " + args[3] + " "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("lowestRankThatCanManuallyApproveAims")) {
					if (sender.hasPermission("pos.set.setting.lowestrankthatcanmanuallyapproveaims")) {
						configPlace = "lowestRankThatCanManuallyApproveAims"; 
						plugin.yc.configuration.set(configPlace, args[3]); 
						sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("startInPromotionTree")) {
					if (sender.hasPermission("pos.set.setting.startinpromotiontree")) {
						configPlace = "startInPromotionTree"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("resetPointsAfterEachPromotion")) {
					if (sender.hasPermission("pos.set.setting.resetpointsaftereachpromotion")) {
						configPlace = "resetPointsAfterEachPromotion"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("updateUsernames")) {
					if (sender.hasPermission("pos.set.setting.updateusernames")) {
						configPlace = "updateUsernames"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("defaultPoints")) {
					if (sender.hasPermission("pos.set.setting.defaultpoints")) {
						configPlace = "defaultPoints"; 
						String rawNumber = args[3]; 
						int pointsNumber = 0;
						Boolean error = false; 
						try {
							pointsNumber = Integer.parseInt(rawNumber); 
						}
						catch (NumberFormatException e) {
							error = true; 
							logger.messageSender(sender, "numbererror", rawNumber); 
							e.printStackTrace(); 
						}
						if (error == true) {
							plugin.yc.configuration.set(configPlace, pointsNumber); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null);
					}
				}
				else if (args[2].equalsIgnoreCase("alwaysSaveFiles")) {
					if (sender.hasPermission("pos.set.setting.alwayssavefiles")) {
						configPlace = "alwaysSaveFiles"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							YamlFiles.alwaysSaveFiles = true; 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							YamlFiles.alwaysSaveFiles = false; 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else if (args[2].equalsIgnoreCase("remindOnJoin")) {
					if (sender.hasPermission("pos.set.setting.remindonjoin")) {
						configPlace = "remindOnJoin"; 
						if (args[3].equalsIgnoreCase("true")) {
							plugin.yc.configuration.set(configPlace, true); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else if (args[3].equalsIgnoreCase("false")) {
							plugin.yc.configuration.set(configPlace, false); 
							sender.sendMessage(args[2] + " has been set to " + args[3] + " "); 
						}
						else {
							sender.sendMessage(ChatColor.RED + "The setting " + args[2] + " must be either 'true' or 'false' "); 
						}
					}
					else {
						logger.messageSender(sender, "nopermission", null); 
					}
				}
				else {
					logger.messageSender(sender, "help", null); 
					sender.sendMessage(ChatColor.RED + "For the 'setting' setting type, only the single level non-list type config fields may be altered "); 
				}
			}
			else {
				logger.messageSender(sender, "help", null); 
				sender.sendMessage(ChatColor.RED + "'setting' setting types never need sub-settings "); 
			}
			plugin.saveFiles(); 
			YamlFiles.reloadTheConfiguration(plugin.yc, plugin.yd, plugin.ys);
		}
		else if (args[1].equalsIgnoreCase("sign")) {
			if (args.length == 5) {
				Set<String> signIds = plugin.ys.configuration.getConfigurationSection("signs").getKeys(false); 
				if (signIds.contains(args[2])) {
					configPlace = "signs." + args[2]; 
					if (args[3].equalsIgnoreCase("usage")) {
						if (sender.hasPermission("pos.set.sign.usage")) {
							configPlace += ".usage"; 
							try {
								int usage = Integer.parseInt(args[4]); 
								plugin.ys.configuration.set(configPlace, usage); 
								sender.sendMessage("The usage limit for signs with the id " + args[2] + " is now " + args[4] + " "); 
							}
							catch (NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "The sign usage limit must be a number "); 
							}
						}
						else {
							logger.messageSender(sender, "nopermission", null); 
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "For signs, only the 'usage' limit can be changed "); 
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "That sign id does not exist "); 
				}
			}
			else {
				logger.messageSender(sender, "help", null); 
				sender.sendMessage(ChatColor.RED + "For signs, only the 'usage' limit can be changed "); 
			}
		}
		else {
			logger.messageSender(sender, "help", null); 
			sender.sendMessage(ChatColor.RED + "You can only change the settings for these object types: aim, target, player and setting"); 
		}
		plugin.saveFiles(); 
	}
	public void checkPlayer(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				if (sender.hasPermission("pos.check")) {
					Player player = (Player) sender; 
					UUID rpId = player.getUniqueId(); 
					getPlayerStatus(sender, rpId, "you"); 
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else {
				logger.messageSender(sender, "wrongarrangement", null); 
			}
		}
		else if (args.length == 3) {
			if (args[1].equalsIgnoreCase("player")) {
				if (sender.hasPermission("pos.check.others")) {
					@SuppressWarnings("deprecation")
					Player player = Bukkit.getPlayer(args[2]); 
					if (player != null) {
						UUID rpId = player.getUniqueId(); 
						getPlayerStatus(sender, rpId, player.getName()); 
					}
					else {
						sender.sendMessage(ChatColor.RED + "That player is not online "); 
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else if (args[1].equalsIgnoreCase("target")) {
				if (sender.hasPermission("pos.check.targets")) {
					if (plugin.yc.configuration.contains("targets." + args[2]) == true) {
						sender.sendMessage("This target takes the aims: " + plugin.yc.configuration.getString("targets." + args[2] + ".aims")); 
						sender.sendMessage("This target runs the commands: " + plugin.yc.configuration.getString("targets." + args[2] + ".commands")); 
						sender.sendMessage("The default next target after this target is: " + plugin.yc.configuration.getString("targets." + args[2] + ".defaultNextTarget")); 
						sender.sendMessage("The targets that one can progress to directly after achieving this target are: " + plugin.yc.configuration.getString("targets." + args[2] + ".leadsTo")); 
					}
					else {
						sender.sendMessage(ChatColor.RED + "There is no target with the name " + args[2]); 
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else if (args[1].equalsIgnoreCase("aim")) {
				if (sender.hasPermission("pos.check.aims")) {
					if (plugin.yc.configuration.contains("aims." + args[2]) == true) {
						String aimType = null; 
						String rawAimType = plugin.yc.configuration.getString("aims." + args[2] + ".type"); 
						Boolean run = true; 
						if (rawAimType.equalsIgnoreCase("xp")) {
							aimType = "an amount of experience "; 
						}
						else if (rawAimType.equalsIgnoreCase("xpl")) {
							aimType = "a number of experience levels "; 
						}
						else if (rawAimType.equalsIgnoreCase("item")) {
							String aimGoal = plugin.yc.configuration.getString("aims." + args[2] + ".achieve"); 
							String[] items = aimGoal.split(";"); 
							if (items.length != 2) {
								logger.warning("custom", args[2] + " does not use a ';' to separate its arguments for its goal "); 
							}
							run = false; 
							sender.sendMessage("This aim requires the player to get " + items[0] + " of the " + items[1] + " material "); 
						}
						else if (rawAimType.equalsIgnoreCase("password")) {
							aimType = "a specific password"; 
							run = false; 
							sender.sendMessage("This aim requires a player to get " + aimType + " "); 
						}
						else if (rawAimType.equalsIgnoreCase("points")) {
							aimType = "a specific number of points"; 
						}
						else if (rawAimType.equalsIgnoreCase("playerpoints")) {
							aimType = "a specific number of points"; 
						}
						else if (rawAimType.equalsIgnoreCase("kills")) {
							aimType = "a certain number of kills"; 
						}
						else if (rawAimType.equalsIgnoreCase("economy")) {
							aimType = "a certain amount of money"; 
						}
						else if (rawAimType.equalsIgnoreCase("group")) {
							aimType = "membership of a certain group"; 
						}
						else if (rawAimType.equalsIgnoreCase("pgroup")) {
							aimType = "membership of a certain group, and for that group to be its primary group"; 
						}
						else if (rawAimType.equalsIgnoreCase("permission")) {
							aimType = "a specific permission, possibly assigned to a certain group ";
							run = false; 
							sender.sendMessage("This aim requires a player to get " + aimType); 
						}
						else if (rawAimType.equalsIgnoreCase("command")) {
							aimType = "a certain command executed"; 
						}
						else if (rawAimType.equalsIgnoreCase("sign")) {
							run = false; 
							sender.sendMessage("This aim requires a player to click on a certain sign"); 
						}
						else if (rawAimType.equalsIgnoreCase("none")) {
							String rank = plugin.yc.configuration.getString("lowestRankThatCanManuallyApproveAims").toLowerCase(); 
							if (rank.startsWith("a") || rank.startsWith("e") || rank.startsWith("o") || rank.startsWith("i") || rank.startsWith("u")) {
								rank = "n " + rank; 
							}
							else {
								rank = " " + rank; 
							}
							sender.sendMessage("This aim must be approved by a" + rank + " of the server "); 
							if (!(plugin.yc.configuration.getString("aims." + args[2] + ".achieve").equalsIgnoreCase("none"))) {
								sender.sendMessage("To get the aim, you must " + plugin.yc.configuration.getString("aims." + args[2] + ".achieve")); 
							}
							run = false; 
						}
						else {
							sender.sendMessage("Aim type unrecognised"); 
							logger.warning("aimType", rawAimType); 
							run = false; 
						}
						if (run == true) {
							sender.sendMessage("This aim requires the player to get " + aimType + " "); 
							sender.sendMessage(plugin.yc.configuration.getString("aims." + args[2] + ".achieve") + ", to be precise "); 
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "There is no aim with the name " + args[2]); 
					}
				}
				else {
					logger.messageSender(sender, "nopermission", null); 
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "The types of objects that you can chck are: player, target and aim"); 
			}
		}
		else {
			logger.messageSender(sender, "argserror", null); 
			logger.warning("updatePlayerError", null); 
		}
	}
	private void getPlayerStatus(CommandSender sender, UUID rpId, String UPN) {
		/*
		 * UPN = The name of the player to use in lower case 
		 * UPNU = The name of the player to use in upper case 
		 * UPNP = The possessive version of the name of the player 
		 * CVTH = The conjugation of the verb to have 
		 * CVTB = The conjugation of the verb to be 
		 */ 
		String spId = rpId.toString(); 
		String UPNU, UPNP, CVTH, CVTB; 
		if (UPN.equalsIgnoreCase("you")) {
			UPNU = "You"; 
			UPNP = "your"; 
			CVTH = "have"; 
			CVTB = "are"; 
		}
		else {
			UPNU = UPN; 
			UPNP = UPN + "'s"; 
			CVTH = "has"; 
			CVTB = "is"; 
		}
		if (plugin.yd.configuration.contains("exempt." + spId + ".exempt")) {
			sender.sendMessage(UPNU + " " + CVTH + " an exemption status of " + plugin.yd.configuration.getString("exempt." + spId + ".exempt")); 
		}
		else if (plugin.yd.configuration.contains("players." + spId + ".finished") == true) {
			Boolean isFinished = plugin.yd.configuration.getBoolean("players." + spId + ".finished"); 
			String target = plugin.yd.configuration.getString("players." + spId + ".target"); 
			if (isFinished == true) {
				sender.sendMessage(UPNU + " " + CVTH + " reached the highest self-promotion possible "); 
				sender.sendMessage("The last target " + UPN + " achieved is: " + target); 
				sender.sendMessage(UPNU + " " + CVTH + " " + plugin.yd.configuration.getString("players." + spId + ".data.points") + " points "); 
				sender.sendMessage(UPNU + " " + CVTH + " " + plugin.yd.configuration.getString("players." + spId + ".data.kills") + " recorded kills "); 
			}
			else {
				sender.sendMessage("The target " + UPN + " " + CVTB + " currently working towards is: " + target); 
				sender.sendMessage("The aims for this target and " + UPNP + " completion status for each of them are: "); 
				for (String i : plugin.yc.configuration.getStringList("targets." + target + ".aims")) {
					sender.sendMessage(i + ": " + plugin.yd.configuration.getString("players." + spId + ".aims." + i)); 
				}
				sender.sendMessage(UPNU + " " + CVTH + " " + plugin.yd.configuration.getString("players." + spId + ".data.points") + " points "); 
				sender.sendMessage(UPNU + " " + CVTH + " " + plugin.yd.configuration.getString("players." + spId + ".data.kills") + " recorded kills "); 
			}
		}
		else if (plugin.yd.configuration.contains("players." + spId + ".finished") == false && plugin.yc.configuration.getBoolean("startInPromotionTree") == false) {
			sender.sendMessage(UPNU + " " + CVTB + " not yet in the promotion tree "); 
		}
		else if (plugin.yd.configuration.contains("players." + spId + ".finished") == false && plugin.yc.configuration.getBoolean("startInPromotionTree") == true) {
			sender.sendMessage(ChatColor.RED + "No data are being stored about " + UPN); 
			logger.warning("noplayer", Bukkit.getPlayer(rpId).getName()); 
		}
		else {
			sender.sendMessage(ChatColor.RED + "There was an error "); 
			logger.warning("updatePlayerError", null); 
		}
	}
}

package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MySignListener implements Listener {
	private static PromoteOneselfMainClass plugin; 
	private static LoggingClass logger; 
	private static UpdateAims ua; 
	
	public MySignListener(PromoteOneselfMainClass instance, LoggingClass log, UpdateAims uainstance) {
		plugin = instance; 
		logger = log; 
		ua = uainstance; 
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL) 
	public void onSignChange(SignChangeEvent event) {
		if (plugin.yc.configuration.getBoolean("allowSigns") == true) {
			if (event.getLine(0).equalsIgnoreCase("[pos]")) {
				if (event.getLine(1).equalsIgnoreCase("update")) {
					if (event.getPlayer().hasPermission("pos.sign.update.create")) {
					}
					else {
						event.setCancelled(true); 
					}
				}
				else if (event.getLine(1).equalsIgnoreCase("points")) {
					if (event.getPlayer().hasPermission("pos.sign.points.create")) {
					}
					else {
						event.setCancelled(true); 
					}
				}
				else if (event.getLine(1).equalsIgnoreCase("target")) {
					if (event.getPlayer().hasPermission("pos.sign.target.create")) {
					}
					else {
						event.setCancelled(true); 
					}
				}
				else if (event.getLine(1).equalsIgnoreCase("sign")) {
					if (event.getPlayer().hasPermission("pos.sign.sign.create")) {
					}
					else {
						event.setCancelled(true); 
					}
				}
				else {
					logger.messageSender(event.getPlayer(), "sign", null); 
					event.setCancelled(true); 
				}
			}
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL) 
	public void onBlockBreak(BlockBreakEvent event) {
		if (plugin.yc.configuration.getBoolean("allowSigns") == true) {
			if (event.getBlock() instanceof Sign) {
				Sign sign = (Sign) event.getBlock(); 
				if (sign.getLine(0).equalsIgnoreCase("[pos]")) {
					if (sign.getLine(1).equalsIgnoreCase("update")) {
						if (event.getPlayer().hasPermission("pos.sign.update.delete")) {
						}
						else {
							event.setCancelled(true); 
						}
					}
					else if (sign.getLine(1).equalsIgnoreCase("points")) {
						if (event.getPlayer().hasPermission("pos.sign.points.delete")) {
						}
						else {
							event.setCancelled(true); 
						}
					}
					else if  (sign.getLine(1).equalsIgnoreCase("target")) {
						if (event.getPlayer().hasPermission("pos.sign.target.delete")) {
						}
						else {
							event.setCancelled(true); 
						}
					}
					else if (sign.getLine(1).equalsIgnoreCase("sign")) {
						if (event.getPlayer().hasPermission("pos.sign.sign.delete")) {
						}
						else {
							event.setCancelled(true); 
						}
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL) 
	public void onPlayerInteract(PlayerInteractEvent event)  {
		if (plugin.yc.configuration.getBoolean("allowSigns") == true) {
			if (event.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) event.getClickedBlock().getState(); 
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (sign.getLine(0).equalsIgnoreCase("[pos]")) {
						Player player = event.getPlayer(); 
						UUID rpId = player.getUniqueId(); 
						String spId = rpId.toString(); 
						boolean proceed = true; 
						Set<String> listedSignIds = Collections.emptySet(); 
						try {
							listedSignIds = plugin.ys.configuration.getConfigurationSection("signs").getKeys(false); 
						}
						catch (NullPointerException e2) {
							proceed = false; 
						}
						if (proceed == true) {
							String signId = sign.getLine(3); 
							if (sign.getLine(1).equalsIgnoreCase("update")) {
								if (event.getPlayer().hasPermission("pos.sign.update.use")) {
									if (signId == null || signId.equalsIgnoreCase("") || player.hasPermission("pos.sign.limitexempt")) {
										ua.updatePlayer(player, new String[] {"update", sign.getLine(2)}); 
									}
									else if (listedSignIds.contains(signId)) {
										try {
											int playerUsage = plugin.yd.configuration.getInt("players." + spId + ".data.signs." + signId); 
											if (playerUsage < plugin.ys.configuration.getInt("signs." + signId + ".usage") || plugin.ys.configuration.getInt("signs." + signId + ".usage") == -1) {
												ua.updatePlayer(player, new String[] {"update", sign.getLine(2)}); 
												playerUsage += 1; 
												plugin.yd.configuration.set("players." + spId + ".data.signs." + signId, playerUsage); 
												plugin.yd.save(); 
											}
											else {
												player.sendMessage(ChatColor.RED + "Could not update target: you have run out of moves "); 
												event.setCancelled(true); 
											}
										}
										catch (NullPointerException e) {
											player.sendMessage(ChatColor.RED + "Could not update target: null pointer exception "); 
											event.setCancelled(true); 
										}
									}
									else {
										event.setCancelled(true); 
									}
								}
								else {
									logger.messageSender(player, "nopermission", null); 
									event.setCancelled(true); 
								}
							}
							else if (sign.getLine(1).equalsIgnoreCase("points")) {
								if (player.hasPermission("pos.sign.points.use")) {
									if (signId == null || signId.equalsIgnoreCase("") || player.hasPermission("pos.sign.limitexempt")) {
										try {
											int signPoints = Integer.parseInt(sign.getLine(2)); 
											int playerPoints = plugin.yd.configuration.getInt("players." + spId + ".data.points"); 
											playerPoints += signPoints; 
											plugin.yd.configuration.set("players." + spId + ".data.points", playerPoints); 
											player.sendMessage("You now have " + sign.getLine(2) + " more points "); 
											plugin.saveFiles(); 
										}
										catch (NumberFormatException e) {
											player.sendMessage(ChatColor.RED + "The number of points to add must be an integer "); 
											logger.warning("custom", "The number of points must be an integer even though it is not on a sign or a player "); 
										}
									}
									else if(listedSignIds.contains(signId))  {
										try {
											int playerUsage = plugin.yd.configuration.getInt("players." + spId + ".data.signs." + signId); 
											if (playerUsage < plugin.ys.configuration.getInt("signs." + signId + ".usage") || plugin.ys.configuration.getInt("signs." + signId + ".usage") == -1) {
												try {
													int signPoints = Integer.parseInt(sign.getLine(2)); 
													int playerPoints = plugin.yd.configuration.getInt("players." + spId + ".data.points"); 
													playerPoints += signPoints; 
													plugin.yd.configuration.set("players." + spId + ".data.points", playerPoints); 
													player.sendMessage("You now have " + sign.getLine(2) + " more points "); 
													playerUsage += 1; 
													plugin.yd.configuration.set("players." + spId + ".data.signs." + signId, playerUsage); 
													plugin.saveFiles(); 
												}
												catch (NumberFormatException e) {
													player.sendMessage(ChatColor.RED + "The number of points to add must be an integer "); 
													logger.warning("custom", "The number of points must be an integer even though it is not on a sign or a player "); 
												}
											}
											else {
												player.sendMessage(ChatColor.RED + "Could not update points: you have run out of moves "); 
												event.setCancelled(true); 
											}
										}
										catch (NullPointerException e) {
											player.sendMessage(ChatColor.RED + "Could not update points: null pointer exception "); 
											event.setCancelled(true); 
										}
									}
									else {
										event.setCancelled(true); 
									}
								}
								else {
									event.setCancelled(true); 
									logger.messageSender(player, "nopermission", null); 
								}
							}
							else if (sign.getLine(1).equalsIgnoreCase("target")) {
								if (player.hasPermission("pos.sign.target.use")) {
									String target = sign.getLine(2); 
									if (plugin.yc.configuration.contains("targets." + target)) {
										if (signId == null || signId.equalsIgnoreCase("") || player.hasPermission("pos.sign.limitexempt")) {
											plugin.yd.configuration.set("players." + spId + ".target", target); 
											plugin.saveFiles(); 
											YamlFiles.updatePlayerTargets(spId, plugin.yc, plugin.yd, plugin.ys); 
											player.sendMessage("Your target is now " + target + " "); 
											plugin.saveFiles(); 
										}
										else if(listedSignIds.contains(signId))  {
											try {
												int playerUsage = plugin.yd.configuration.getInt("players." + spId + ".data.signs." + signId); 
												if (playerUsage < plugin.ys.configuration.getInt("signs." + signId + ".usage") || plugin.ys.configuration.getInt("signs." + signId + ".usage") == -1) {
													plugin.yd.configuration.set("players." + spId + ".target", target); 
													plugin.saveFiles(); 
													YamlFiles.updatePlayerTargets(spId, plugin.yc, plugin.yd, plugin.ys); 
													player.sendMessage("Your target is now " + target + " "); 
													playerUsage += 1; 
													plugin.yd.configuration.set("players." + spId + ".data.signs." + signId, playerUsage); 
													plugin.saveFiles(); 
												}
												else {
													player.sendMessage(ChatColor.RED + "Could not update target: you have run out of moves "); 
													event.setCancelled(true); 
												}
											}
											catch (NullPointerException e) {
												player.sendMessage(ChatColor.RED + "Could not update target: null pointer exception "); 
												event.setCancelled(true); 
											}
										}
										else {
											event.setCancelled(true); 
										}
									}
									else {
										player.sendMessage(ChatColor.RED + "There is no target with the name " + target + " "); 
										logger.warning("custom", "There is a sign using the target " + target + " even though there is no such target "); 
									}
								}
								else {
									event.setCancelled(true); 
									logger.messageSender(player, "nopermission", null); 
								}
							}
							else if (sign.getLine(1).equalsIgnoreCase("sign")) {
								if (player.hasPermission("pos.sign.sign.use")) {
									Boolean changed = false; 
									String aim = sign.getLine(2); 
									Set<String> rawPlayerAims = plugin.yd.configuration.getConfigurationSection("players." + spId + ".aims").getKeys(false); 
									List<String> playerAims = new ArrayList<String>(rawPlayerAims); 
									for (String i : playerAims) {
										if (aim.equalsIgnoreCase(i)) {
											changed = true; 
											if (signId == null || signId.equalsIgnoreCase("") || player.hasPermission("pos.sign.limitexempt")) {
												plugin.yd.configuration.set("players." + spId + ".aims." + aim, true); 
												player.sendMessage("your aim " + aim + " is now completed "); 
											}
											else if(listedSignIds.contains(signId))  {
												try {
													int playerUsage = plugin.yd.configuration.getInt("players." + spId + ".data.signs." + signId); 
													if (playerUsage < plugin.ys.configuration.getInt("signs." + signId + ".usage") || plugin.ys.configuration.getInt("signs." + signId + ".usage") == -1) {
														plugin.yd.configuration.set("players." + spId + ".aims." + aim, true); 
														player.sendMessage("your aim " + aim + " is now completed "); 
														playerUsage += 1; 
														plugin.yd.configuration.set("players." + spId + ".data.signs." + signId, playerUsage); 
													}
													else {
														player.sendMessage(ChatColor.RED + "Could not update aim: you have run out of moves "); 
														event.setCancelled(true); 
													}
												}
												catch (NullPointerException e) {
													player.sendMessage(ChatColor.RED + "Could not update aim: null pointer exception "); 
													event.setCancelled(true); 
												}
											}
											else {
												event.setCancelled(true); 
											}
										}
									}
									if (changed == false) {
										player.sendMessage(ChatColor.RED + "The aim " + aim +" is not one that you are working towards "); 
									}
									plugin.saveFiles(); 
									
								}
								else {
									event.setCancelled(true); 
									logger.messageSender(player, "nopermission", null); 
								}
							}
						}
						else {
							logger.warning("missingkey", "signs key from signs.yml"); 
						}
					}
				}
			}
		}
	}
}

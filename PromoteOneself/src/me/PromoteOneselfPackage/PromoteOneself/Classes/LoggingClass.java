package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class LoggingClass {
	private static Logger logger; 
	private static PromoteOneselfMainClass plugin; 
	
	private static PluginDescriptionFile descriptionFile; 
	private static String name; 
	private static String nameFormat; 
	public LoggingClass(PromoteOneselfMainClass instance, String string) {
		plugin = instance; 
		logger = Logger.getLogger(string); 
		descriptionFile = plugin.getDescription(); 
		name = descriptionFile.getName(); 
		nameFormat = "[" + name + "]" + " "; 
	}
	
	public void enable() {
		logger.info(nameFormat + name + " " + descriptionFile.getVersion() + " has been enabled "); 
	}
	public void disable() {
		logger.info(nameFormat + name + " " + descriptionFile.getVersion() + " has been disabled "); 
	}
	public void info(String type, String message) {
		if (type.equalsIgnoreCase("custom")){
			logger.info(nameFormat + message + " "); 
		}
		else {
			unrecognisedLogType(type, "info"); 
		}
	}
	public void warning(String type, String message) {
		if (message == null) {
			message = " "; 
		}
		if (type.equalsIgnoreCase("custom")) {
			logger.warning(nameFormat + message + " "); 
		}
		else if (type.equalsIgnoreCase("updatePlayerError")) {
			logger.warning(nameFormat + "There was an error updating a player's information "); 
		}
		else if (type.equalsIgnoreCase("aimType")) {
			logger.warning(nameFormat + "Aim type '" + message + "' is unrecognised "); 
		}
		else if (type.equalsIgnoreCase("noPlayer")) {
			logger.warning(nameFormat + "There is no information stored about " + message + " "); 
		}
		else if (type.equalsIgnoreCase("reload")) {
			logger.warning(nameFormat + "An error occured whilst reloading the configuration files that prevented them from being reloaded properly " + message + " "); 
		}
		else if (type.equalsIgnoreCase("missingkey")) {
			logger.warning(nameFormat + "The following key is missing from the following config file: " + message); 
		}
		else if (type.equalsIgnoreCase("configparseerror")) {
			logger.warning(nameFormat + "The following config file could not be loaded (it is probably incorrectly formatted): " + message); 
		}
		else if (type.equalsIgnoreCase("configparseerroranonymous")) {
			logger.warning(nameFormat + "A config file could not be loaded (it is probably incorrectly formatted). " + message); 
		}
		else {
			unrecognisedLogType(type, "warning"); 
		}
	}
	public void severe(String type, String message) {
		if (type.equalsIgnoreCase("custom")) {
			logger.severe(nameFormat + message + " "); 
		}
		else {
			unrecognisedLogType(type, "severe"); 
		}
	}
	public void exception(String message, Exception ex) {
		logger.log(Level.SEVERE, nameFormat + message, ex); 
	}
	public void messageSender(CommandSender sender, String type, String message) {
		if (message == null) {
			message = " "; 
		}
		if (type.equalsIgnoreCase("custom")) {
			sender.sendMessage(message); 
		}
		else if (type.equalsIgnoreCase("nopermission")) {
			sender.sendMessage(ChatColor.RED + " You do not have the required permissions to perform this command; contact a server operator if you believe this is an error " + message + " "); 
		}
		else if (type.equalsIgnoreCase("wrongarrangement")) {
			sender.sendMessage(ChatColor.RED + "This command with this arrangement of arguments must be typed in by a player " + message + " "); 
		}
		else if (type.equalsIgnoreCase("argserror")) {
			sender.sendMessage(ChatColor.RED + "There was an error checking the length of 'args' " + message + " "); 
		}
		else if (type.equalsIgnoreCase("exemptplayer")) {
			sender.sendMessage(ChatColor.RED + "This player is exempt "); 
		}
		else if (type.equalsIgnoreCase("noplayer")) {
			sender.sendMessage(ChatColor.RED + "This player is not in the promotion tree "); 
		}
		else if (type.equalsIgnoreCase("sign")) {
			sender.sendMessage(ChatColor.RED + "This is not a valid " + descriptionFile.getName() + " sign configuration "); 
		}
		else if (type.equalsIgnoreCase("help")) {
			sender.sendMessage(ChatColor.RED + "Incorrect Command Layout: for help type in " + ChatColor.AQUA + "/prom help"); 
		}
		else if (type.equalsIgnoreCase("offline")) {
			sender.sendMessage(ChatColor.RED + "The player could not be obtained" + message + " "); 
		}
		else if (type.equalsIgnoreCase("numbererror")) {
			sender.sendMessage(ChatColor.RED + "The value you gave (" + message + ") was not an integer "); 
		}
		else if (type.equalsIgnoreCase("aimupdate")) {
			sender.sendMessage(ChatColor.AQUA + nameFormat + "Player aim completion status update complete" + message + " "); 
		}
		else if (type.equalsIgnoreCase("offlineplayer")) {
			sender.sendMessage(ChatColor.RED + "The player could not be obtained; it is probably offline "); 
		}
		else {
			unrecognisedLogType(type, "a command sender message"); 
		}
	}
	public void broadcastMessageServer(CommandSender sender, String message) {
		sender.sendMessage(message); 
		info("custom", message); 
	}
	public void broadcastMessageBukkit(String message) {
		Bukkit.broadcastMessage(nameFormat + message); 
	}
	public String getName() {
		return descriptionFile.getName(); 
	}
	public String getName(Boolean fullFormat) {
		if (fullFormat == false) {
			return getName(); 
		}
		else {
			return nameFormat; 
		}
	}
	private void unrecognisedLogType(String type, String severity) {
		logger.warning(nameFormat + type + " is unrecognised for " + severity + " "); 
	}
}

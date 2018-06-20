package me.PromoteOneselfPackage.PromoteOneself.Classes;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandHelp {
	@SuppressWarnings("unused")
	private static PromoteOneselfMainClass plugin; 
	private static LoggingClass logger; 
	protected static final String encasing = ChatColor.WHITE + "-----"; 
	public CommandHelp(PromoteOneselfMainClass instance, LoggingClass log) {
		plugin = instance; 
		logger = log; 
	}
	
	public void helpPages(CommandSender sender) {
		helpPages(sender, 1); 
	}
	public void helpPages(CommandSender sender, int page) {
		if (page == 1) {
			sender.sendMessage(encasing + ChatColor.DARK_BLUE + logger.getName() + " help" + ChatColor.AQUA + " | " + "page 1/2" + encasing); 
			sender.sendMessage(ChatColor.AQUA + "/prom help [page]" + ChatColor.WHITE + " - " + ChatColor.AQUA + "displays this top level help command "); 
			sender.sendMessage(ChatColor.AQUA + "/prom help <command name> <first argument> [page]" + ChatColor.WHITE + " - " + ChatColor.AQUA + "for help with an individual command of the form <command name> <first argument> <other arguments> "); 
			sender.sendMessage(ChatColor.AQUA + "/prom version" + ChatColor.WHITE + " - Get the version of the plugin "); 
			sender.sendMessage(ChatColor.AQUA + "/prom update <arguments>" + ChatColor.WHITE + " - Update a player's targets and aims "); 
			sender.sendMessage(ChatColor.AQUA + "/prom check [arguments]" + ChatColor.WHITE + " - Get information about a player, aim or target "); 
			sender.sendMessage(ChatColor.AQUA + "/prom list <argument>" + ChatColor.WHITE + " - Get all the names of the specified object type "); 
			sender.sendMessage(ChatColor.AQUA + "/prom password <arguments>" + ChatColor.WHITE + " - Get and set a player's passwords for password type aims "); 
		}
		else {
			sender.sendMessage(encasing + ChatColor.DARK_BLUE + logger.getName() + " help" + ChatColor.AQUA + " | " + "page 2/2" + encasing); 
			sender.sendMessage(ChatColor.AQUA + "/posset exempt <arguments>" + ChatColor.WHITE + " - Make a player exempt from the promotion tree "); 
			sender.sendMessage(ChatColor.AQUA + "/posset save" + ChatColor.WHITE + " - Save the plugin's configuration files "); 
			sender.sendMessage(ChatColor.AQUA + "/posset reload [argument]" + ChatColor.WHITE + " - Reload the plugin's configuration files "); 
			sender.sendMessage(ChatColor.AQUA + "/posset player <arguments>" + ChatColor.WHITE + " - Reset, add or remove a player in, to or from the promotion tree "); 
			sender.sendMessage(ChatColor.AQUA + "/posset set <arguments>" + ChatColor.WHITE + " - Set various values for players, aim, targets and settings "); 
		}
	}
	public void helpCommand(CommandSender sender, String command, String firstArgument) {
		helpCommand(sender, command, firstArgument, 1); 
	}
	public void helpCommand(CommandSender sender, String command, String firstArgument, int page) {
		if (command.equalsIgnoreCase("prom") && firstArgument.equalsIgnoreCase("update")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/prom update [target [player [aim]]]"); 
			sender.sendMessage("Specifying a target specifies the next target to progress to "); 
			sender.sendMessage("Specifying a username specifies the player who needs its information to be updated "); 
			sender.sendMessage("Specifying an aim makes the command check only that one aim "); 
		}
		else if (command.equalsIgnoreCase("prom") && firstArgument.equalsIgnoreCase("check")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/prom check [player|target|aim|sign|config] <name> "); 
			sender.sendMessage(ChatColor.AQUA + "/prom check <player>");
			sender.sendMessage("Using the command with one argument gives the player who typed it in its own information "); 
			sender.sendMessage("One of player, target, aim, sign and config can be specified and then a name can be given to get the information for the given object "); 
			sender.sendMessage("Using the command with two arguments assumes that a player is specified "); 
		}
		else if (command.equalsIgnoreCase("prom") && firstArgument.equalsIgnoreCase("list")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/prom list <targets|aims|players|exempt|signs> "); 
			sender.sendMessage("This command lists the name/id of each target, aim, player, exempt player or sign "); 
		}
		else if (command.equalsIgnoreCase("prom") && firstArgument.equalsIgnoreCase("password")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/prom password get [player] <aim> "); 
			sender.sendMessage(ChatColor.AQUA + "/prom password set [player] <password> <aim> ");
			sender.sendMessage("With this comand you can get and set a player's password "); 
		}
		else if (command.equalsIgnoreCase("prom") && firstArgument.equalsIgnoreCase("help")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/prom help ");
			sender.sendMessage(ChatColor.AQUA + "/prom help <prom|posset|set> <argument> [page]");
			sender.sendMessage("This command with no arguments displays the top level help information ");
			sender.sendMessage("This command with two arguments gives more detailed help about the specific command ");
		}
		else if (command.equalsIgnoreCase("prom") && firstArgument.equalsIgnoreCase("version")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/prom version"); 
			sender.sendMessage("This command displays the running version of the " + logger.getName() + " plugin ");
		}
		else if (command.equalsIgnoreCase("posset") && firstArgument.equalsIgnoreCase("exempt")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/posset exempt <player> <true|temp|add|join> "); 
			sender.sendMessage("With this command you can make a player exempt from the promotion tree "); 
			sender.sendMessage("True makes the player exempt; temp keeps the player's data whilst making it exempt; "); 
			sender.sendMessage("Add makes the player loose exemption at the next login; join makes it loose exemption immediately "); 
		}
		else if (command.equalsIgnoreCase("posset") && firstArgument.equalsIgnoreCase("save")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/posset save "); 
			sender.sendMessage("Saves the plugin's configuration files if changes have been made to them "); 
		}
		else if (command.equalsIgnoreCase("posset") && firstArgument.equalsIgnoreCase("reload")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/posset reload [check|nocheck] "); 
			sender.sendMessage("Specifying nothing or specifying check makes the plugin chack a player's aims against its target's aims and make changes as necessary "); 
			sender.sendMessage("Specifying nocheck just reloads the configuration files "); 
			sender.sendMessage("Specifiying nocheck could cause errors "); 
		}
		else if (command.equalsIgnoreCase("posset") && firstArgument.equalsIgnoreCase("player")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/posset player <add|remove|delete> [name] "); 
			sender.sendMessage("This command lets you remove or add a player from or to the promotion tree and reset a player in the promotion tree "); 
			sender.sendMessage("A player could also do these things to itself "); 
		}
		else if (command.equalsIgnoreCase("posset") && firstArgument.equalsIgnoreCase("set")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/posset set <player|target|aim|setting|sign> <name> [setting] [sub-setting] <value> "); 
			sender.sendMessage("Set various properties for the plugin "); 
			sender.sendMessage(ChatColor.AQUA + "/prom help set <player|target|aim|setting|sign>" + ChatColor.WHITE + " - " + ChatColor.AQUA + "gives you more help with the /posset set command "); 
		}
		else if (command.equalsIgnoreCase("set") && firstArgument.equalsIgnoreCase("player"))  {
			if (page == 1) {
				helpCommandHeading(sender, command, firstArgument, 1, 2); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> aims <aim-name> <true|false>" + ChatColor.WHITE + " - set a player's aim completion status "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> password <aim-name> <password>" + ChatColor.WHITE + " - set a password for a player's aim "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> points <set|add|remove> <amount>" + ChatColor.WHITE + " - change the amount of points that a player has "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> sign <sign-id> <player-usage>" + ChatColor.WHITE + " - change the recorded sign usage of a player "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> finished <true|false>" + ChatColor.WHITE + " - set whether a player has finished the promotion tree or not "); 
			}
			else {
				helpCommandHeading(sender, command, firstArgument, 2, 2); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> target <target-name>" + ChatColor.WHITE + " - set a player's target and update its aims automatically "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> kills <amount>" + ChatColor.WHITE + " - set how many recorded kills a player has "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set player <player-username> lastusername" + ChatColor.WHITE + " - make the plugin automatically update a player's username in the configuration file "); 
			}
		}
		else if (command.equalsIgnoreCase("set") && firstArgument.equalsIgnoreCase("target")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/posset set target <target-name> aims <add|remove> <aim-name>" + ChatColor.WHITE + " - set the aims for a target "); 
			sender.sendMessage(ChatColor.AQUA + "/posset set target <target-name> leadsTo <nullify|add <target-name>|remove <target-name>>" + ChatColor.WHITE + " - set the targets that lead on from the specified target "); 
			sender.sendMessage(ChatColor.AQUA + "/posset set target <target-name> defaultNextTarget <target-name>" + ChatColor.WHITE + " - set the default next target for the specified target "); 
		}
		else if (command.equalsIgnoreCase("set")  && firstArgument.equalsIgnoreCase("aim")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.RED + "Aims of type 'command' cannot be altered with this command "); 
			sender.sendMessage(ChatColor.AQUA + "/posset set aim <aim-name> type <aim-type>" + ChatColor.WHITE + " - set the type of an aim "); 
			sender.sendMessage(ChatColor.AQUA + "/posset set aim <aim-name> achieve <aim-goal>" + ChatColor.WHITE + " - set the goal of an aim "); 
		}
		else if (command.equalsIgnoreCase("set") && firstArgument.equalsIgnoreCase("setting")) {
			if (page == 1) {
				helpCommandHeading(sender, command, firstArgument, 1, 2); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting detectKills <true|false>" + ChatColor.WHITE + " - set whether the plugin should listen for player deaths or not "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting watchCommands <true|false>" + ChatColor.WHITE + " - set whether the plugin should detect players executing commands which might be aim goals or not "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting allowSigns <true|false>" + ChatColor.WHITE + " - set whether the plugin should use signs or not "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting defaultTarget <targetName>" + ChatColor.WHITE + " - set the default first target when a player first joins the promotion tree "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting lowestRankThatCanManuallyApproveAims <rank>" + ChatColor.WHITE + " - set the lowest rank that can manually approve aims (informational only: not a critical value) "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting startInPromotionTree <true|false>" + ChatColor.WHITE + " - set whether a player hsould automatically be added to the promotion tree on its first join or not "); 
			}
			else {
				helpCommandHeading(sender, command, firstArgument, 2, 2); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting resetPointsAfterEachPromotion <true|false>" + ChatColor.WHITE + " - set whether a player's points should return to 0 after each promotion it gets or not "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting updateUsernames <true|false>" + ChatColor.WHITE + " - set whether the plugin should automatically update a player's recorded username after each login or not "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting defaultPoints <integer>" + ChatColor.WHITE + " - set the default amount of points each player gets "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting alwaysSaveFiles <true|false>" + ChatColor.WHITE + " - set if the plugin should still save config files it deems to have corrupted when loading "); 
				sender.sendMessage(ChatColor.AQUA + "/posset set setting remindOnJoin <true|false>" + ChatColor.WHITE + " - set if the plugin should send a reminder message to players when they join ");
			}
		}
		else if (command.equalsIgnoreCase("set") && firstArgument.equalsIgnoreCase("sign")) {
			helpCommandHeading(sender, command, firstArgument); 
			sender.sendMessage(ChatColor.AQUA + "/posset set sign <sign-id> usage <number>" + ChatColor.WHITE + " - set the maximum number of times a player can use a sign with the given sign id "); 
		}
		else {
			sender.sendMessage(ChatColor.RED + "That is not a recognised command; for help enter the command " + ChatColor.AQUA + "/prom help"); 
		}
	}
	private void helpCommandHeading(CommandSender sender, String command, String firstArgument) {
		helpCommandHeading(sender, command, firstArgument, 1, 1); 
	}
	private void helpCommandHeading(CommandSender sender, String command, String firstArgument, int page, int maxPages) {
		if (command.equalsIgnoreCase("set")) {
			command = "posset " + command; 
		}
		sender.sendMessage(encasing + ChatColor.DARK_BLUE + logger.getName() + " help" +  ChatColor.AQUA + " | " + "/" + command + " " + firstArgument + " | page " + Integer.toString(page) + "/" + Integer.toString(maxPages) + encasing); 
	}
}

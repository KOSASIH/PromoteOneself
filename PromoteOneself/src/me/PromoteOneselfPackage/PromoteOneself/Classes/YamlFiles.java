package me.PromoteOneselfPackage.PromoteOneself.Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
public class YamlFiles {
	private static PromoteOneselfMainClass plugin; 
	private static LoggingClass logger;  
	public static Boolean alwaysSaveFiles = false; 
	
	public YamlConfiguration configuration; 
	public final File theOutFile; 
	private final String theInFile; 
	private Boolean shouldSave; 
	public YamlFiles(PromoteOneselfMainClass instance, LoggingClass log, String outFileName, String inFileName) {
		plugin = instance; 
		logger = log; 
		if (plugin.getDataFolder().exists() == false) {
			plugin.getDataFolder().mkdir(); 
		}
		theOutFile = new File(plugin.getDataFolder(), outFileName); 
		theInFile = inFileName; 
		shouldSave = true; 
		configuration = loadFiles(); 
		checkConfiguration(); 
	}
	
	public static void reloadTheConfiguration(YamlFiles configuration, YamlFiles players, YamlFiles signs) {
		Boolean loadErrorFree = true; 
		configuration.configuration = loadAConfiguration(configuration.theOutFile); 
		players.configuration = loadAConfiguration(players.theOutFile); 
		signs.configuration = loadAConfiguration(signs.theOutFile); 
		loadErrorFree = loadErrorFree && configuration.checkConfiguration(); 
		loadErrorFree = loadErrorFree && players.checkConfiguration(); 
		loadErrorFree = loadErrorFree && signs.checkConfiguration(); 
		Boolean areSigns = true; 
		List<String> signIds = new ArrayList<String>(); 
		try {
			Set<String> rawSignIds = signs.configuration.getConfigurationSection("signs").getKeys(false); 
			signIds.addAll(rawSignIds); 
		}
		catch (NullPointerException e) {
			areSigns = false; 
			logger.warning("custom", "No sign ids were found in the 'signs.yml' file (this is not an error if this is supposed to be true) "); 
		}
		try {
			Set<String> pcf = players.configuration.getConfigurationSection("players").getKeys(false); 
			if (pcf.isEmpty() == false) {
				for (String i : pcf) {
					updatePlayerTargets(i, configuration, players, signs, areSigns, signIds); 
				}
			}
		}
		catch (NullPointerException e) {
			logger.warning("custom", "There was a null pointer exception when parsing the players.yml file; this is normally caused by there being no players in it (this is not an error if that is supposd to be true)");
		}
		catch (Exception e) {
			logger.warning("reload", e.toString()); 
			e.printStackTrace(); 
		}
		MyPlayerListener.updateCommandsList(); 
		alwaysSaveFiles = configuration.configuration.getBoolean("alwaysSaveFiles"); 
		configuration.save(); 
		players.save(); 
		signs.save();  
		if (loadErrorFree == true) {
			logger.broadcastMessageBukkit(plugin.getDescription().getName() + " configuration reloaded, checked and saved ");
		}
		else {
			logger.broadcastMessageBukkit(ChatColor.RED + plugin.getDescription().getName() + " configuration reloaded, but there were errors "); 
			logger.warning("configparseerroranonymous", "");
		}
	}
	protected static void updatePlayerTargets(String i, YamlFiles configuration, YamlFiles players, YamlFiles signs, Boolean areSigns, List<String> Signs) {
		Set<String> rawPlayerAims = players.configuration.getConfigurationSection("players." + i + ".aims").getKeys(false); 
		List<String> playerAims = new ArrayList<String>(rawPlayerAims); 
		List<String> targetAims = configuration.configuration.getStringList("targets." + players.configuration.getString("players." + i + ".target") + ".aims"); 
		if (targetAims != playerAims) {
			for (String j : playerAims) {
				if (targetAims.contains(j) == true) {
					
				}
				else {
					players.configuration.set("players." + i + ".aims." + j, null); 
				}
			}
			Set<String> newRawPlayerAims = players.configuration.getConfigurationSection("players." + i + ".aims").getKeys(false); 
			List<String> newPlayerAims = new ArrayList<String>(newRawPlayerAims); 
			for (String k : targetAims) {
				if (newPlayerAims.contains(k) == false) {
					players.configuration.set("players." + i + ".aims." + k, false); 
				}
			}
		}
		Set<String> rawPasswordAndCommandPlayerAims = players.configuration.getConfigurationSection("players." + i + ".aims").getKeys(false); 
		List<String> playerPasswordAndCommandAims = new ArrayList<String>(rawPasswordAndCommandPlayerAims); 
		try {
			List<String> neededPasswordAims = new ArrayList<String>(); 
			for (String l : playerPasswordAndCommandAims) {
				if (configuration.configuration.getString("aims." + l + ".type").equalsIgnoreCase("password")) {
					if (players.configuration.contains("players." + i + ".data.password." + l)) {
						neededPasswordAims.add(l); 
					}
					else {
						players.configuration.set("players." + i + ".data.password." + l, "none"); 
						neededPasswordAims.add(l); 
					}
				}
			}
			Set<String> newRawPlayerPasswordAims = players.configuration.getConfigurationSection("players." + i + ".data.password").getKeys(false); 
			List<String> newPlayerPasswordAims = new ArrayList<String>(newRawPlayerPasswordAims); 
			for (String m : newPlayerPasswordAims) {
				if (neededPasswordAims.contains(m)) {
					
				}
				else {
					players.configuration.set("players." + i + ".data.password." + m, null); 
				}
			}
		}
		catch (NullPointerException e) {
			logger.warning("custom", "The config file may have malformed information "); 
		}
		List<String> playerCommandAims = new ArrayList<String>(); 
		for (String n : playerPasswordAndCommandAims) {
			if (configuration.configuration.getString("aims." + n + ".type").equalsIgnoreCase("command")) {
				playerCommandAims.add(n); 
			}
		}
		players.configuration.set("players." + i + ".data.commands", playerCommandAims); 
		if (configuration.configuration.getStringList("targets." + players.configuration.getString("players." + i + ".target") + ".leadsTo").get(0) != "none") {
			players.configuration.set("players." + i + ".finished", false); 
		}
		else {
		}
		List<String> playerSigns = new ArrayList<String>(); 
		Boolean runSigns = areSigns; 
		Boolean runPlayerSigns = true; 
		try {
			Set<String> rawPlayerSigns = players.configuration.getConfigurationSection("players." + i + ".data.signs").getKeys(false); 
			playerSigns.addAll(rawPlayerSigns); 
		}
		catch (NullPointerException e3) {
			runPlayerSigns = false; 
			//logger.warning("custom", "Some player sign information could not be found "); 
		}
		if (runSigns == true) {
			if (runPlayerSigns == true) {
				for (String o : playerSigns) {
					if (Signs.contains(o) == true) {
						logger.info("custom", "loop o true if: " + o); 
					}
					else {
						logger.info("custom", "loop o: " + o); 
						players.configuration.set("players." + i + ".data.signs." + o, null); 
					}
				}
			}
			List<String> newPlayerSigns = new ArrayList<String>(); 
			Boolean runNewPlayerSigns = true; 
			try {
				Set<String> newRawPlayerSigns = players.configuration.getConfigurationSection("players." + i + ".data.signs").getKeys(false); 
				newPlayerSigns.addAll(newRawPlayerSigns); 
			}
			catch (NullPointerException e4) {
				runNewPlayerSigns = false; 
				//logger.warning("custom", "Player sign information could not be updated properly "); 
			}
			if (runNewPlayerSigns == true) {
				for (String p : Signs) {
					if (newPlayerSigns.contains(p) == false) {
						players.configuration.set("players." + i + ".data.signs." + p, 0); 
					}
				}
			}
			else {
				for (String q : Signs) {
					players.configuration.set("players." + i + ".data.signs." + q, 0); 
				}
			}
		}
	}
	public static YamlConfiguration loadAConfiguration(File file) {
		YamlConfiguration currentConfigurationFile = YamlConfiguration.loadConfiguration(file); 
		return currentConfigurationFile; 
	}
	public Boolean checkConfiguration() {
		if (configuration.getKeys(false).size() > 0) {
			save(); 
			logger.info("custom", "Configuration file " + theOutFile.getName() + " loaded "); 
			shouldSave = true; 
		}
		else {
			logger.warning("configparseerror", theOutFile.getName()); 
			shouldSave = false; 
		}
		return shouldSave; 
	}
	public Boolean getSaveable() {
		return shouldSave; 
	}
	private static void copy(InputStream src, OutputStream dst) throws IOException{
		byte[] bytes = new byte[2048]; 
		int transfer; 
		while ((transfer = src.read(bytes)) > 0) {
			dst.write(bytes, 0, transfer); 
		}
		dst.close(); 
		src.close(); 
	}
	private static String getFileHeader(InputStream src) throws IOException {
		String header = null; 
		String line; 
		StringBuilder sb = new StringBuilder(); 
		sb.append("\n"); 
		BufferedReader br = new BufferedReader(new InputStreamReader(src));
		while ((line = br.readLine()) != null){  
			sb.append(line); 
			sb.append("\n"); 
		}
		br.close(); 
		src.close(); 
		header = sb.toString(); 
		return header; 
	}
	
	public YamlConfiguration loadFiles() {
		YamlConfiguration theConfiguration = null; 
		logger.info("custom", "Attempting to load the configuration file " + theOutFile.getName()); 
		if (theOutFile.exists() != true) {
			try {
				copy(plugin.getResource(theInFile), new FileOutputStream(theOutFile)); 
				logger.info("custom", "Configuration file " + theOutFile.getName() + " created "); 
			} catch (FileNotFoundException e) {
				logger.exception("Unable to create a configuration file", e); 
			} catch (IOException e) {
				logger.exception("Unable to create a configuration file", e); 
			} 
			theConfiguration = loadAConfiguration(theOutFile); 
			fileHeaders(theConfiguration); 
		}
		else {
			theConfiguration = loadAConfiguration(theOutFile); 
		}
		return theConfiguration; 
	}
	public void fileHeaders (YamlConfiguration theConfiguration) {
		FileConfigurationOptions options = theConfiguration.options(); 
		String theHeader = null; 
		if (theOutFile.getName().equalsIgnoreCase("players.yml")) {
			try {
				theHeader = getFileHeader(plugin.getResource("playersHeader.txt")); 
			} catch (FileNotFoundException e) {
				logger.exception("Unable to create a file header", e); 
			} catch (IOException e) {
				logger.exception("Unable to create a file header", e); 
			} 
		}
		else if (theOutFile.getName().equalsIgnoreCase("config.yml")) {
			try {
				theHeader = getFileHeader(plugin.getResource("configHeader.txt")); 
			} catch (FileNotFoundException e) {
				logger.exception("Unable to create a file header", e); 
			} catch (IOException e) {
				logger.exception("Unable to create a file header", e); 
			} 
		}
		else if (theOutFile.getName().equalsIgnoreCase("signs.yml")) {
			try {
				theHeader = getFileHeader(plugin.getResource("signsHeader.txt")); 
			} catch (FileNotFoundException e) {
				logger.exception("Unable to create a file header", e); 
			} catch (IOException e) {
				logger.exception("Unable to create a file header", e); 
			} 
		}
		else {
			logger.warning("custom", "There is no file with the name " + theOutFile.getName()); 
		}
		options.header(theHeader); 
		options.copyHeader(true); 
	}
	public void save() {
		if (shouldSave == true || alwaysSaveFiles == true) {
			try {
				configuration.save(theOutFile);
			} catch (IOException e) {
				logger.exception("Unable to save file " + theOutFile, e); 
			} 
		}
	}
}

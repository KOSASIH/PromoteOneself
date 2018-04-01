# PromoteOneself
## Description: 
The PromoteOneself plugin is built for Minecraft Spigot versions 1.8/1.12. This plugin enables server administrators to set a series of targets for player to achieve in order to gain rewards, such as rank-ups; the plugin is configured so that, when a player meets a target, the plugin can runs a set of commands as defined in the config.yml file. Targets are composed of aims; a player achieves a target upon completing all aims that are part of the target. 

This plugin also has integration with the 'Valut' plugin and the 'PlayerPoints' plugin. 


## Debug Information --- to be removed ---: 
--- This plugin curuently contains a number of debug commands; these should be removed before the plugin is built as they are a security problem. This plugin is made using eclipse; the .classpath file is excluded from this repository so should should be recreated --- 

--- Extra information is currently in the PromoteOneself/commands.txt file, but it is outdated --- 

## License: 
This plugin is under a MIT license (see the LICENSE file for the full license). 

## Aims: 
Each aim can have one of any of the following types: 
 - none - The aim must be given to a player by a server administrator 
 - xp - The player requires a certain amount of xp 
 - xpl - The player requires a certain number of levels of xp 
 - item - The player needs to possess a certain amount of a certain item 
 - password - The player needs to enter a password 
 - points - The player needs to have gathered a certain number of points 
 - playerpoints - The player needs to have gathered a certain number of points, where points are from the 'PlayerPoints' plugin (this requires the 'PlayerPoints' plugin to be installed)
 - kills - The player needs a certain number of kills 
 - economy - The player needs a certain balance (this requires the 'Vault' plugin to be installed) 
 - group - The player needs to be in a certain permissions group (this requires the 'Vault' plugin to be installed)
 - pgroup - The player needs to have a certain permissions group as its primary permissions group (this requires the 'Vault' plugin to be installed)
 - permission - The player needs to have the permission to get to the next target (e.g. pos.promote.target1, where 'target1' is the name of a target defined in the config.yml file) 

## Commands: 
 - /prom 
 - /posset 
 - /promoteoneself
 
## Permissions: 
This plugin has the following permissions: 
 - promoteoneself.* 

## Config Files: 
### The Config Files: 
This plugin uses the following config files: 
 - config.yml 
 - signs.yml 
 - players.yml 

### The config.yml File: 
The config.yml defines aims and targets, as well as the following plugin settings:
 - detectKills - Whether the plugin should listen for player kills or not (true: the plugin listens for kills; false: the plugin doesn't listen for kills) 
 - watchCommands - 
 - allowSigns - Whether the plugin should allow signs or not (true: the plugin allows signs; false: the plugin ignores all signs) 
 - resetPointsAfterEachPromotion - Whether the plugin should automatically reset the player's point count for point type aims after each promotion or not (this has no effect on the points system of the 'PlayerPoints plugin') (true: the points total is reset; false: the points total is carried on after each target is completed) 
 - updateUsernames - Whether the plugin should update the usernames listed under UUIDs or not (true: usernames are updated; false: usernames are not updated) 
 - startInPromotionTree - Whether players should start off with a target or not (true: players start off with a target; false: players start off with no target) 
 - lowestRankThatCanManuallyApproveAims - This is a string that is used only in plugin player messages that states the name of the lowest rank that server operators have decided can approve aims manually 
 - defaultPoints - The integer number of points that players start off with (this has nothing to do with 'PlayerPoints' plugin points) 

Aims are defined in this file in the section called 'aims'. An example aim is shown below: 
```yaml
aims: 
  aim1:
    type: none 
    achieve: none 
  aim2:
    type: points
	achieve: 200
```
Each section heading within the 'aims' section is the name of an aim. The two aim data fields are explained below: 
 - type - The type of the aim 
 - achieve - The value that needs to be achieved to achieve the aim (for example, the number of points or the amount of xp) 

Targets are defined in this file in the section called 'targets'. 

### The players.yml File: 

### The signs.yml File: 

## Signs: 
Signs are written in the form: 
line 1: [pos]
line2: <type>
line3: <data>
line4: <sign id as in signs.yml> 

Signs can have any of the following types: 
 - Update 
 - Points 
 - Target 
 - Sign 


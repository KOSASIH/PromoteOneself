# PromoteOneself
## Description: 
The PromoteOneself plugin is a Minecraft Spigot server plugin. This plugin enables server administrators to set a series of targets for player to achieve in order to gain rewards, such as rank-ups; the plugin is configured so that, when a player achieves a target, the plugin can runs a set of commands as defined in the config.yml file. Targets are composed of aims; a player achieves a target upon completing all aims that are part of the target. As players achieve more targets, they work their way up the 'promotion tree' until they achieve the last target. 

This plugin is compatible with the 'Vault' plugin and the 'PlayerPoints' plugin. There are currently no known compatibility issues. 

## Versions: 
The current plugin version is Development_11.0.1.8. Thie most recent 'released' version (in the 'release' section) (which is considered to be a pre-release version) is version Development_11.0.1.0. The most recently compiled .jar file can be found in the 'Jar' folder. When there is a full release, it will be found in both the 'Jar' folder and the 'releases' section. This plugin is designed to be run with Minecraft Spigot running versions between 1.7.x and 1.12.x. Most versions of Vault and PlayerPoints for these Spigot versions should work. Bugs found when running on the aforementioned Minecraft versions will be fixed. The plugin may still work with other versions but bugs will not necessarily be fixed. 

## License: 
This plugin and its source code are released under a GNU GPL v3.0 license (see the LICENSE file for the full license). This plugin is copyright (c) aappleton3/aappleton8, 2018. 

## Building the Project: 
This project was made using eclipse. To build this project, the .classpath file needs to be regenerated by eclispe. The .classpath file should contain the path of: 
 - the JDK (jdk1.8.0_152) 
 - the craftbukkit-1.12.2.jar file 
 - the Vault.jar file 
 - the PlayerPoints.jar file 

## Aims: 
Each aim can have one of any of the following types: 
 - none - The aim must be given to a player by a server administrator 
 - xp - The player requires a certain amount of xp 
 - xpl - The player requires a certain number of levels of xp 
 - item - The player needs to possess a certain amount of a certain item (where the item is specified by its name in the appropriate enum https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html) 
 - itemid - The player needs to posees a certain amount of a certain item (where the item is specified by its id) 
 - password - The player needs to enter a password 
 - points - The player needs to have gathered a certain number of points 
 - playerpoints - The player needs to have gathered a certain number of points, where points are from the 'PlayerPoints' plugin (this requires the 'PlayerPoints' plugin to be installed)
 - kills - The player needs a certain number of kills 
 - economy - The player needs a certain balance (this requires the 'Vault' plugin to be installed) 
 - group - The player needs to be in a certain permissions group (this requires the 'Vault' plugin to be installed)
 - pgroup - The player needs to have a certain permissions group as its primary permissions group (this requires the 'Vault' plugin to be installed)
 - permission - The player needs to have the permission to get to the next target (e.g. pos.promote.target1, where 'target1' is the name of a target defined in the config.yml file) 
 - command - The player must enter a certain command with a certain set of arguments 
 - sign - The player must achieve the aim by clicking on a sign 
 
Each of the above aim types can also be given to a player manually by a server administrator with the correct permission. 

## Commands: 
This plugin contains the following commands: 
 - /promoteoneself
 - /prom &lt;arguments&gt; 
 - /posset &lt;arguments&gt; 

The */promoteoneself* command shows the help page. \
The */prom help [page]* command shows the top level help pages. \
The */prom help &lt;command&gt; &lt;first argument&gt; [page]* command shows more detailed information about the */&lt;command&gt; &lt;first argument&gt; &lt;all-remaining-arguments&gt;* commands. \
The */prom help set &lt;second argument&gt; [page]* command shows more detailed information about the */posset set &lt;second argument&gt; &lt;all-remaining arguments&gt;* commands. \
The */prom version* command shows the plugin version. \
The */prom update [target [player-username [aim]]]* command checks a player's completion status for a target or an aim of a target. \
The */prom check [player|target|aim|sign|config] &lt;name&gt;* command gives information about a player, target, aim, sign or config (not specifying which one defaults to finding a player). \
The */prom password &lt;get [player] &lt;aim&gt;|set [player] &lt;password&gt; &lt;aim&gt;&gt;* command enables a player to get or set a player's password guess attempts. \
The */prom list &lt;targets|aims|players|exempt|signs&gt;* command lists all the names of the specified object type. \
The */posset exempt &lt;player&gt; &lt;true|temp|add|join&gt;* command sets the exemption status of a player (true: make exempt and delete data; temp: make exempt and keep data; add: lose exemption at next login; join: lose exemption now). \
The */posset save* command saves the config files. \
The */posset reload [check|nocheck]* command reloads the configuration files (adding nothing or 'check' makes it checks each players' aims with the aims each target specifies the player should have; specifying 'nocheck' makes it jut reload the files). \
The */posset player &lt;add|remove|delete&gt; [name]* command provides the ability to add or remove a player to/from the promotion tree or to reset a player in the promotion tree. \
The */posset set player &lt;player-username&gt; aims &lt;aim-name&gt; &lt;true|false&gt;* command sets a player's aim completetion status. \
The */posset set player &lt;player-username&gt; password &lt;aim-name&gt; &lt;password&gt;* command sets a player's password guess for an aim. \
The */posset set player &lt;player-username&gt; points &lt;set|add|remove&gt; &lt;amount&gt;* command changes the amount of points a player has. \
The */posset set player &lt;player-username&gt; sign &lt;sign-id&gt; &lt;player-usage&gt;* command sets a player's recorded sign usage. \
The */posset set player &lt;player-username&gt; finished &lt;true|false&gt;* command sets the promotion tree completion status of a player. \
The */posset set player &lt;player-username&gt; target &lt;target-name&gt;* command changes a player's target and updates the player's aims. \
The */posset set player &lt;player-username&gt; kills &lt;amount&gt;* command changes a player's recorded kill number. \
The */posset set player &lt;player-username&gt; lastusername* command makes the plugin update the player's recorded last username in the config files. \
The */posset set target &lt;target-name&gt; aims &lt;add|remove&gt; &lt;aim-name&gt;* command adds or removes an aim to/from a target. \
The */posset set target &lt;target-name&gt; leadsTo &lt;nullify|add &lt;target-name&gt;|remove &lt;target-name&gt;&gt;* command sets which targets lead on from the specified target. \
The */posset set target &lt;target-name&gt; defaultNextTarget &lt;target-name&gt;* command sets the default next target of the specified target. \
The */posset set aim &lt;aim-name&gt; type &lt;aim-type&gt;* command sets the type of an aim (this command does not work for 'command' type aims). \
The */posset set aim &lt;aim-name&gt; achieve &lt;aim-goal&gt;* command sets the goal of an aim (this command does not worm for 'command' type aims). \
The */posset set setting detectKills &lt;true|false&gt;* command sets whether the plugin should listen for player deaths for 'kills' type aims or not. \
The */posset set setting watchCommands &lt;true|false&gt;* command sets whether the plugin should listen for player commands for 'command' type aims or not. \
The */posset set setting allowSigns &lt;true|false&gt;* command sets whether the plugin should allow the use of signs or not. \
The */posset set setting defaultTarget &lt;targetName&gt;* command sets the default first target when a player first joins the promotion tree. \
The */posset set setting lowestRankThatCanManuallyApproveAims &lt;rank&gt;* command sets the informational value in the config file used in error messages stating the lowest required rank that can manually approve aims. \
The */posset set setting startInPromotionTree &lt;true|false&gt;* command sets whether players should automatically start in the promotion tree or not. \
The */posset set setting resetPointsAfterEachPromotion &lt;true|false&gt;* command sets whether a player's points should be reset after each promotion or not. \
The */posset set setting updateUsernames &lt;true|false&gt;* command sets whether the plugin should update the username recorded for the player in the config files each time the player logs in or not. \
The */posset set setting defaultPoints &lt;integer&gt;* command sets the default number of points players should have. \
The */posset set setting alwaysSaveFiles &lt;true|false&gt; command sets whether configuration files the plugin couldn't load properly should be saved regardless (this may wipe the files). \
The */posset set setting remindOnJoin &lt;true|false&gt; command sets whether the plugin should send a reminder to players each time they join. 

## Permissions: 
All permissions for this plugin default to being ops only. Any permission ending '.others' to refer to other players has, as a child permission, the permission referring to the player entering the command. This plugin has the following permissions: 
 - pos.* - The root permission for the plugin 
 - pos.save - The player can save the config files 
 - pos.reload.* - The root permission for the config reload commands 
 - pos.reload.check - The player can reload the config files and check that all player aims are correct 
 - pos.reload.nocheck - The player can reload the config files without checking that all player aims are correct *(not recommended)* 
 - pos.update.* - The root permission to update players' aims 
 - pos.update.others - Update another player's aims 
 - pos.update - Update one's own aims 
 - pos.update.add.others - Add other players to the promotion tree 
 - pos.update.add - Add oneself to the promotion tree 
 - pos.update.target.others - Update a player's aims and set which target it should progress to next 
 - pos.update.target - Update one's own aims and set which target to progress to next /posset player &lt;add|remove|delete&gt; [name]
 - pos.update.aim.others - Update an individual aim of a player without causing the player to advance 
 - pos.update.aim - Update one's own aim without causing oneself to advance 
 - pos.password.* - The root permission for getting and setting people's attempts at 'password' type aims 
 - pos.password.get.others - The permission to let a player see what another player has guessed as a password for a 'password' type aim 
 - pos.password.get - Let a player examine its own passwords that are guesses to 'password' type aims 
 - pos.password.set.others - Let a player set another player's passwords 
 - pos.password.set - Let a player set its own passwords 
 - pos.check.* - The root permission for checking players, targets and aims 
 - pos.check.others - Get the information of another player 
 - pos.check - Let a player view its own information 
 - pos.check.targets - Get the information of a target 
 - pos.check.aims - Let a player check the information of an aim 
 - pos.check.signs - Let a player check the maximum allowed usage of a sign 
 - pos.check.configs - Let a player check the saveable status of each config file (a config file is not saveable if there was an error loading it) 
 - pos.list.* - Let a player list the names of all object types 
 - pos.list.targets - Let a player list the names of every target
 - pos.list.aims - Let a player list the names of every aim
 - pos.list.players - Let a player list the name of every player 
 - pos.list.exempt - Let a player list the name of every exempt player
 - pos.list.signs - Let a player list the id of every sign 
 - pos.exempt.* - The root permission for setting a player's exemption status 
 - pos.exempt.true - Make a player exempt 
 - pos.exempt.temp - Make a player exempt, whilst keeping its data in the promotion tree 
 - pos.exempt.add - Make another player lose its exemption at its next login 
 - pos.exempt.join - Remove another player's exemption 
 - pos.player.* - The root permission for removing and resetting player information (this also gives the pos.update.add.others permission and its child pos.update.add permission) 
 - pos.player.remove.others - Remove the information about a player from the promotion tree 
 - pos.player.remove - Remove one's own information from the promotion tree 
 - pos.player.reset.others - Reset the information about a player in the promotion tree 
 - pos.player.reset - Reset one's own information in the promotion tree 
 - pos.set.* - The root permission for setting information 
 - pos.set.player.* - The root permission for setting player information 
 - pos.set.player.aim - Set a player's aim completion status (this has the pos.set.player.aim.none permission as a child permission) 
 - pos.set.player.aim.none - Set a player's aim completion status if the aim type is none 
 - pos.set.player.finished - Set a player's promotion tree completion status 
 - pos.set.player.target - Set a player's current target and update the player's aims 
 - pos.set.player.sign - Set the recorded number of times a player has used a sign 
 - pos.set.player.password - Set a player's guess at a password for an aim 
 - pos.set.player.points - Set a player's points 
 - pos.set.player.kills - Set the number of recorded kills a player has 
 - pos.set.player.player - Update a player's recorded username 
 - pos.set.target.* - The root permission for setting target information 
 - pos.set.target.aims - Set the aims of a target 
 - pos.set.target.leadsto - Set what targets a target leads to 
 - pos.set.target.defaultnexttarget - Set the default next target of a target 
 - pos.set.aim.* - The root permission for setting aim information 
 - pos.set.aim.type - Set the type of an aim 
 - pos.set.aim.achieve - Set the goal of an aim 
 - pos.set.setting.* - The root permission for setting configurable plugin values 
 - pos.set.setting.defaulttarget - Set the default starting target 
 - pos.set.setting.lowestrankthatcanmanuallyapproveaims - Set the name of the lowest rank that can manually approve aims (the rank with the pos.set.player.aim permission) 
 - pos.set.setting.startinpromotiontree - Set whether players should start in the promotion tree or not 
 - pos.set.setting.updateusernames - Set whether the plugin should update player's recorded usernames when they login or not 
 - pos.set.setting.detectkills - Set whether the plugin should listen to player deaths for 'kills' type aims or not 
 - pos.set.setting.resetpointsaftereachpromotion - Set whether players' points for 'points' type aims should be reset each time the player is promoted or not 
 - pos.set.setting.allowsigns - Set whether the plugin allows the use of signs or not 
 - pos.set.setting.watchcommands - Set whether the plugin should listen to player commands for 'command' type aims or not 
 - pos.set.setting.defaultpoints - Set the default number of points players should have 
 - pos.set.setting.alwayssavefiles - Set if files that the plugin couldn't load should be saved regardless (this may wipe the files) 
 - pos.set.setting.remindonjoin - Set if the plugin should send a reminder to players when they join 
 - pos.set.sign.* - The root permission for setting configurable sign information 
 - pos.set.sign.usage - Set the maximum number of times a player can use a sign 
 - pos.set.promote.* - Let the player be promoted to any target (only valid for 'permission' type aims) 
 - pos.set.promote.&lt;target-name&gt; - Let the player be promoted to a specific target (only valid for 'permission' type aims) 
 - pos.sign.* - The root permission for handling signs 
 - pos.sign.update.* - The root permission for handling 'update' type signs 
 - pos.sign.update.use - Use an 'update' type sign 
 - pos.sign.update.create - Create an 'update' type sign 
 - pos.sign.update.delete - Remove an 'update' type sign 
 - pos.sign.points.* - The root permission for handling 'points' type signs 
 - pos.sign.points.use - Use a 'points' type sign 
 - pos.sign.points.create - Create a 'points' type sign 
 - pos.sign.points.delete - Remove a 'points' type sign 
 - pos.sign.target.* - The root permission for handling 'target' type signs 
 - pos.sign.target.use - Use a 'target' type sign
 - pos.sign.target.create - Create a 'target' type sign 
 - pos.sign.target.delete - Remove a 'target' type sign 
 - pos.sign.sign.* - The root permission for handling 'sign' type signs 
 - pos.sign.sign.use - Use a 'sign' type sign 
 - pos.sign.sign.create - Create a 'sign' type sign 
 - pos.sign.sign.delete - Remove a 'sign' type sign 
 - pos.sign.limitexempt - Be exempt from sign usage limits 

## Config Files: 
### The Config Files: 
This plugin uses the following config files: 
 - config.yml 
 - signs.yml 
 - players.yml 

### The config.yml File: 
The config.yml file defines aims and targets, as well as the following plugin settings:
 - detectKills - Whether the plugin should listen for player kills or not (true: the plugin listens for kills; false: the plugin doesn't listen for kills) 
 - watchCommands - This specifies whether the plugin should listen to commands for other plugins when checking 'command' type aims or not (true: the plugin watched commands; false: the plugin doesn;t watch commands) 
 - allowSigns - Whether the plugin should allow signs or not (true: the plugin allows signs; false: the plugin ignores all signs) 
 - resetPointsAfterEachPromotion - Whether the plugin should automatically reset the player's point count for point type aims after each promotion or not (this has no effect on the points system of the 'PlayerPoints plugin') (true: the points total is reset; false: the points total is carried on after each target is completed) 
 - updateUsernames - Whether the plugin should update the usernames listed under UUIDs or not (true: usernames are updated; false: usernames are not updated) 
 - startInPromotionTree - Whether players should start off with a target or not (true: players start off with a target; false: players start off with no target) 
 - lowestRankThatCanManuallyApproveAims - This is a string that is used only in plugin player messages that states the name of the lowest rank that server operators have decided can approve aims manually 
 - defaultPoints - The integer number of points that players start off with (this has nothing to do with 'PlayerPoints' plugin points) 
 - commands - This is a list of commands with arguments that the plugin will watch for when dealing with 'command' type aims 
 - defaultTarget - This is the default target that players start on when they first join the promotion tree 
 - alwaysSaveFiles - This turns on or off the saving of files that are suspected to be malformed when the game loads (saving these files may wipe their contents and this option doesn;t effect every in-game command: if a file is suspected to be malformed, the server should be stopped and the file should be backed up and fixed) 
 - remindOnJoin - This sets whether the plugin will send a reminder message to each player when it joins or not 

Aims are defined in this file in the section called 'aims'. An example 'aims' section is shown below: 
```yaml
aims: 
  aim1:
    type: none 
    achieve: none 
  aim2:
    type: points
    achieve: 200
  aim3:
    type: command
    achieve: rules
  aim4:
    type: command
    achieve: rules 2
  aim5:
    type: playerpoints
    achieve: 1000
  aim6:
    type: xp
    achieve: 500
  aim7:
    type: xpl
    achieve: 8
  aim8:
    type: item
    achieve: 2;ANVIL
  aim9:
    type: itemid
    achieve: 64;27
  aim10:
    type: password
    achieve: P@ss50r$
  aim11:
    type: password
    achieve: P@ss50r$2
  aim12:
    type: kills
    achieve: 30
  aim13:
    type: economy
    achieve: 4000
  aim14:
    type: group
    achieve: player_group_name_here
  aim15:
    type: pgroup
    achieve: player_group_name_here
  aim16:
    type: permission
    achieve: target_name_here
  aim17:
    type: sign
    achieve: none
  aim18:
    type: sign
    achieve: none
  aim19:
    type: sign
    achieve: none
  aim20:
    type: sign
    achieve: none
```
Each section heading within the 'aims' section is the name of an aim. The two aim data fields are explained below: 
 - type - The type of the aim 
 - achieve - The value that needs to be achieved to achieve the aim (for example, the number of points or the amount of xp; 'sign' and 'none' type aims require 'none' as the value for this field as they are awarded upon an action rather than when a player runs the command to check its target progress) 

Targets are defined in this file in the section called 'targets'. An example 'targets' section is given below: 
```yaml
targets:
  target1: 
    aims:
      - aim1
      - aim2
    commands: 
      - this is an example command
      - completion_command_2 
    defaultNextTarget: target3
    leadsTo: 
      - target2 
      - target3
  target2:
    aims: 
      - aim2
      - aim9
    commands:
      - completion_command_3 
    defaultNextTarget: none 
    leadsTo: none
  target3:
    aims:
      - aim3
      - aim4
      - aim5
      - aim6
      - aim7
      - aim8
      - aim10
      - aim11
      - aim17
      - aim18
      - aim19
      - aim20
    commands:
      - completion_command_4
    defaultNextTarget: none
    leadsTo: none
```
Each section within the 'targets' section is the name of a target. The fields for each target are explained below: 
 - aims - The list of aims that a player must achieve in order to complete a target 
 - commands - The commands that will be run when the player completes the target 
 - defaultNextTarget - The name of the default target that will be the next target for the player to work towards 
 - leadsTo - The names of the targets that the player can progress on to after completing this target 

### The players.yml File: 
The players.yml file defines information for player which are either exempt from being in the promotion tree or in the promotion tree. An example players.yml file is given below: 
```yaml
exempt:
  UUID1:
    lastUsername: username
    exempt: true
players: 
  UUID1: 
    lastUsername: username
    target: target3
    finished: false
    data:
      password: 
        aim10: none
        aim11: P@ss50r$2
      commands:
      - aim3
      - aim4
      points: 0
      kills: 0
      signs:
        id1: 1
        id2: 0
        id3: 1
        id4: 5
    aims: 
      aim3: true 
      aim4: false
      aim5: false
      aim6: true
      aim7: true
      aim8: true
      aim10: false
      aim11: true
      aim17: false
      aim18: false
      aim19: true
      aim20: false
```
Within the 'exempt' section, each section heading represents a player UUID. The two data fields within each UUID section are explained below: 
 - lastUsername - The last known username of the player 
 - exempt - Whether the player is actually exempt or not (true: the player is exempt; false: the player is not exempt) 
Within the 'players' section, each section heading represents a player UUID. The fields and sections within each UUID section are explained below: 
 - lastUsername - The last known username of the player 
 - target - The target that the player is working towards 
 - finished - Whether the player has achieved the final target in the promotion tree or not (a 'final taget' is a target with 'none' as the value of the 'defaultNextTarget' and 'leadsTo' fields) 
 - data - Data used by the plugin when determing whether a player has achieved an aim or not (the fields within this section are explained lower down) 
 - aims - The aims that the player needs to complete in order to achieve the target; a 'true' value means that the player has finished the aim and a 'false' value means that the player has not completed the aim 
The fields within the 'data' section are explained below: 
 - password - The player's current guesses at the password for 'password' type aims 
 - commands - The aims that require the player to enter a command (the 'command' type aims) 
 - points - The points the player currently has for 'points' type aims 
 - kills - The number of players that the player has killed 
 - signs - The number of times the player has used a sign with one of the listed ids 

### The signs.yml File: 
An example signs.yml file is given below: 
```yaml
signs:
  SignId1:
    usage: -1
  SignId2:
    usage: 0
  SignId3:
    usage: 1
  SignId4:
    usage: 10
```
The only section directly within the file is the 'signs' section. Each subsection of this section is the 'id' of a sign; each sign with an id specifies its id by writing the id on its fourth line. Each sign id within the file has a 'usage' field, which specifies the maximum number of times a player can use the sign. A usage of '-1' means that each player can use the sign an unlimited number of times and a usage of '0' means that a player can only use the sign if it has the *pos.sign.limitexempt* permission. A player with the permission *pos.set.player.sign* can use the command */posset set player &lt;username&gt; sign &lt;sign-id&gt; &lt;player-usage&gt;* to change the usage numbers. 

## Signs: 
Signs are written in the form: \
line 1: [pos] \
line2: &lt;type&gt; \
line3: &lt;data&gt; \
line4: &lt;sign id as in signs.yml that is used to set the maximum number of times a player can use the sign (optional)&gt; 

Signs can have any of the following types: 
 - Update 
 - Points 
 - Target 
 - Sign 

Signs with a type of 'update' have the same use as the */prom update [target]* command, where the third line of the sign is mandatory and represents the name of the target to progress to if the player has achieved all the necessary aims. 

Signs with a type of 'points' have the same use as the */posset set player &lt;player-name&gt; points add &lt;amount&gt;* command, where the name of the player clicking the sign is the player-name and the third line of the sign is the amount of points to add. 

Signs with a type of 'target' set the player's target to the one specified by the third line of the sign. 

Signs with a type of 'sign' are used by aims with a type of 'sign': when a player clicks on one of these signs, the player completes the aim specified by the third line of the sign. 

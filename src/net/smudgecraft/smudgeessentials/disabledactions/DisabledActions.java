package net.smudgecraft.smudgeessentials.disabledactions;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

public class DisabledActions 
{
	public static void init()
	{
		DisabledActionsListener dal = new DisabledActionsListener();
	
		SmudgeEssentials.plugin.getServer().getPluginManager().registerEvents(dal, SmudgeEssentials.plugin);
		
		SmudgeEssentials.plugin.getCommand("nonether").setExecutor(new DisabledActionsCommands());
		SmudgeEssentials.plugin.getCommand("cleanchests").setExecutor(new DisabledActionsCommands());
	}
}

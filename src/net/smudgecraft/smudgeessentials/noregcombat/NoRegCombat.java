package net.smudgecraft.smudgeessentials.noregcombat;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;

public class NoRegCombat 
{
	public static void init()
	{
		CombatListener cl = new CombatListener();
		
		Bukkit.getPluginManager().registerEvents(cl, SmudgeEssentials.plugin);
		
		Bukkit.getConsoleSender().sendMessage("[SmudgeEssentials] NoRegCombat initialized.");
	}
}

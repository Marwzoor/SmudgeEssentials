package net.smudgecraft.smudgeessentials.safezonenoskills;


import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;


public class SafezoneNoSkills 
{
	public static void init()
	{
		Bukkit.getPluginManager().registerEvents(new SkillsListener(), SmudgeEssentials.plugin);
	}
}

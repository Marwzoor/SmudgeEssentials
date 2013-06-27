package net.smudgecraft.smudgeessentials.signcolors;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;

public class SignColors 
{
	public static void init()
	{
		SignListener sl = new SignListener();
		
		Bukkit.getPluginManager().registerEvents(sl, SmudgeEssentials.plugin);
		
		Bukkit.getConsoleSender().sendMessage("[SmudgeEssentials] SignColors initialized.");
	}
}

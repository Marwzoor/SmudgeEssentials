package net.smudgecraft.smudgeessentials.antipvplog;

import java.io.File;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class AntiPvPLog implements CommandExecutor
{
	public static void init()
	{
		AntiPvPLogListener apll = new AntiPvPLogListener();
		
		Bukkit.getServer().getPluginManager().registerEvents(apll, SmudgeEssentials.plugin);
		
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/pvplog.yml");
		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch(Exception e)
			{
				
			}
		}
		
		SmudgeEssentials.plugin.getCommand("pvplog").setExecutor(new AntiPvPLog());
	}
	
	public static FileConfiguration getConfig()
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/pvplog.yml");
		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch(Exception e)
			{
				
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		return config;
	}
	
	public static File getConfigFile()
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/pvplog.yml");
		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch(Exception e)
			{
				
			}
		}
		
		return file;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender instanceof Player)
		{
		Player player = (Player) sender;	
		
		if(commandLabel.equalsIgnoreCase("pvplog"))
		{
			if(args.length>0)
			{
				if(args[0].equalsIgnoreCase("info"))
				{
					if(args[1]!=null)
					{
					FileConfiguration config = getConfig();
					
					if(config.contains(args[1] + ".PvPLog"))
					{
						int amount = config.getInt(args[1] + ".PvPLog");
						
						player.sendMessage(ChatColor.GREEN + "Player " + ChatColor.AQUA + args[1] + ChatColor.GREEN + " has PvPLogged " + ChatColor.AQUA + amount + ChatColor.GREEN + " times.");
					}
					else
					{
						player.sendMessage(ChatColor.GREEN + "Player " + ChatColor.AQUA + args[1] + ChatColor.GREEN + " has PvPLogged " + ChatColor.AQUA + 0 + ChatColor.GREEN + " times.");
					}
					}
					else
					{
						player.sendMessage(ChatColor.RED + "You have to enter player name!");
					}
				}
			}
			else
			{
				player.sendMessage(ChatColor.GREEN + "=== " + ChatColor.AQUA + "PvPLog commands" + ChatColor.GREEN  + " ===");
				player.sendMessage(ChatColor.AQUA + "/pvplog info <player>" + ChatColor.GREEN + " - shows you how many times a player has pvplogged.");
			}
		}
		}
		return true;
	}
}

package net.smudgecraft.smudgeessentials.heroexpboost;

import java.io.File;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HeroExpBoost implements CommandExecutor
{
	public static Double expboost;
	
	public static void init()
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/heroexpboost.yml");
		
		FileConfiguration config;
		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
				
				config = getConfig();
				
				config.set("HeroExpBoost", Double.valueOf(0));
				
				try
				{
				config.save(file);
				}
				catch(Exception e)
				{
					
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
		config = getConfig();
		
		expboost = config.getDouble("HeroExpBoost");
		
		ExperienceListener el = new ExperienceListener();
		
		Bukkit.getPluginManager().registerEvents(el, SmudgeEssentials.plugin);
		
		SmudgeEssentials.plugin.getCommand("heroexpboost").setExecutor(new HeroExpBoost());
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("heroexpboost") && args.length>0)
		{
			if(sender.isOp() || sender instanceof ConsoleCommandSender)
			{
				if(args[0].equalsIgnoreCase("set") && args.length>1)
				{
					if(isNumber(args[1]))
					{
						Double boost = Double.parseDouble(args[1]);
						
						expboost = boost;
						
						sender.sendMessage(ChatColor.GREEN + "HeroExpBoost set to " + ChatColor.AQUA + boost + "%" + ChatColor.GREEN + ".");
					
						FileConfiguration config = getConfig();
						
						config.set("HeroExpBoost", boost);
						
						File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/heroexpboost.yml");
						
						try
						{
						config.save(file);
						}
						catch(Exception e)
						{
							
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That is not a valid percentage! You can't use strings such as %");
					}
				}
				else if(args[0].equalsIgnoreCase("get") && args.length==1)
				{
					sender.sendMessage(ChatColor.GREEN + "HeroExpBoost is set to " + ChatColor.AQUA + expboost + "%" + ChatColor.GREEN + ".");
				}
			}
		}
		return true;
	}
	
	public static FileConfiguration getConfig()
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/heroexpboost.yml");
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
	
	public static boolean isNumber(String arg)
	{
		boolean parsable=true;
		try
		{
			Double.parseDouble(arg);
		}
		catch(NumberFormatException e)
		{
			parsable=false;
		}
		return parsable;
	}
}

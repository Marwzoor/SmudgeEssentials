package net.smudgecraft.smudgeessentials.herotips;

import java.io.File;
import java.util.List;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class HeroTipsCommands implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("announce") && args.length>0)
		{
			if(args[0].equalsIgnoreCase("reload"))
			{
				if(sender instanceof Player)
				{
					Player player = (Player) sender;
					
					if(player.hasPermission("herotips.reload") || player.isOp())
					{
						HeroTips.reload();
						player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "HeroTips reloaded.");
					}
				}
				else
				{
					ConsoleCommandSender cs = Bukkit.getConsoleSender();
					
					HeroTips.reload();
					
					cs.sendMessage("[SmudgeEssentials] HeroTips reloaded!");
				}
			}
			else if(args[0].equalsIgnoreCase("disable"))
			{
				if(sender instanceof Player)
				{
					Player player = (Player) sender;
					
					if(player.hasPermission("herotips.disable") || player.isOp())
					{
						HeroTips.disable();
						player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "HeroTips disabled.");
						
					}
				}
				else
				{
					ConsoleCommandSender cs = Bukkit.getConsoleSender();
					
					cs.sendMessage("[SmudgeEssentials] HeroTips disabled.");

					HeroTips.disable();
				}
			}
			else if(args[0].equalsIgnoreCase("enable"))
			{
				if(sender instanceof Player)
				{
					Player player = (Player) sender;
					
					if(player.hasPermission("herotips.enable") || player.isOp())
					{
						HeroTips.enable();
						player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "HeroTips enabled.");
					}
				}
				else
				{
					ConsoleCommandSender cs = Bukkit.getConsoleSender();
					
					cs.sendMessage("[SmudgeEssentials] HeroTips enabled.");

					HeroTips.enable();
				}
			}
			else if(args[0].equalsIgnoreCase("ignore"))
			{
				Player player = (Player) sender;
				if(player.hasPermission("herotips.ignore") || player.isOp())
				{
				File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/herotips.yml");
				
				if(file.exists())
				{
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					
					if(HeroTips.pignore.contains(player.getName()))
					{
						HeroTips.pignore.remove(player.getName());
						
						List<String> pignore = config.getStringList("IgnorePlayers");
						
						pignore.remove(player.getName());
						
						config.set("IgnorePlayers", pignore);
						
						try
						{
							config.save(file);
						}
						catch(Exception e)
						{
							
						}
						
						player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Announcements are now enabled. type /ignore again to \n ignore announcements.");
					}
					else
					{
						HeroTips.pignore.add(player.getName());
						
						List<String> pignore = config.getStringList("IgnorePlayers");
						
						pignore.add(player.getName());
						
						config.set("IgnorePlayers", pignore);
						
						try
						{
						config.save(file);
						}
						catch(Exception e)
						{
							
						}
						
						player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Announcements are now ignored. type /ignore again to \n enable announcements.");
					}
				}
				}
			}
		}
		return true;
	}
}

package net.smudgecraft.smudgeessentials.loginz;

import java.io.File;
import java.util.ArrayList;
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

public class Loginz implements CommandExecutor
{
	public static boolean isMaintainance = false;
	public static Loginz loginz;
	
	public static void init()
	{
		
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/loginz.yml");
		
		boolean firstTime = false;
		
		if(!file.exists())
		{
			try
			{
			file.createNewFile();
			}
			catch(Exception e)
			{
			Bukkit.getConsoleSender().sendMessage("[SmudgeEssentials] Could not create loginz.yml!");
			}
			firstTime=true;
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if(firstTime==true)
		{
			config.set("HighestPlayers", 0);
			List<String> defaultCommands1 = new ArrayList<String>();
			List<String> defaultCommands2 = new ArrayList<String>();
			
			defaultCommands1.add("hero profession choose handyman");
			defaultCommands1.add("hero confirm");
			defaultCommands2.add("warp tutorial %p");
			
			config.set("FirstTimeCommandsPlayer", defaultCommands1);
			config.set("FirstTimeCommandsServer", defaultCommands2);
			
			try
			{
			config.save(file);
			}
			catch(Exception e)
			{
			Bukkit.getConsoleSender().sendMessage(SmudgeEssentials.plugin + "[SmudgeEssentials] Could not save loginz.yml");
			}
		}
		
		LoginListener ll = new LoginListener();
		
		Bukkit.getPluginManager().registerEvents(ll, SmudgeEssentials.plugin);
		
		SmudgeEssentials.plugin.getCommand("hplayers").setExecutor(new Loginz());
		SmudgeEssentials.plugin.getCommand("maintainance").setExecutor(new Loginz());
		
		Bukkit.getConsoleSender().sendMessage("[SmudgeEssentials] Loginz initialized.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args)
	{
		if(sender instanceof Player)
		{
		if(commandlabel.equalsIgnoreCase("hplayers"))
		{
			Player player = (Player) sender;
			if(player.isOp())
			{
			File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/loginz.yml");
			
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			if(config.get("HighestPlayers")!=null)
			{
				int hplayers = config.getInt("HighestPlayers");
				String date = config.getString("Date.Date");
				String time = config.getString("Date.Time");
				String onlineplayers = config.getString("OnlinePlayersAtTime");
				onlineplayers = setChatColor(onlineplayers);
				player.sendMessage(ChatColor.GOLD + "HighestPlayers: " + ChatColor.YELLOW + hplayers);
				player.sendMessage(ChatColor.GOLD + "Date: " + ChatColor.YELLOW + date);
				player.sendMessage(ChatColor.GOLD + "Time: " + ChatColor.YELLOW + time);
				player.sendMessage(ChatColor.GOLD + "PlayersAtThatTime: " + onlineplayers);
			}
			}
		}
		else if(commandlabel.equalsIgnoreCase("maintainance"))
		{
			if(((Player) sender).isOp())
			{
			if(isMaintainance=true)
			{
			isMaintainance=false;
			}
			else
			{
			Player[] players = Bukkit.getServer().getOnlinePlayers();
			for(Player player : players)
			{
				if(player.isOp()==false || player.hasPermission("loginz.maintainance")==false)
				{
					player.kickPlayer(ChatColor.YELLOW + "Maintainance");
				}
			}
			((Player) sender).sendMessage(ChatColor.GOLD + "Maintainance mode initiated, players without permission \n will not have access to the server. \n use the command \"maintainance\" again when you are done with the maintainace \n to make the server accesible.");
			isMaintainance=true;
			}
			}
		}
		}
		else if(sender instanceof ConsoleCommandSender)
		{
			if(commandlabel.equalsIgnoreCase("hplayers"))
			{
				ConsoleCommandSender cs = Bukkit.getServer().getConsoleSender();
				
				File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/loginz.yml");
				
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				if(config.get("HighestPlayers")!=null)
				{
					int hplayers = config.getInt("HighestPlayers");
					String date = config.getString("Date.Date");
					String time = config.getString("Date.Time");
					String onlineplayers = config.getString("OnlinePlayersAtTime");
					onlineplayers = setChatColor(onlineplayers);
					cs.sendMessage(ChatColor.GOLD + "HighestPlayers: " + ChatColor.YELLOW + hplayers);
					cs.sendMessage(ChatColor.GOLD + "Date: " + ChatColor.YELLOW + date);
					cs.sendMessage(ChatColor.GOLD + "Time: " + ChatColor.YELLOW + time);
					cs.sendMessage(ChatColor.GOLD + "PlayersAtThatTime: " + onlineplayers);
				}
			}
			else if(commandlabel.equalsIgnoreCase("maintainance"))
			{
				if(isMaintainance=true)
				{
				isMaintainance=false;
				}
				else
				{
				Player[] players = Bukkit.getServer().getOnlinePlayers();
				for(Player player : players)
				{
					if(player.isOp()==false)
					{
						player.kickPlayer(ChatColor.YELLOW + "Maintainance");
					}
				}
				ConsoleCommandSender cs = Bukkit.getServer().getConsoleSender();
				cs.sendMessage(ChatColor.GOLD + "[SmudgeEssentials] Maintainance mode initiated, players without permission \n will not have access to the server. \n use the command \"maintainance\" again when you are done with the maintainace \n to make the server accesible.");
				isMaintainance=true;
				}
			}
		}
		return true;
	}
	public String setChatColor(String s)
	{
		s = s.replace("&b","" + ChatColor.AQUA);
		s = s.replace("&a", "" + ChatColor.GREEN);
		return s;
	}
	
	public static FileConfiguration getConfig()
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/loginz.yml");
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		return config;
	}
}

package net.smudgecraft.smudgeessentials.adminstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.smudgecraft.smudgeessentials.adminstate.ClearChatCommand;
import net.smudgecraft.smudgeessentials.SmudgeEssentials;
import net.smudgecraft.smudgeessentials.adminchat.ChatListenerAdmin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AwayCommand implements CommandExecutor
{
	public static HashMap<Player, Boolean> adminstate = new HashMap<Player, Boolean>();
	public static List<Player> admins = new ArrayList<Player>();
	
	public static void init()
	{
		ChatListener cl = new ChatListener();
		
		ChatListenerAdmin cla = new ChatListenerAdmin();
		
		Bukkit.getPluginManager().registerEvents(cl, SmudgeEssentials.plugin);
		
		Bukkit.getPluginManager().registerEvents(cla, SmudgeEssentials.plugin);
		
		SmudgeEssentials.plugin.getCommand("away").setExecutor(new AwayCommand());
		SmudgeEssentials.plugin.getCommand("clearchat").setExecutor(new ClearChatCommand());
		
		Bukkit.getConsoleSender().sendMessage("[SmudgeEssentials] ChatEssentials initialized.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(commandLabel.equalsIgnoreCase("away"))
			{
				if(player.hasPermission("smudgeessentials.away"))
				{
					if(adminstate.containsKey(player))
					{
						if(adminstate.get(player)==false)
						{
							adminstate.put(player, true);
							player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Your admin state is now set to" + ChatColor.GOLD + "\"away\".");
						}
						else
						{
							player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Your admin state is now set to " + ChatColor.GOLD + "\"present\".");
							adminstate.put(player, false);
						}
					}
					else
					{
						player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Your admin state is now set to " + ChatColor.GOLD + "\"away\".");
						adminstate.put(player, true);
					}
					if(!admins.contains(player))
					{
						admins.add(player);
					}
				}
			}
		}
		else
		{
			if(commandLabel.equalsIgnoreCase("away"))
			{
			ConsoleCommandSender cs = (ConsoleCommandSender) sender;
			cs.sendMessage("[SmudgeEssentials] You can't issue that command from the console.");
			}
		}
		return true;
	}
}

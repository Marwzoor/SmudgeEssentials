package net.smudgecraft.smudgeessentials.adminstate;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("clearchat"))
		{
			if(sender instanceof Player)
			{
				Player player = (Player) sender;
			
				for(int i=0;i<50;++i)
				{
					player.sendMessage("");
				}
				
				player.sendMessage(ChatColor.AQUA + "Your chat has been cleared!");
				
				return true;
			}
			else
			{
			return false;
			}
		}
		return true;
	}
}

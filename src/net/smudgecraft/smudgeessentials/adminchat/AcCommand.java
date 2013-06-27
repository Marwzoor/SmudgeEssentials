package net.smudgecraft.smudgeessentials.adminchat;

import java.util.HashMap;
import java.util.List;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcCommand implements CommandExecutor
{
	  public static final HashMap<Player, List<Block>> playersInChat = new HashMap<Player, List<Block>>();
	
	  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	  {
	    Player player = (Player)sender;
	    if (commandLabel.equalsIgnoreCase("ac"))
	    {
	    	if (player.hasPermission("adminchat.use"))
	    	{
	    		if (playersInChat.containsKey(player))
	    		{
	    			playersInChat.remove(player);
	    			player.sendMessage(ChatColor.GREEN + "You Left Admin Chat!");
	    		}
	    		else
	    		{
	    			playersInChat.put(player, null);
	    			player.sendMessage(ChatColor.GREEN + "You Entered Admin Chat!");
	    		}
	    	}
	    	else
	    	  player.sendMessage(ChatColor.RED + "YOU DONT HAVE PERMISSION TO DO THAT!");
	    }
	    return true;
	  }
	  
	  public static boolean isInAdminChat(Player player)
	  {
		  if(player.hasPermission("adminchat.use"))
		  {
			  if(playersInChat.containsKey(player))
			  {
				  return true;
			  }
			  else
			  {
				  return false;
			  }
		  }
		  else
		  {
			  return false;
		  }
	  }
	  
	  public static void init()
	  {
		  SmudgeEssentials.plugin.getCommand("ac").setExecutor(new AcCommand());
	  }
}

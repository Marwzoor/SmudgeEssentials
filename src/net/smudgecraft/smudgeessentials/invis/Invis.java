package net.smudgecraft.smudgeessentials.invis;

import java.util.ArrayList;
import java.util.List;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invis implements CommandExecutor
{
  public static List<InvisPlayer> invisPlayers = new ArrayList<InvisPlayer>();
  public static Invis plugin;
  
  public static void init()
  {  
	PlayerListener pl = new PlayerListener();
    Bukkit.getPluginManager().registerEvents(pl, SmudgeEssentials.plugin);
    SmudgeEssentials.plugin.getCommand("invis").setExecutor(new Invis());
  }

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    Player player = (Player)sender;
    InvisPlayer ip = getInvisPlayer(player);
    if (commandLabel.equalsIgnoreCase("invis"))
    {
      if (player.hasPermission("invis.use"))
      {
        if (args.length == 0)
        {
          if (ip.getIsInvisible())
          {
            ip.makeVisible();
          }
          else
          {
            ip.makeInvis();
          }

        }
        else if ((args.length >= 1) && (args[0].equalsIgnoreCase("leave")))
        {
          for (Player p : Bukkit.getOnlinePlayers())
          {
            if (!p.hasPermission("invis.see"))
              p.sendMessage(ChatColor.YELLOW + player.getName() + " has left the game.");
            else
              p.sendMessage(ChatColor.AQUA + player.getName() + " used a false leave message.");
          }
        }
        else if ((args.length >=2) && args[0].equalsIgnoreCase("player"))
        {
        	Player tplayer = Bukkit.getPlayer(args[1]);
        	InvisPlayer itp = getInvisPlayer(tplayer);
        	
        	if(itp!=null)
        	{
        		if(itp.getIsInvisible())
        		{
        			itp.makeVisible(player);
        		}
        		else
        		{
        			itp.makeInvis(player);
        		}
        	}
        	else
        	{
        		player.sendMessage(ChatColor.AQUA + args[1] + ChatColor.RED + " is not online!");
        	}
        }

      }

    }

    return true;
  }

  public static boolean isInvis(Player player)
  {
    for (InvisPlayer ip : invisPlayers)
    {
      if (ip.getPlayer().equals(player)) {
        return true;
      }
    }

    return false;
  }

  public static InvisPlayer getInvisPlayer(Player player)
  {
    for (InvisPlayer ip : invisPlayers)
    {
      if (ip.getPlayer().equals(player))
        return ip;
    }
    return null;
  }
}
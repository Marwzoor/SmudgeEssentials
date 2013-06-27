package net.smudgecraft.smudgeessentials.invis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class InvisPlayer
{
  protected Player player;
  protected boolean isInvisible;

  public InvisPlayer(Player player)
  {
    this.player = player;
    this.isInvisible = false;
  }

  public Player getPlayer()
  {
    return this.player;
  }

  public void setIsInvisible(boolean isInvisible)
  {
    this.isInvisible = isInvisible;
  }

  public boolean getIsInvisible()
  {
    return this.isInvisible;
  }

  public void makeVisible()
  {
    for (Player p : Bukkit.getServer().getOnlinePlayers())
    {
      if (p.hasPermission("invis.see"))
        continue;
      p.showPlayer(this.player);
    }

    this.isInvisible = false;
    this.player.sendMessage(ChatColor.GREEN + "You Are Now Visible Again!");
  }
  
  public void makeVisible(Player from)
  {
	  for(Player p : Bukkit.getServer().getOnlinePlayers())
	  {
		  if(p.hasPermission("invis.see"))
			  continue;
		  p.showPlayer(this.player);
	  }
	  
	  this.isInvisible = false;
	  from.sendMessage(ChatColor.GREEN + "You Have Made " + ChatColor.AQUA + this.player.getName() + ChatColor.GREEN + " Visable Again!");
  }

  public void makeInvis(Player from)
  {
	    for (Player p : Bukkit.getServer().getOnlinePlayers())
	    {
	      if (p.hasPermission("invis.see"))
	        continue;
	      p.hidePlayer(this.player);
	    }

	    this.isInvisible = true;
	    from.sendMessage(ChatColor.GREEN + "You Have Made " + ChatColor.AQUA + this.player.getName() + ChatColor.GREEN + " Invisable!");
  }
  
  public void makeInvis()
  {
    for (Player p : Bukkit.getServer().getOnlinePlayers())
    {
      if (p.hasPermission("invis.see"))
        continue;
      p.hidePlayer(this.player);
    }

    this.isInvisible = true;
    this.player.sendMessage(ChatColor.GREEN + "You Became Invisible!");
  }
}
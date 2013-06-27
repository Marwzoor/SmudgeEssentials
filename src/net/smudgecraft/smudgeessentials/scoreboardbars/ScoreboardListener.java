package net.smudgecraft.smudgeessentials.scoreboardbars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
public class ScoreboardListener implements Listener
{
	@EventHandler
	public void onPlayerRegainHealthEvent(EntityRegainHealthEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			
			if(ScoreboardBars.hasScoreboard(player))
			{
				ScoreboardBars.updateScoreboard(player);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		ScoreboardBars.setScoreboard(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event)
	{
		ScoreboardBars.removeScoreboard(event.getPlayer());
	}
}

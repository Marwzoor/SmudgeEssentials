package net.smudgecraft.smudgeessentials.nonetherportals;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class PortalListener implements Listener
{
	
	@EventHandler
	public void onPortalCreateEvent(PortalCreateEvent event)
	{
		if(event.getReason().equals(CreateReason.FIRE))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerTeleportEvent(PlayerTeleportEvent event)
	{
		if(event.getCause().equals(TeleportCause.NETHER_PORTAL) || event.getCause().equals(TeleportCause.END_PORTAL))
		{
			if(!event.getPlayer().isOp())
			{
				event.setCancelled(true);
			}
		}
	}
}

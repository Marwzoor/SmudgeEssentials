package net.smudgecraft.smudgeessentials.disabledactions;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;
import org.bukkit.inventory.ItemStack;

public class DisabledActionsListener implements Listener
{
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event)
	{
		String[] split = event.getMessage().split(" ");
		String[] disallowed = new String[]{ "me", "m" };
		
		if(split.length<1) { return; }
		
		String cmd = split[0].toLowerCase().replace("/", "");
		
		Player player = event.getPlayer();
		if(!player.isOp())
		{
			for(String s : disallowed)
			{
				if(s.equalsIgnoreCase(cmd))
				{
					player.sendMessage(ChatColor.RED + "You don't have permission to do that!");
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onLavaDispenseEvent(BlockDispenseEvent event)
	{	
		if(event.getItem().getType().equals(Material.LAVA_BUCKET))
				event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityExplodeEvent(EntityExplodeEvent event)
	{
		if(event.getEntity() instanceof ExplosiveMinecart)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEnderPearlUseEvent(PlayerInteractEvent event)
	{	
		Player player = event.getPlayer();
		
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			ItemStack iteminhand = player.getItemInHand();
						
			if(iteminhand.getType().equals(Material.ENDER_PEARL))
			{
				if(!player.hasPermission("disabledactions.enderpearl") || !player.isOp())
				{
					player.sendMessage(ChatColor.RED + "You are not allowed to use enderpearls!");
					event.setCancelled(true);
				}
			}
			
			else if(iteminhand.getType().equals(Material.POTION))
			{
				if(iteminhand.getDurability()==16462 || iteminhand.getDurability()==16430 || iteminhand.getDurability()==16398 || iteminhand.getDurability()==8270 || iteminhand.getDurability()==8238 || iteminhand.getDurability()==8206)
				{
				if(!player.hasPermission("disabledactions.invisibility") || !player.isOp())
				{
					player.sendMessage(ChatColor.RED + "You are not allowed to turn invisible!");
					event.setCancelled(true);
				}
				}
			}
			
		}
		
		else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			ItemStack iteminhand = player.getItemInHand();
			
			if(iteminhand.getType().equals(Material.ENDER_PEARL))
			{
				if(!player.hasPermission("disabledactions.enderpearl") || !player.isOp())
				{
					player.sendMessage(ChatColor.RED + "You are not allowed to use enderpearls!");
					event.setCancelled(true);
				}
			}
			
			else if(iteminhand.getType().equals(Material.LAVA_BUCKET) || iteminhand.getType().equals(Material.LAVA))
			{
				if(!player.hasPermission("disabledactions.lavaplacement") || !player.isOp())
				{
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "You are not allowed to place lava!");
				}
			}
			
			else if(iteminhand.getType().equals(Material.POTION))
			{
				if(iteminhand.getDurability()==16462 || iteminhand.getDurability()==16430 || iteminhand.getDurability()==16398 || iteminhand.getDurability()==8270 || iteminhand.getDurability()==8238 || iteminhand.getDurability()==8206)
				{
				if(!player.hasPermission("disabledactions.invisibility") || !player.isOp())
				{
					player.sendMessage(ChatColor.RED + "You are not allowed to turn invisible!");
					event.setCancelled(true);
				}
				}
			}
		}
	}
	
	@EventHandler
	public void onEnderPearlTeleportEvent(PlayerTeleportEvent event)
	{
		if(event.isCancelled())
			return;
		
		Player player = event.getPlayer();
		
		if(event.getCause().equals(TeleportCause.ENDER_PEARL))
		{
			if(!player.hasPermission("disabledactions.enderpearl") || !player.isOp())
			{
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You are not allowed to use enderpearls!");
			}
		}
	}
	
	@EventHandler
	public void onMobSpawnerEvent(CreatureSpawnEvent event)
	{
		if(event.getSpawnReason().equals(SpawnReason.SPAWNER))
			event.setCancelled(true);
	}
	
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

package net.smudgecraft.smudgeessentials.antipvplog;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.herocraftonline.heroes.characters.Hero;

public class AntiPvPLogListener implements Listener
{
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event)
	{
		final Player player = event.getPlayer();

		Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
		
		Boolean isInCombatWithPlayer = false;
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
		
		if(hero.isInCombatWith(p))
		{
			
			isInCombatWithPlayer = true;
		}
		
		}
		
		if(isInCombatWithPlayer)
		{
				PlayerInventory pi = player.getInventory();
				
				for(ItemStack is : pi.getContents())
				{
					if(is!=null)
					{
					player.getWorld().dropItemNaturally(player.getLocation(), is);
					}
				}
				
				pi.clear();
				
				player.setHealth(0D);
				
				Bukkit.getServer().broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + " tried to PvPLog!");
				
				FileConfiguration config = AntiPvPLog.getConfig();
				
				if(config.contains(player.getName() + ".PvPLogs"))
				{
					
				int amount = config.getInt(player.getName() + ".PvPLogs");
				
				++amount;
				
				config.set(player.getName() + ".PvPLogs", amount);
				}
				else
				{
					config.set(player.getName() + ".PvPLogs", 1);
				}
				
				try
				{
					config.save(AntiPvPLog.getConfigFile());
				}
				catch(Exception e)
				{
					
				}
		}
	}
}

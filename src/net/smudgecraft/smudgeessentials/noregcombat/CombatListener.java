package net.smudgecraft.smudgeessentials.noregcombat;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.herocraftonline.heroes.characters.Hero;

public class CombatListener implements Listener
{
	public CombatListener()
	{
	}
	
	@EventHandler
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
			
			if(hero.isInCombat())
			{
				event.setCancelled(true);
			}
			
		}
	}
}

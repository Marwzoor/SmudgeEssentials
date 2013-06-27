package net.smudgecraft.smudgessentials.safezonenoskills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.api.events.SkillUseEvent;
import com.herocraftonline.heroes.characters.skill.Skill;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

public class SkillsListener implements Listener
{
	@EventHandler
	public void onSkillUseEvent(SkillUseEvent event)
	{
		Skill skill = event.getSkill();
		Player player = event.getHero().getPlayer();
		
		if(!player.isOp() && skill!=null && player!=null)
		{
			if(Board.getFactionAt(new FLocation(player.getLocation())).isSafeZone())
			{
				if(!isSkillAllowed(skill))
				{
					player.sendMessage(ChatColor.RED + "You can't use skills in safezone!");
					event.setCancelled(true);
				}
			}
		}
	}
	
	public boolean isSkillAllowed(Skill skill)
	{
		String[] allowed = new String[]{ "Pray", "SummonArrow", "ManaPotion" };
		
		for(String s : allowed)
		{
			if(skill.getName().equalsIgnoreCase(s))
				return true;
		}
		return false;
	}
}
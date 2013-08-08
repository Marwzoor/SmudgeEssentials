package net.smudgecraft.smudgeessentials.safezonenoskills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.api.events.SkillUseEvent;
import com.herocraftonline.heroes.characters.skill.Skill;
import com.massivecraft.factions.FFlag;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.mcore.ps.PS;

public class SkillsListener implements Listener
{
	@EventHandler
	public void onSkillUseEvent(SkillUseEvent event)
	{
		Skill skill = event.getSkill();
		Player player = event.getHero().getPlayer();
		
		if(!player.isOp() && skill!=null && player!=null)
		{
			if(BoardColls.get().getFactionAt(PS.valueOf(player.getLocation())).getFlag(FFlag.PVP)==false)
			{
				if(!isSkillAllowed(skill))
				{
					player.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "Skill" + ChatColor.GRAY + "]" + ChatColor.RED + "You can't use that skill here!");
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
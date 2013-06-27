package net.smudgecraft.smudgeessentials.heroexpboost;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.api.events.ExperienceChangeEvent;
import com.herocraftonline.heroes.characters.classes.HeroClass.ExperienceType;

public class ExperienceListener implements Listener
{
	@EventHandler
	public void onHeroGainExperienceEvent(ExperienceChangeEvent event)
	{
		Double exp = event.getExpChange();
		
		if(HeroExpBoost.expboost>0 && !event.getSource().equals(ExperienceType.ADMIN) && !event.getSource().equals(ExperienceType.DEATH))
		{
			Double tempexp = HeroExpBoost.expboost/100;
			
			tempexp = tempexp + 1;
			
			exp = exp*tempexp;
			
			event.setExpGain(exp);
		}
	}
}
package net.smudgecraft.smudgeessentials.heroexpboost;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.api.events.ExperienceChangeEvent;

public class ExperienceListener implements Listener
{
	@EventHandler
	public void onHeroGainExperienceEvent(ExperienceChangeEvent event)
	{
		if(!HeroExpBoost.expboosts.isEmpty())
		{
		double exp = event.getExpChange();
		
		ExpBoost expboost = HeroExpBoost.getByHighestPriority();
		
		if(expboost!=null)
		{
		
			double tempexp = expboost.getBoost()/100;
			
			tempexp = tempexp + 1;
			
			exp = exp*tempexp;
			
			event.setExpGain(exp);
		}
		}
	}
}
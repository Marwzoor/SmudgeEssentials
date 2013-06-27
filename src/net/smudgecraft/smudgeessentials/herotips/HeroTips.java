package net.smudgecraft.smudgeessentials.herotips;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;
import net.smudgecraft.smudgeessentials.util.Util;

public class HeroTips
{
	public static List<Integer> scheduleids = new ArrayList<Integer>();
	public static final List<String> pignore = new ArrayList<String>();
	
	public static void init()
	{
		SmudgeEssentials.plugin.getCommand("announce").setExecutor(new HeroTipsCommands());
		
		
		File config = new File(SmudgeEssentials.plugin.getDataFolder() + "/herotips.yml");
		
		boolean firstTime=false;
		
		if(!config.exists())
		{
			try
			{
				config.createNewFile();
				firstTime=true;
			}
			catch(Exception e)
			{
				
			}
		}
		
		FileConfiguration conf = YamlConfiguration.loadConfiguration(config);
		
		if(firstTime)
		{
			conf.set("Guidance.Period.Hours", Integer.valueOf(0));
			conf.set("Guidance.Period.Minutes", Integer.valueOf(7));
			conf.set("Guidance.Period.Seconds", Integer.valueOf(0));
			conf.set("Guidance.isEnabled", true);
			List<String> defaultList = new ArrayList<String>();
			defaultList.add("&9[Guidance]: &bWhen you reach level 30 you will be able to choose a new progression class.");
			defaultList.add("&9[Guidance]: &bThe mage path is able to enchant weapons and armor. Although, low leveled mages will have a hard time enchanting items.");
			conf.set("Guidance.Messages", defaultList);
			List<String> defaultSchedules = new ArrayList<String>();
			defaultSchedules.add("Guidance");
			conf.set("Schedules", defaultSchedules);
			
			try
			{
			conf.save(config);
			}
			catch(Exception e)
			{
				
			}
		}
		
		List<String> schedules = conf.getStringList("Schedules");
		
		for(String s : schedules)
		{
		
		int hours = conf.getInt(s + ".Period.Hours");
		
		int minutes = conf.getInt(s + ".Period.Minutes");
		
		int seconds = conf.getInt(s + ".Period.Seconds");
		
		final List<String> messages = conf.getStringList(s + ".Messages");
		
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SmudgeEssentials.plugin, new Runnable()
		{
			public void run()
			{
				Random rand = new Random();
				
				int random = rand.nextInt(messages.size());
				
				String message = messages.get(random);
				
				String[] msg = Util.getMultilineMessage(message);

				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(!pignore.contains(p.getName()))
					{
						for(String s : msg)
						{
							p.sendMessage(Util.setChatColor(s));
						}
					}
				}
				
			}
		},(20L*60*60*hours) + (20L*60*minutes) + (20L*seconds), (20L*60*60*hours) + (20L*60*minutes) + (20L*seconds));
		
		scheduleids.add(id);
		
		List<String> playerignore = conf.getStringList("IgnorePlayers");
		
		pignore.addAll(playerignore);
		
		}
	}
	
	public static void reload()
	{
		disable();
		enable();
	}
	
	public static void disable()
	{
		for(int id : scheduleids)
		{
			Bukkit.getScheduler().cancelTask(id);
		}
		
		scheduleids.clear();
		pignore.clear();
	}
	
	public static void enable()
	{
		init();
	}
}
package net.smudgecraft.smudgeessentials.heroexpboost;

import java.io.File;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ExpBoost 
{
	private final String playername;
	private boolean isEnabled;
	private String boostname;
	private final int boost;
	private int hours;
	private int minutes;
	private int scheduleid;
	private int priority;
	
	public ExpBoost(String pname, int boost, int hours, int minutes, int priority, String boostname)
	{
		this.playername=pname;
		this.boost=boost;
		this.hours=hours;
		this.minutes=minutes;
		this.priority=priority;
		this.boostname=boostname;
	}
	
	public void startCountdown()
	{
		if(this.isEnabled==false)
		{
		this.isEnabled=true;
		this.scheduleid=Bukkit.getScheduler().scheduleSyncRepeatingTask(SmudgeEssentials.plugin, getRunnable(), 20L*60, 20L*60);
		}
	}
	
	public void stopCountdown()
	{
		if(this.isEnabled==true)
		{
		this.isEnabled=false;
		Bukkit.getScheduler().cancelTask(scheduleid);
		}
	}
	
	public Runnable getRunnable()
	{
		final ExpBoost expboost = this;
		
		Runnable run = new Runnable()
		{
			public void run()
			{
				if(expboost.getMinutes()==0)
				{
					if(expboost.getHours()==0)
					{
						expboost.stopCountdown();
						
						ExpBoost exb = null;
						
						for(ExpBoost exp : HeroExpBoost.expboosts)
						{
							exp.setPriority(exp.getPriority()-1);
							if(exp.getPriority()==1)
							{
								exp.startCountdown();
								exb=exp;
							}
						}
						
						FileConfiguration config = HeroExpBoost.getConfig();
						
						config.set("expboosts." + expboost.boostname, null);
						
						try
						{
							config.save(new File(SmudgeEssentials.plugin.getDataFolder() + "/heroexpboost.yml"));
						}
						catch(Exception e)
						{
							
						}
						
						HeroExpBoost.expboosts.remove(expboost);
						
						Bukkit.getConsoleSender().sendMessage("ExpBoost by the name: " + expboost.boostname + " ended!");
						Bukkit.broadcastMessage(ChatColor.GOLD + "Expboost purchased by " + ChatColor.AQUA + expboost.getPlayerName() + ChatColor.GOLD + " ended!");
						if(exb==null)
						{
							Bukkit.broadcastMessage(ChatColor.GOLD + "There was no expboost scheduled after this!");
							Bukkit.broadcastMessage(ChatColor.GOLD + "Purchase expboosts at: " + ChatColor.YELLOW + "http://shop.smudgecraft.net");
						}
						else
						{
							Bukkit.broadcastMessage(ChatColor.GOLD + "Expboost purchased by " + ChatColor.AQUA + exb.getPlayerName() + ChatColor.GOLD + " has started!");
							Bukkit.broadcastMessage(ChatColor.GOLD + "The boost is " + ChatColor.YELLOW + exb.getBoost() + "%" + ChatColor.GOLD + " extra exp.");
							if(exb.getHours()==0)
							{
								Bukkit.broadcastMessage(ChatColor.GOLD + "It will expire in: " + ChatColor.YELLOW + exb.getMinutes() + " minutes.");
							}
							else if(exb.getMinutes()==0)
							{
								Bukkit.broadcastMessage(ChatColor.GOLD + "It will expire in: " + ChatColor.YELLOW + exb.getHours() + " hours.");
							}
							else
							{
								Bukkit.broadcastMessage(ChatColor.GOLD + "It will expire in: " + ChatColor.YELLOW + exb.getHours() + " hours " + ChatColor.GOLD + "and " + ChatColor.YELLOW + exb.getMinutes() + " minutes.");
							}
						}
					}
					else
					{
						expboost.setHours(expboost.getHours()-1);
						expboost.setMinutes(59);
					}
				}
				else
				{
					expboost.setMinutes(expboost.getMinutes()-1);
				}
			}
		};
		
		return run;
	}
	
	public String getPlayerName()
	{
		return this.playername;
	}
	
	public int getBoost()
	{
		return this.boost;
	}
	
	public int getHours()
	{
		return this.hours;
	}
	
	public int getMinutes()
	{
		return this.minutes;
	}
	
	public int getScheduleId()
	{
		return this.scheduleid;
	}
	
	public void setHours(int hours)
	{
		this.hours=hours;
	}
	
	public void setMinutes(int minutes)
	{
		this.minutes=minutes;
	}
	
	public int getPriority()
	{
		return this.priority;
	}
	
	public void setPriority(int priority)
	{
		this.priority=priority;
	}
	
	public void save()
	{
		FileConfiguration config = HeroExpBoost.getConfig();
		
			ConfigurationSection node = config.getConfigurationSection("expboosts");
			
			if(node==null)
				return;
			
				node.set(boostname + ".player", playername);
				node.set(boostname + ".priority", priority);
				node.set(boostname + ".boost", boost);
				node.set(boostname + ".time-remaining.hours", hours);
				node.set(boostname + ".time-remaining.minutes", minutes);
				
				File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/heroexpboost.yml");
		
				try
				{
					config.save(file);
				}
				catch(Exception e)
				{
					
				}
	}
}

package net.smudgecraft.smudgeessentials.heroexpboost;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class HeroExpBoost implements CommandExecutor
{
	public static final List<ExpBoost> expboosts = new ArrayList<ExpBoost>();
	
	private static boolean isEnabled=false;
	
	public static int savescheduleid;
	
	public static void init()
	{
		isEnabled=true;
		
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/heroexpboost.yml");
		
		FileConfiguration config;
		
		loadExpBoosts();
		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
				
				config = getConfig();
				
				config.createSection("expboosts");
				
				try
				{
				config.save(file);
				}
				catch(Exception e)
				{
					
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
		//Scheduled save of the expboosts.
		
		savescheduleid = SmudgeEssentials.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(SmudgeEssentials.plugin, new Runnable()
		{
			public void run()
			{
				saveBoosts();
			}
		}, 20L*60*10, 20L*60*10);
		
		ExperienceListener el = new ExperienceListener();
		
		Bukkit.getPluginManager().registerEvents(el, SmudgeEssentials.plugin);
		
		SmudgeEssentials.plugin.getCommand("heroexpboost").setExecutor(new HeroExpBoost());
		SmudgeEssentials.plugin.getCommand("expboost").setExecutor(new HeroExpBoost());
	}
	
	public static boolean isEnabled()
	{
		return isEnabled;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("heroexpboost"))
		{
			if(sender.isOp() || sender instanceof ConsoleCommandSender)
			{
				if(args[0].equalsIgnoreCase("add") && args.length>2)
				{
					if(isNumber(args[1]) && isNumber(args[3]) && isNumber(args[4]))
					{
						int boost = Integer.parseInt(args[1]);
						int hours = Integer.parseInt(args[3]);
						int minutes = Integer.parseInt(args[4]);
						
						FileConfiguration config = getConfig();
						
						if(config.contains("expboosts"))
						{
						
						ConfigurationSection node = config.getConfigurationSection("expboosts");
						
						int number=0;
						
						for(String s : node.getKeys(true))
						{
							if(!s.contains("."))
							{
								s = s.replaceAll("[\\D]", "");
								if(Integer.parseInt(s)>number)
									number=Integer.parseInt(s);
							}
						}
						
						number++;
						
						ExpBoost expboost = new ExpBoost(args[2], boost, hours, minutes, getNewPriority(), "exp" + number);
						
						expboost.save();
						
						expboosts.add(expboost);
						
						sender.sendMessage(ChatColor.GREEN + "HeroExpBoost set to " + ChatColor.AQUA + boost + "%");
												
						getByHighestPriority().startCountdown();
					
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That is not a valid percentage! You can't use strings such as %");
					}
				}
				else if(args[0].equalsIgnoreCase("get") && args.length==1)
				{
					if(expboosts.isEmpty() || getByHighestPriority()==null)
					{
						sender.sendMessage(ChatColor.GREEN + "There are no expboost active at the moment!");
					}
					else
					{
						sender.sendMessage(ChatColor.GREEN + "Current expboost is: " + getByHighestPriority().getBoost() + "%");
					}
				}
			}
		}
		else if(commandLabel.equalsIgnoreCase("expboost"))
		{
			if(sender instanceof Player)
			{
				Player player = (Player) sender;
				
				if(expboosts.isEmpty() || getByHighestPriority()==null)
				{
					player.sendMessage(ChatColor.GOLD + "There is no active expboost at the moment!");
					player.sendMessage(ChatColor.GOLD + "Purchase expboosts at: " + ChatColor.YELLOW + "http://shop.smudgecraft.net");
				}
				else
				{
					ExpBoost exb = getByHighestPriority();
					
					player.sendMessage(ChatColor.GOLD + "The current expboost is giving " + ChatColor.YELLOW + exb.getBoost() + "%" + ChatColor.GOLD + " extra exp.");
					player.sendMessage(ChatColor.GOLD + "Thanks to " + ChatColor.AQUA + exb.getPlayerName() + ChatColor.GOLD + " for purchasing!");
					if(exb.getHours()==0)
					{
						player.sendMessage(ChatColor.GOLD + "It will expire in: " + ChatColor.YELLOW + exb.getMinutes() + " minutes.");
					}
					else if(exb.getMinutes()==0)
					{
						player.sendMessage(ChatColor.GOLD + "It will expire in: " + ChatColor.YELLOW + exb.getHours() + " hours.");
					}
					else
					{
						player.sendMessage(ChatColor.GOLD + "It will expire in: " + ChatColor.YELLOW + exb.getHours() + " hours" + ChatColor.GOLD + " and " + ChatColor.YELLOW + exb.getMinutes() + " minutes.");
					}
					player.sendMessage(ChatColor.GOLD + "There may be more exp boosts scheduled after this one.");
				}
			}
		}
		return true;
	}
	
	public static FileConfiguration getConfig()
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/heroexpboost.yml");
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch(Exception e)
			{
				
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		return config;
	}
	
	public static void loadExpBoosts()
	{
		FileConfiguration config = getConfig();
		
		if(config.contains("expboosts"))
		{
			ConfigurationSection node = config.getConfigurationSection("expboosts");
			
			for(String s : node.getKeys(true))
			{
				if(!s.contains("."))
				{
					String playername = node.getString(s + ".player");
					String boostname = s;
					int priority = node.getInt(s + ".priority");
					int boost = node.getInt(s + ".boost");
					int hours = node.getInt(s + ".time-remaining.hours");
					int minutes = node.getInt(s + ".time-remaining.minutes");
					
					ExpBoost expboost = new ExpBoost(playername, boost, hours, minutes, priority, boostname);
					
					expboosts.add(expboost);
					
					if(expboost.getPriority()==1)
						expboost.startCountdown();
				}
			}
		}
	}
	
	public static boolean isNumber(String arg)
	{
		boolean parsable=true;
		try
		{
			Double.parseDouble(arg);
		}
		catch(NumberFormatException e)
		{
			parsable=false;
		}
		return parsable;
	}
	
	public static ExpBoost getByHighestPriority()
	{
		ExpBoost expboost=null;
		
		for(ExpBoost exb : expboosts)
		{
			if(expboost==null)
				expboost=exb;
			
			if(expboost.getPriority()>exb.getPriority())
			{
				expboost=exb;
			}
		}
		
		return expboost;
	}
	
	public static int getNewPriority()
	{
		int priority=1;
		
		for(ExpBoost exb : expboosts)
		{
			if(exb.getPriority()>=priority)
				priority=exb.getPriority()+1;
		}
		
		return priority;
	}
	
	public static void saveBoosts()
	{
		for(ExpBoost exb : expboosts)
		{
			exb.save();
		}
	}
}

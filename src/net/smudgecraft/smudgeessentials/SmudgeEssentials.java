package net.smudgecraft.smudgeessentials;


import java.io.File;

import net.smudgecraft.smudgeessentials.adminchat.AcCommand;
import net.smudgecraft.smudgeessentials.adminstate.AwayCommand;
import net.smudgecraft.smudgeessentials.antipvplog.AntiPvPLog;
import net.smudgecraft.smudgeessentials.backups.Backups;
import net.smudgecraft.smudgeessentials.bpermssimplified.BPermsSimplified;
import net.smudgecraft.smudgeessentials.disabledactions.DisabledActions;
import net.smudgecraft.smudgeessentials.heroexpboost.HeroExpBoost;
import net.smudgecraft.smudgeessentials.herotips.HeroTips;
import net.smudgecraft.smudgeessentials.invis.Invis;
import net.smudgecraft.smudgeessentials.itemz.Itemz;
import net.smudgecraft.smudgeessentials.levelup.LevelUp;
import net.smudgecraft.smudgeessentials.loginz.Loginz;
import net.smudgecraft.smudgeessentials.noregcombat.NoRegCombat;
import net.smudgecraft.smudgeessentials.notes.NoteCommand;
import net.smudgecraft.smudgeessentials.playerheads.PlayerHeads;
import net.smudgecraft.smudgeessentials.safezonenoskills.SafezoneNoSkills;
import net.smudgecraft.smudgeessentials.scoreboardbars.ScoreboardBars;
import net.smudgecraft.smudgeessentials.signcolors.SignColors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.heroes.Heroes;
import com.massivecraft.factions.Factions;

import de.bananaco.bpermissions.imp.Permissions;

public class SmudgeEssentials extends JavaPlugin
{
	public static SmudgeEssentials plugin;
	public static Heroes heroes;
	public static Factions factions;
	public static Permissions bperms;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		setupSE();
	}
	
	@Override
	public void onDisable()
	{
		if(HeroExpBoost.isEnabled())
		{
			getServer().getScheduler().cancelTask(HeroExpBoost.savescheduleid);
			HeroExpBoost.saveBoosts();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("se") && args.length>0)
		{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if(player.isOp())
			{
				if(args[0].equalsIgnoreCase("reload"))
				{
					File file = new File(plugin.getDataFolder() + "/config.yml");
					
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					
					if(config.getBoolean("Backups")==true)
					{
					Backups.reloadBackups();
					player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Backups reloaded!");
					}
				}
			}
		}
		else
		{
			ConsoleCommandSender cs = (ConsoleCommandSender) sender;
			
			if(args[0].equalsIgnoreCase("reload"))
			{
				File file = new File(plugin.getDataFolder() + "/config.yml");
				
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				
				if(config.getBoolean("Backups")==true)
				{
				Backups.reloadBackups();
				cs.sendMessage("[SmudgeEssentials] Backups reloaded!");
				}
			}
		}
		}
		return true;
	}
	
	public static void setupSE()
	{
		ConsoleCommandSender cs = plugin.getServer().getConsoleSender();
		
		PluginDescriptionFile pdfFile = plugin.getDescription();
		
		cs.sendMessage("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " has been enabled.");
		
		heroes = (Heroes) plugin.getServer().getPluginManager().getPlugin("Heroes");
		
		factions = (Factions) plugin.getServer().getPluginManager().getPlugin("Factions");
		
		bperms = (Permissions) plugin.getServer().getPluginManager().getPlugin("bPermissions");
		
		File mainfolder = plugin.getDataFolder();
		
		File configfile = new File(plugin.getDataFolder() + "/config.yml");
		
		if(!mainfolder.exists())
		{
			mainfolder.mkdir();
		}
		
		boolean firstTime=false;
		
		if(!configfile.exists())
		{
			try
			{
				configfile.createNewFile();
			}
			catch(Exception e)
			{
				
			}
			firstTime=true;
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(configfile);
		
		if(firstTime==true)
		{
			config.set("Backups", true);
			config.set("NoRegCombat", true);
			config.set("NoNetherPortals", true);
			config.set("Loginz", true);
			config.set("SignColors", true);
			config.set("AdminState", true);
			config.set("AdminChat", true);
			config.set("HeroTips", true);
			config.set("DisabledActions", true);
			config.set("AntiPvPLog", true);
			config.set("BPermsSimplified", true);
			config.set("Invis", true);
			config.set("HeroExpBoost", true);
			config.set("Itemz", true);
			config.set("ScoreboardBars", true);
			config.set("LevelUp", true);
			config.set("PlayerHeads", true);
			config.set("SafezoneNoSkills", true);
			try
			{
			config.save(configfile);
			}
			catch(Exception e)
			{
				
			}
		}
		
		if(config.getBoolean("Backups")==true)
		{
		Backups.setupBackups();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] Backups disabled.");
		}
		
		if(heroes!=null && config.getBoolean("NoRegCombat")==true)
		{
			cs.sendMessage("[SmudgeEssentials] NoRegCombat enabled.");
			NoRegCombat.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] NoRegCombat disabled.");
		}
		
		if(config.getBoolean("Loginz")==true)
		{
			cs.sendMessage("[SmudgeEssentials] Loginz enabled.");
			Loginz.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] Loginz disabled.");
		}
		
		if(config.getBoolean("SignColors")==true)
		{
			cs.sendMessage("[SmudgeEssentials] SignColors enabled.");
			SignColors.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] SignColors disabled.");
		}
		
		if(config.getBoolean("AdminState")==true && factions!=null)
		{
			cs.sendMessage("[SmudgeEssentials] AdminState enabled.");
			AwayCommand.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] AdminState disabled.");
		}
		
		if(config.getBoolean("AdminChat")==true)
		{
			cs.sendMessage("[SmudgeEssentials] AdminChat enabled.");
			AcCommand.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] AdminChat disabled.");
		}
		
		if(config.getBoolean("HeroTips")==true)
		{
			cs.sendMessage("[SmudgeEssentials] HeroTips enabled.");
			HeroTips.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] HeroTips disabled.");
		}
		
		if(config.getBoolean("DisabledActions")==true)
		{
			cs.sendMessage("[SmudgeEssentials] DisabledActions enabled.");
			DisabledActions.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] DisabledActions disabled.");
		}
		
		if(config.getBoolean("AntiPvPLog")==true && heroes!=null)
		{
			cs.sendMessage("[SmudgeEssentials] AntiPvPLog enabled.");
			AntiPvPLog.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] AntiPvPLog disabled.");
		}
		
		if(config.getBoolean("BPermsSimplified")==true && bperms!=null)
		{
			cs.sendMessage("[SmudgeEssentials] BPermsSimplified enabled.");
			BPermsSimplified.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] BPermsSimplified disabled.");
		}
		
		if(config.getBoolean("Invis")==true)
		{
			cs.sendMessage("[SmudgeEssentials] Invis enabled.");
			Invis.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] Invis disabled.");
		}
		
		if(config.getBoolean("HeroExpBoost")==true && heroes!=null)
		{
			cs.sendMessage("[SmudgeEssentials] HeroExpBoost enabled.");
			HeroExpBoost.init();
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] HeroExpBoost disabled.");
		}
		
		if(config.getBoolean("Itemz")==true)
		{
			Itemz.init();
			cs.sendMessage("[SmudgeEssentials] Itemz enabled.");
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] Itemz disabled.");
		}
		
		if(config.getBoolean("ScoreboardBars")==true && heroes!=null)
		{
			ScoreboardBars.init();
			cs.sendMessage("[SmudgeEssentials] ScoreboardBars enabled.");
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] ScoreboardBars disabled.");
		}
		
		if(config.getBoolean("LevelUp")==true && heroes!=null)
		{
			LevelUp.init();
			cs.sendMessage("[SmudgeEssentials] LevelUp enabled.");
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] LevelUp disabled.");
		}
		
		if(config.getBoolean("PlayerHeads")==true)
		{
			PlayerHeads.init();
			cs.sendMessage("[SmudgeEssentials] PlayerHeads enabled.");
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] PlayerHeads disabled.");
		}
		
		if(config.getBoolean("SafezoneNoSkills")==true && factions!=null)
		{
			SafezoneNoSkills.init();
			cs.sendMessage("[SmudgeEssentials] SafezoneNoSkills enabled.");
		}
		else
		{
			cs.sendMessage("[SmudgeEssentials] SafezoneNoSkills disabled.");
		}
		
		plugin.getCommand("note").setExecutor(new NoteCommand());
	}
}

package net.smudgecraft.smudgeessentials.backups;

import java.io.File;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;
import net.smudgecraft.smudgeessentials.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Backup 
{
	private boolean isFolder;
	private int hours;
	private int minutes;
	private int seconds;
	private String fromDir;
	private String name;
	private boolean isDisabled;
	private int scheduleId;
	private boolean isScheduled;
	
	public Backup(String name, String fromDir, int hours, int minutes, int seconds, boolean isFolder)
	{
		this.name=name;
		this.fromDir=fromDir;
		this.hours=hours;
		this.minutes=minutes;
		this.seconds=seconds;
		this.isFolder=isFolder;
		this.isDisabled=false;
	}
	
	public void init()
	{
		if(this.isFolder==true)
		{
		
		this.isScheduled=true;	
		
		scheduleId = Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, new Runnable()
		{
			public void run()
			{
				ConsoleCommandSender cs = Bukkit.getConsoleSender();
				
				File folder = new File(SmudgeEssentials.plugin.getDataFolder() + "/SmudgeBackups/" + name);
				
				if(!folder.exists())
				{
					cs.sendMessage("[SmudgeEssentials]: Creating folder for backup...");
					folder.mkdir();
					cs.sendMessage("[SmudgeEssentials]: Folder created!");
				}
				
				try
				{
				cs.sendMessage("[SmudgeEssentials] Beginning backing up \"" + name + "\"");
				Util.zipFolder(new File(new File("").getAbsolutePath() + fromDir).getAbsolutePath(), SmudgeEssentials.plugin.getDataFolder() + "/SmudgeBackups/" + name + "/" + name + "_" + Util.getDateFormat() + ".zip");
				cs.sendMessage("[SmudgeEssentials] \"" + name + "\" backed up!");
				scheduleId = Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, this, (20L*60*60*hours + 20L*60*minutes + 20L*seconds));
				}
				catch(Exception e)
				{
				cs.sendMessage("[SmudgeEssentials] Backup failed!");
				e.printStackTrace();
				scheduleId = Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, this, (20L*60*10));
				}
			}
		}, (20L*60*60*hours + 20L*60*minutes + 20L*seconds));
		}
		else
		{
			
			this.isScheduled=true;
			
			scheduleId = Bukkit.getScheduler().scheduleSyncRepeatingTask(SmudgeEssentials.plugin, new Runnable()
			{
				public void run()
				{
					ConsoleCommandSender cs = Bukkit.getConsoleSender();
					
					File folder = new File(SmudgeEssentials.plugin.getDataFolder() + "/SmudgeBackups/" + name);
					
					if(!folder.exists())
					{
						cs.sendMessage("[SmudgeEssentials] Creating folder for backup...");
						folder.mkdir();
						cs.sendMessage("[SmudgeEssentials] Folder created!");
					}
					
					try
					{
					cs.sendMessage("[SmudgeEssentials] Beginning backing up \"" + name + "\"");
					Util.zipFile(new File(new File("").getAbsolutePath() + fromDir).getAbsolutePath(), SmudgeEssentials.plugin.getDataFolder() + "/SmudgeBackups/" + name + "/" + name + "_" + Util.getDateFormat() + ".zip");
					cs.sendMessage("[SmudgeEssentials] \"" + name + "\" backed up!");
					scheduleId = Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, this, (20L*60*60*hours + 20L*60*minutes + 20L*seconds));
					}
					catch(Exception e)
					{
					cs.sendMessage("[SmudgeEssentials] Backup failed! Postponing 10 minutes");
					e.printStackTrace();
					scheduleId = Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, this, (20L*60*10));
					}
				}
			}, (20L*60*60*hours + 20L*60*minutes), (20L*60*60*hours + 20L*60*minutes + 20L*seconds));
		}
	}
	
	public String getBackupName()
	{
		return this.name;
	}
	
	public int getScheduleId()
	{
		return this.scheduleId;
	}
	
	public boolean isFileBackup()
	{
		if(this.isFolder==true)
			return false;
		else
			return true;
	}
	
	public String getFromDirectory()
	{
		return this.fromDir;
	}
	
	public boolean isDisabled()
	{
		return this.isDisabled;
	}
	
	public boolean isFolderBackup()
	{
		return this.isFolder;
	}
	
	public int getPeriodHours()
	{
		return this.hours;
	}
	
	public int getPeriodMinutes()
	{
		return this.minutes;
	}
	
	public int getPeriodSeconds()
	{
		return this.seconds;
	}
	
	public boolean isScheduled()
	{
		return this.isScheduled;
	}
}

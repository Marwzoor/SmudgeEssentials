package net.smudgecraft.smudgeessentials.backups;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Backups 
{
	private static List<Backup> backups;
	
	public static void setupBackups()
	{
		File backupFolder = new File(SmudgeEssentials.plugin.getDataFolder() + "/SmudgeBackups");
		
		if(!backupFolder.exists())
		{
			backupFolder.mkdir();
		}
		
		ConsoleCommandSender cs = Bukkit.getServer().getConsoleSender();
		
		cs.sendMessage("[SmudgeEssentials] Initializing backups..."); 
		
		FileConfiguration config = setBackupConfig();
		
		if(config==null) { cs.sendMessage("[SmudgeEssentials] Backup initialize failed! Some problems with backups.yml..."); return; }
		
		if(!config.contains("Backups")) { return; }
		
		backups = new ArrayList<Backup>();
		
		for(String s : config.getStringList("Backups"))
		{
			int hours = config.getInt(s + ".Period.Hours");
			int minutes = config.getInt(s + ".Period.Minutes");
			int seconds = config.getInt(s + ".Period.Seconds");
			String fromDir = config.getString(s + ".Folder");
			String name = config.getString(s + ".Name");
			boolean isFolder = config.getBoolean(s + ".isFolder");
			
			Backup backup = new Backup(name, fromDir, hours, minutes, seconds, isFolder);
			
			backups.add(backup);
			
			backup.init();
		}
		
		cs.sendMessage("[SmudgeEssentials] Backups initialized!");
	}
	
	public static void clearBackups()
	{
		for(Backup backup : backups)
		{
			if(backup.isScheduled())
			{
				Bukkit.getScheduler().cancelTask(backup.getScheduleId());
				
				backups.remove(backup);
			}
		}
	}
	
	public static void reloadBackups()
	{
		clearBackups();
		
		setupBackups();
	}
	
	public static FileConfiguration setBackupConfig()
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/backups.yml");
		
		boolean couldCreate=false;
		boolean firstTime=false;
		
		if(!file.exists())
		{
		try
		{
		couldCreate = file.createNewFile();
		firstTime=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
		else
		{
		couldCreate = true;
		}
		
		if(couldCreate==false) { return null; }
		
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if(firstTime)
		{
		List<String> backups = new ArrayList<String>();
		backups.add("Default1");
		backups.add("Default2");
		
		config.set("Backups", backups);
		
		config.set("Default1.Period.Hours", 1);
		config.set("Default1.Period.Minutes", 20);
		config.set("Default1.Period.Seconds", 10);
		config.set("Default1.Folder", "/plugins/SmudgeEssentials");
		config.set("Default1.Name", "DefaultFolderBackup");
		config.set("Default1.isFolder", true);
		
		config.set("Default2.Period.Hours", 1);
		config.set("Default2.Period.Minutes", 20);
		config.set("Default2.Period.Seconds", 10);
		config.set("Default2.Folder", "/plugins/SmudgeEssentials/backups.yml");
		config.set("Default2.Name", "DefaultFileBackup");
		config.set("Default2.isFolder", false);
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		return config;
	}
	
	public static List<Backup> getBackups()
	{
		return backups;
	}
}

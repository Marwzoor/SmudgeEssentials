package net.smudgecraft.smudgeessentials.loginz;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet250CustomPayload;
import net.smudgecraft.smudgeessentials.SmudgeEssentials;
import org.bukkit.craftbukkit.entity.CraftPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class LoginListener implements Listener
{
	public LoginListener()
	{
	}
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		String url = Loginz.getConfig().getString("servertextureurl");
		
		EntityPlayer victim = ((CraftPlayer)event.getPlayer()).getHandle();
		victim.playerConnection.sendPacket(getTexturePackChangePacket(url));
		
		if(!event.getPlayer().hasPlayedBefore())
		{
			firstTime(event);
		}
		
		if(!event.getPlayer().getName().equalsIgnoreCase("Marwzoor") && !event.getPlayer().getName().equalsIgnoreCase("Deminth"))
		{
		String ip = event.getPlayer().getAddress().getAddress().getHostAddress();
		
		for(Player player : Bukkit.getOnlinePlayers())
		{
			if(player.hasPermission("loginz.ip") || player.isOp())
			{
				player.sendMessage(ChatColor.GREEN + "Player " + ChatColor.AQUA + event.getPlayer().getName() + ChatColor.GREEN + " connected with the ip: " + ChatColor.GOLD + ip);
			}
		}
		}
		
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/loginz.yml");
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if(config.getInt("HighestPlayers")>=Bukkit.getServer().getOnlinePlayers().length)
			return;
		else if(config.getInt("HighestPlayers")<Bukkit.getServer().getOnlinePlayers().length)
		{
			config.set("HighestPlayers", Bukkit.getServer().getOnlinePlayers().length);
			Player[] players = Bukkit.getServer().getOnlinePlayers();
			TimeZone tz = TimeZone.getTimeZone("GMT+1");
			Calendar cal = Calendar.getInstance(tz);
			String date = "";
			String time = "";
			int second = cal.get(Calendar.SECOND);
			int minute = cal.get(Calendar.MINUTE);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			month++;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			date = Integer.toString(year) + "-";
			date += Integer.toString(month) + "-";
			date += Integer.toString(day);
			time = Integer.toString(hour) + ":";
			time += Integer.toString(minute) + ":";
			if(second<10)
			{
			time += "0" + Integer.toString(second);
			}
			else
			{
				time += Integer.toString(second);
			}
			config.set("Date.Date", date);
			config.set("Date.Time", time);
			String onlineplayers = "";
			for(int i=0;i<players.length;++i)
			{
				if(i==0)
				{
					onlineplayers = "&a" + players[i].getName();
				}
				else
				{
					onlineplayers += "&b" + ", " + "&a" + players[i].getName(); 
				}
			}
			config.set("OnlinePlayersAtTime", onlineplayers);
			try
			{
			config.save(file);
			}
			catch(Exception e)
			{
			Bukkit.getConsoleSender().sendMessage("[SmudgeEssentials] Could not save loginz.yml!");
			}
		}
	}
	@EventHandler
	public void onPlayerPreLoginEvent(PlayerLoginEvent event)
	{
		Player player = event.getPlayer();
		if(Loginz.isMaintainance==true)
		{
			if(player.isOp()==false || player.hasPermission("loginz.maintainance"))
			{
				event.disallow(Result.KICK_OTHER, ChatColor.YELLOW + "Maintainance");
			}
		}
		
	}
	
	public Packet250CustomPayload getTexturePackChangePacket(String url) 
	{
		 
		Packet250CustomPayload packet = new Packet250CustomPayload();
		 
		int size = 16;
		String message = url + "\u0000" + size; //build the data
		 
		byte[] data= message.getBytes();
		 
		packet.data = data;
		packet.length = data.length;
		packet.lowPriority = false;
		packet.tag = "MC|TPack"; //set the channel
		 
		return packet;
		 
	}
	
	public void firstTime(PlayerJoinEvent event)
	{
		final File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/loginz.yml");
		
		if(file.exists())
		{
		
		final Player player = event.getPlayer();
			
		final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			
		Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, new Runnable()
		{
			public void run()
			{
				if(config.getStringList("FirstTimeCommandsServer")!=null)
				{
				List<String> commands = config.getStringList("FirstTimeCommandsServer");
				
				for(String s : commands)
				{
					String command = s.replaceAll("%p", player.getName());
					
					SmudgeEssentials.plugin.getServer().dispatchCommand(SmudgeEssentials.plugin.getServer().getConsoleSender(), command);
					}
				}
				else
				{
					List<String> defaultCommands = new ArrayList<String>();
					
					defaultCommands.add("warp tutorial %p");
					
					config.set("FirstTimeCommandsServer", defaultCommands);
					
					try
					{
					config.save(file);
					}
					catch(Exception e)
					{
						
					}
				}
				if(config.getStringList("FirstTimeCommandsPlayer")!=null)
				{
					List<String> commands = config.getStringList("FirstTimeCommandsPlayer");
					
					for(String s : commands)
					{
						String command = s.replaceAll("%p", player.getName());
						
						SmudgeEssentials.plugin.getServer().dispatchCommand(player, command);
					}
				}
				else
				{
					List<String> defaultCommands = new ArrayList<String>();
					
					defaultCommands.add("hero profession choose handyman");
					defaultCommands.add("hero confirm");
					
					config.set("FirstTimeCommandsPlayer", defaultCommands);
					
					try
					{
					config.save(file);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}, 5L);	
		
	}
	}
	
	public static String mergeStringArray(String[] array)
	{
		String merged = "";
		
		for(int i=0;i<array.length;++i)
		{
			if(i!=array.length-1)
			{
			merged += array[i] + " ";
			}
			else
			{
			merged += array[i];
			}
		}
		
		return merged;
	}
}

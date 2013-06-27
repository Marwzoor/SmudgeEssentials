package net.smudgecraft.smudgeessentials.scoreboardbars;

import java.util.HashMap;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.herocraftonline.heroes.characters.Hero;

public class ScoreboardBars
{
	public static HashMap<Player, Scoreboard> sbplayers;
	
	public static void init()
	{
		sbplayers = new HashMap<Player, Scoreboard>();

		Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), SmudgeEssentials.plugin);
	}
	
	
	public static String createHealthBar(Player player)
	{
		double maxhealth = player.getMaxHealth();
		double health = player.getHealth();
		
		double amount = health/maxhealth;
		
		amount = amount*10;
		
		int am = (int) amount;
		
		String message = "";
		
		for(int i=1;i<11;++i)
		{
			if(i<am)
			{
				message += ChatColor.GREEN + "█";
			}
			else
			{
				message += ChatColor.RED + "█";
			}
		}
		
		return message;
	}
	
	public static String createManaBar(Player player)
	{
		Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
		
		double maxmana = hero.getMaxMana();
		double mana = hero.getMana();
		
		double amount = mana/maxmana;
		
		amount = amount*10;
		
		int am = (int) amount;
		
		String message = "";
		
		for(int i=1;i<11;++i)
		{
			if(i<am)
			{
				message += ChatColor.BLUE + "█";
			}
			else
			{
				message += ChatColor.GRAY + "█";
			}
		}
		
		return message;
	}
	
	public static Scoreboard getScoreboard(Player player)
	{
		if(sbplayers.containsKey(player))
		{
		return sbplayers.get(player);
		}
		return null;
	}
	
	public static boolean hasScoreboard(Player player)
	{
		if(sbplayers.containsKey(player))
		{
			return true;
		}
		return false;
	}
	
	public static void setScoreboard(Player player)
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		
		Scoreboard sb = manager.getNewScoreboard();
		
		Objective hbar = sb.registerNewObjective("healthbar", "dummy");
		
		Objective mbar = sb.registerNewObjective("manabar", "dummy");
		
		hbar.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		mbar.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		hbar.setDisplayName(createHealthBar(player));
		
		mbar.setDisplayName(createManaBar(player));
		
		player.setScoreboard(sb);
		
		player.sendMessage(createHealthBar(player));
		
		player.sendMessage(createManaBar(player));
		
		sbplayers.put(player, sb);
	}
	
	public static void updateScoreboard(Player player)
	{
		if(hasScoreboard(player))
		{
			Scoreboard sb = getScoreboard(player);

			Objective hbar = sb.getObjective("healthbar");
			
			Objective mbar = sb.getObjective("manabar");
			
			hbar.setDisplayName(createHealthBar(player));
			
			mbar.setDisplayName(createManaBar(player));
			
			player.setScoreboard(sb);
			
			sbplayers.put(player, sb);
		}
	}
	
	public static void removeScoreboard(Player player)
	{
		if(hasScoreboard(player))
		{
			sbplayers.remove(player);
		}
	}
}

package net.smudgecraft.smudgeessentials.scoreboardbars;

import java.util.HashMap;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.entity.Damageable;

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
		double maxhealth = ((Damageable)player).getMaxHealth();
		double health = ((Damageable)player).getHealth();
		
		double amount = health/maxhealth;
		
		amount = amount*5;
		
		int am = (int) amount;
		
		String message = "";
		
		for(int i=1;i<=6;++i)
		{
			if(i<am)
			{
				message += ChatColor.GREEN + "|";
			}
			else
			{
				message += ChatColor.RED + "|";
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
		
		amount = amount*5;
		
		int am = (int) amount;
		
		String message = "";
		
		for(int i=1;i<6;++i)
		{
			if(i<=am)
			{
				message += ChatColor.BLUE + "|";
			}
			else
			{
				message += ChatColor.GRAY + "|";
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
		Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		
		Scoreboard sb = manager.getNewScoreboard();
		
		Objective stats = sb.registerNewObjective("stats", "dummy");
		
		stats.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		stats.setDisplayName(ChatColor.GOLD + player.getName() + "'s Stats" + ChatColor.BLUE);
				
		Score mbar = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Mana"));
		mbar.setScore(hero.getMana());
		
		Score hbar = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Health"));
		hbar.setScore((int)((Damageable)player).getHealth());
		
		Score primaryclass = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + hero.getHeroClass().getName()));
		primaryclass.setScore(hero.getLevel(hero.getHeroClass()));
		
		if(hero.getSecondClass()!=null)
		{
		Score profession = stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + hero.getSecondClass().getName()));
		profession.setScore(hero.getLevel(hero.getSecondClass()));
		}
				
		player.setScoreboard(sb);
		
		sbplayers.put(player, sb);
	}
	
	/*public static void updateScoreboard(Player player)
	{
		if(hasScoreboard(player))
		{
			Scoreboard sb = getScoreboard(player);

			Objective stats = sb.getObjective("stats");
			
			
			
			player.setScoreboard(sb);
			
			sbplayers.put(player, sb);
		}
	}*/
	
	public static void removeScoreboard(Player player)
	{
		if(hasScoreboard(player))
		{
			sbplayers.remove(player);
		}
	}
}

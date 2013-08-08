package net.smudgecraft.smudgeessentials.scoreboardbars;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.herocraftonline.heroes.api.events.CharacterDamageEvent;
import com.herocraftonline.heroes.api.events.ClassChangeEvent;
import com.herocraftonline.heroes.api.events.HeroChangeLevelEvent;
import com.herocraftonline.heroes.api.events.HeroRegainManaEvent;
import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.SkillUseEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;

public class ScoreboardListener implements Listener
{
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerRegainHealthEvent(EntityRegainHealthEvent event)
	{
		if(event.isCancelled())
			return;
		
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();

			if(((Damageable)player).getHealth()+event.getAmount()>((Damageable)player).getMaxHealth())
			{
				updateHealth(player, ((Damageable)player).getMaxHealth());
			}
			else
			{
				updateHealth(player, ((Damageable)player).getHealth() + event.getAmount());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamageEvent(CharacterDamageEvent event)
	{
		if(event.isCancelled())
			return;
		
		if(event.getEntity() instanceof Player)
		{	
			Player player = (Player) event.getEntity();
			
			if(((Damageable)player).getHealth()-event.getDamage()<0)
			{
				updateHealth(player, 0);
			}
			else
			{
				updateHealth(player, ((Damageable)player).getHealth()-event.getDamage());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onWeaponDamageEvent(WeaponDamageEvent event)
	{
		if(event.isCancelled())
			return;
		
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			
			if(((Damageable)player).getHealth()-event.getDamage()<0)
			{
				updateHealth(player, 0);
			}
			else
			{
				updateHealth(player, ((Damageable)player).getHealth()-event.getDamage());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onSkillDamageEvent(SkillDamageEvent event)
	{
		if(event.isCancelled())
			return;
		
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			
			if(((Damageable)player).getHealth()-event.getDamage()<0)
			{
				updateHealth(player, 0);
			}
			else
			{
				updateHealth(player, ((Damageable)player).getHealth()-event.getDamage());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerRespawnEvent(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		
		updateHealth(player, ((Damageable)player).getMaxHealth());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onHeroChangeLevelEvent(HeroChangeLevelEvent event)
	{
		if(event.getHeroClass().isPrimary())
		{
			updatePrimaryClass(event.getHero().getPlayer(), event.getTo());
		}
		else if(event.getHeroClass().isSecondary())
		{
			updateSecondaryClass(event.getHero().getPlayer(), event.getTo());
		}
		
		final Player player = event.getHero().getPlayer();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, new Runnable()
		{
			public void run()
			{
				Player p = Bukkit.getPlayer(player.getName());
				if(p!=null)
				{
				updateHealth(p, ((Damageable)p).getHealth());
				updateMana(p, SmudgeEssentials.heroes.getCharacterManager().getHero(p).getMana());
				}
			}
		}, 1L);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onHeroChangeClassEvent(ClassChangeEvent event)
	{
		if(event.isCancelled())
			return;
		
		HeroClass from = event.getFrom();
		HeroClass to = event.getTo();
		
		if(from!=null)
		{
			if(from.isPrimary())
			{
				changePrimaryClass(event.getHero().getPlayer(), from, to);
			}
			else if(from.isSecondary())
			{
				changeSecondaryClass(event.getHero().getPlayer(), from, to);
			}
		}
		else
		{
			if(to.isPrimary())
			{
				changePrimaryClass(event.getHero().getPlayer(), to);
			}
			else if(to.isPrimary())
			{
				changeSecondaryClass(event.getHero().getPlayer(), to);
			}
		}
		
		final Player player = event.getHero().getPlayer();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, new Runnable()
		{
			public void run()
			{
				Player p = Bukkit.getPlayer(player.getName());
				if(p!=null)
				{
				updateHealth(p, ((Damageable)p).getHealth());
				updateMana(p, SmudgeEssentials.heroes.getCharacterManager().getHero(p).getMana());
				}
			}
		}, 10L);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onHeroRegainManaEvent(HeroRegainManaEvent event)
	{
		if(event.isCancelled())
			return;
		
		if(ScoreboardBars.hasScoreboard(event.getHero().getPlayer()))
		{
			if(event.getAmount()+event.getHero().getMana()>event.getHero().getMaxMana())
			{
				updateMana(event.getHero().getPlayer(), event.getHero().getMaxMana());
			}
			else
			{
				updateMana(event.getHero().getPlayer(), event.getHero().getMana()+event.getAmount());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onSkillUseEvent(SkillUseEvent event)
	{
		if(event.isCancelled())
			return;

		if(event.getHero().getMana()-event.getManaCost()<0)
		{
			updateMana(event.getHero().getPlayer(), 0);	
		}
		else
		{
			updateMana(event.getHero().getPlayer(), event.getHero().getMana()-event.getManaCost());
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		ScoreboardBars.setScoreboard(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event)
	{
		ScoreboardBars.removeScoreboard(event.getPlayer());
	}
	
	public void updateMana(Player player, int mana)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
			
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Mana")).setScore(mana);
		}
	}
	
	public void updateHealth(Player player, double health)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
			
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Health")).setScore((int)health);
		}
	}
	
	public void updatePrimaryClass(Player player, int level)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
			
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
			
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + hero.getHeroClass().getName())).setScore(level);
		}
	}
	
	public void changePrimaryClass(Player player, HeroClass to)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
			
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
						
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + to.getName())).setScore(hero.getLevel(to));
		}
	}
	
	public void changePrimaryClass(Player player, HeroClass from, HeroClass to)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
			
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
			
			sb.resetScores(Bukkit.getOfflinePlayer(ChatColor.AQUA + from.getName()));
			
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + to.getName())).setScore(hero.getLevel(to));
		}
	}
	
	public void changeSecondaryClass(Player player, HeroClass to)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
			
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
						
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + to.getName())).setScore(hero.getLevel(to));
		}
	}
	
	public void changeSecondaryClass(Player player, HeroClass from, HeroClass to)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
			
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
			
			sb.resetScores(Bukkit.getOfflinePlayer(ChatColor.AQUA + from.getName()));
			
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + to.getName())).setScore(hero.getLevel(to));
		}
	}
	
	public void updateSecondaryClass(Player player, int level)
	{
		if(ScoreboardBars.hasScoreboard(player))
		{
			Hero hero = SmudgeEssentials.heroes.getCharacterManager().getHero(player);
			
			Scoreboard sb = ScoreboardBars.getScoreboard(player);
			
			Objective stats = sb.getObjective("stats");
			
			stats.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + hero.getSecondClass().getName())).setScore(level);
		}
	}
}

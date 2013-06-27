package net.smudgecraft.smudgeessentials.playerheads;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeads implements CommandExecutor, Listener
{
	public static void init()
	{
		SmudgeEssentials.plugin.getCommand("phead").setExecutor(new PlayerHeads());
		
		Bukkit.getPluginManager().registerEvents(new PlayerHeads(), SmudgeEssentials.plugin);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("phead") && sender instanceof Player && args.length>0 && sender.isOp())
		{
			Player player = (Player) sender;
			
			String tname = args[0];
			
			ItemStack is = new ItemStack(Material.SKULL_ITEM, 1);
			SkullMeta sm = (SkullMeta) is.getItemMeta();
			
			sm.setOwner(tname);
			is.setDurability((short) 3);
			
			is.setItemMeta(sm);
			
			player.getInventory().addItem(is);
			
			player.sendMessage(ChatColor.GOLD + "You have recieved the head of " + ChatColor.YELLOW + tname + ChatColor.GOLD + "!");
		}
		else if(commandLabel.equalsIgnoreCase("phead") && args.length==0 && sender instanceof Player)
		{
			Player player = (Player) sender;
			
			player.sendMessage(ChatColor.RED + "You have to enter player name!");
			player.sendMessage(ChatColor.BLUE + "Command usage: " + ChatColor.GREEN + "/phead <playername>");
		}
		return true;
	}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			
			if(player.getKiller() instanceof Player)
			{
				ItemStack is = new ItemStack(Material.SKULL_ITEM, 1);
				SkullMeta sm = (SkullMeta) is.getItemMeta();
				
				sm.setOwner(player.getName());
				is.setDurability((short) 3);
				
				is.setItemMeta(sm);
				
				player.getWorld().dropItemNaturally(player.getLocation(), is);
			}
		}
	}
}

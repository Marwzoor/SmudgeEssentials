package net.smudgecraft.smudgeessentials.itemz;

import java.util.ArrayList;
import java.util.List;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Itemz implements CommandExecutor
{
	
	public static void init()
	{
		SmudgeEssentials.plugin.getCommand("itemz").setExecutor(new Itemz());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if(commandLabel.equalsIgnoreCase("itemz") && args.length>0)
			{
				if(player.isOp())
				{
					if(player.getItemInHand()!=null)
					{
						if(player.getItemInHand().getType()!=Material.AIR)
						{
							if(args[0].equalsIgnoreCase("name"))
							{
								if(args[1].equalsIgnoreCase("set") && args.length>2)
								{
									String name = arrayToString(args, 2);
							
									name = name.replace("&", "§");
							
									ItemStack is = player.getItemInHand();
									
									ItemMeta im = is.getItemMeta();
									
									im.setDisplayName(name);
									
									is.setItemMeta(im);
									
									player.setItemInHand(is);
									
									player.sendMessage(ChatColor.GOLD + "Name of item in hand has been set.");
								}
								else if(args[1].equalsIgnoreCase("reset"))
								{
									ItemStack is = player.getItemInHand();
									
									ItemMeta im = is.getItemMeta();
									
									im.setDisplayName(new ItemStack(is.getType()).getItemMeta().getDisplayName());
									
									player.setItemInHand(is);
									
									player.sendMessage(ChatColor.GOLD + "Name of item in hand has been reset.");

								}
							}
							else if(args[0].equalsIgnoreCase("lore"))
							{
								if(args[1].equalsIgnoreCase("set") && args.length>2)
								{
									String title = arrayToString(args, 2);
									
								    title = title.replace("&", "§");
									
									ItemStack is = player.getItemInHand();
									
									List<String> lore = new ArrayList<String>();
									
									lore.add(title);
									
									ItemMeta im = is.getItemMeta();
									
									im.setLore(lore);
									
									is.setItemMeta(im);
									
									player.setItemInHand(is);
									
									player.sendMessage(ChatColor.GOLD + "Lore of the item in hand has been set.");

								}
								else if(args[1].equalsIgnoreCase("add") && args.length>2)
								{
									String title = arrayToString(args, 2);
									
									title = title.replace("&", "§");
									
									ItemStack is = player.getItemInHand();
									
									ItemMeta im = is.getItemMeta();
									
									List<String> lore = new ArrayList<String>();
									
									lore.add(title);
									
									for(String s : im.getLore())
									{
										lore.add(s);
									}
									
									im.setLore(lore);
									
									is.setItemMeta(im);
									
									player.setItemInHand(is);
									
									player.sendMessage(ChatColor.GOLD + "Lore of the item in hand has been added.");

								}
								else if(args[1].equalsIgnoreCase("remove"))
								{
									List<String> lore = new ArrayList<String>();
									
									ItemStack is = player.getItemInHand();
									
									ItemMeta im = is.getItemMeta();
									
									im.setLore(lore);
									
									is.setItemMeta(im);
									
									player.setItemInHand(is);
									
									player.sendMessage(ChatColor.GOLD + "Lore of the item in hand has been removed.");

								}
							}
						}
					}
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String arrayToString(String[] args, int startfrom)
	{
		String s = "";
		
		for(int i=startfrom;i<args.length;++i)
		{
			s += args[i] + " ";
		}
		
		return s;
	}
}

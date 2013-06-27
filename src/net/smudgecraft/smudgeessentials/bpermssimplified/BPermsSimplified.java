package net.smudgecraft.smudgeessentials.bpermssimplified;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.Calculable;
import de.bananaco.bpermissions.api.CalculableType;
import de.bananaco.bpermissions.api.GroupCarrier;
import de.bananaco.bpermissions.api.WorldManager;

public class BPermsSimplified implements CommandExecutor
{
		public final WorldManager instance = WorldManager.getInstance();
		
		public static void init()
		{
			SmudgeEssentials.plugin.getCommand("groups").setExecutor(new BPermsSimplified());
			SmudgeEssentials.plugin.getCommand("prefix").setExecutor(new BPermsSimplified());
			SmudgeEssentials.plugin.getCommand("suffix").setExecutor(new BPermsSimplified());
		}
		
		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
		{
			if(sender instanceof ConsoleCommandSender || sender.isOp())
			{
			if(commandLabel.equalsIgnoreCase("groups") && args.length>3)
			{
				if(args[0].equalsIgnoreCase("add"))
				{
				String pname = args[1];
				
				String gname = args[2];
				
				String wname = args[3];
				
				boolean isWorld=false;
				
				for(World w : Bukkit.getWorlds())
				{
					if(w.getName().equalsIgnoreCase(wname))
					{
						isWorld=true;
						continue;
					}
				}
				
				if(isWorld)
				{
					ApiLayer.addGroup(wname, CalculableType.USER, pname, gname);
					sender.sendMessage(ChatColor.GREEN + "User " + ChatColor.AQUA + pname + ChatColor.GREEN + " added to group " + ChatColor.AQUA + gname + ChatColor.GREEN + " in world " + ChatColor.AQUA + wname + ChatColor.GREEN + ".");
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "That is not a valid world name!");
				}
				}
				else if(args[0].equalsIgnoreCase("remove"))
				{
					String pname = args[1];
					
					String gname = args[2];
					
					String wname = args[3];
					
					boolean isWorld=false;
					
					for(World w : Bukkit.getWorlds())
					{
						if(w.getName().equalsIgnoreCase(wname))
						{
							isWorld=true;
							continue;
						}
					}
					
					if(isWorld)
					{
						ApiLayer.removeGroup(wname, CalculableType.USER, pname, gname);
						sender.sendMessage(ChatColor.GREEN + "User " + ChatColor.AQUA + pname + ChatColor.GREEN + " removed from group " + ChatColor.AQUA + gname + ChatColor.GREEN + " in world " + ChatColor.AQUA + wname + ChatColor.GREEN + ".");
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That is not a valid world name!");
					}
				}
			}
			else if(commandLabel.equalsIgnoreCase("prefix") && args.length>0)
			{
				if(args[0].equalsIgnoreCase("set") && args.length>2)
				{
					String pname = args[1];
					
					String wname = args[2];
					
					String prefix = argsToString(args);
					
					boolean isWorld=false;
					
					for(World w : Bukkit.getWorlds())
					{
						if(w.getName().equalsIgnoreCase(wname))
						{
							isWorld=true;
							continue;
						}
					}
					
					if(isWorld)
					{				
						ApiLayer.setValue(wname, CalculableType.USER, pname, "prefix", prefix);
						sender.sendMessage(ChatColor.GREEN + "User " + ChatColor.AQUA + pname + ChatColor.GREEN + " prefix is now set to " + ChatColor.AQUA + prefix + ChatColor.GREEN + " in world " + ChatColor.AQUA + wname + ChatColor.GREEN + ".");
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That is not a valid world name!");
					}
				}
				else if(args[0].equalsIgnoreCase("remove") && args.length>2)
				{
					String pname = args[1];
					
					String wname = args[2];
					
					boolean isWorld=false;
					
					for(World w : Bukkit.getWorlds())
					{
						if(w.getName().equalsIgnoreCase(wname))
						{
							isWorld=true;
							continue;
						}
					}
					
					if(isWorld)
					{
						de.bananaco.bpermissions.api.World world = instance.getWorld(wname);
						
						Calculable c = world.get(pname, CalculableType.USER);
						
						int previous = 0;
						
						GroupCarrier group = null;
						
						for(GroupCarrier g : c.getGroups())
						{
							if(g.getPriority()>=previous)
							{
								group=g;
								previous=g.getPriority();
							}
						}
						
						if(group!=null)
						{
						ApiLayer.setValue(wname, CalculableType.USER, pname, "prefix", group.getValue("prefix"));
						
						c.getMeta().remove("prefix");
						
						sender.sendMessage(ChatColor.GREEN + "User " + ChatColor.AQUA + pname + ChatColor.GREEN + " prefix is now removed in world " + ChatColor.AQUA + wname + ChatColor.GREEN + ".");
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That is not a valid world name!");
					}
				}
			}
			else if(commandLabel.equalsIgnoreCase("suffix") && args.length>0)
			{
				if(args[0].equalsIgnoreCase("set") && args.length>2)
				{
					String pname = args[1];
					
					String wname = args[2];
					
					String suffix = argsToString(args);
					
					boolean isWorld=false;
					
					for(World w : Bukkit.getWorlds())
					{
						if(w.getName().equalsIgnoreCase(wname))
						{
							isWorld=true;
							continue;
						}
					}
					
					if(isWorld)
					{
						ApiLayer.setValue(wname, CalculableType.USER, pname, "suffix", suffix);
						sender.sendMessage(ChatColor.GREEN + "User " + ChatColor.AQUA + pname + ChatColor.GREEN + " suffix is now set to " + ChatColor.AQUA + suffix + ChatColor.GREEN + " in world " + ChatColor.AQUA + wname + ChatColor.GREEN + ".");
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That is not a valid world name!");
					}
				}
				else if(args[0].equalsIgnoreCase("remove") && args.length>2)
				{
					String pname = args[1];
					
					String wname = args[2];
					
					boolean isWorld=false;
					
					for(World w : Bukkit.getWorlds())
					{
						if(w.getName().equalsIgnoreCase(wname))
						{
							isWorld=true;
							continue;
						}
					}
					
					if(isWorld)
					{
						de.bananaco.bpermissions.api.World world = instance.getWorld(wname);
						
						Calculable c = world.get(pname, CalculableType.USER);
						
						int previous = 0;
						
						GroupCarrier group = null;
						
						for(GroupCarrier g : c.getGroups())
						{
							if(g.getPriority()>=previous)
							{
								group=g;
								previous=g.getPriority();
							}
						}
						
						if(group!=null)
						{
						ApiLayer.setValue(wname, CalculableType.USER, pname, "suffix", group.getValue("suffix"));
						
						c.getMeta().remove("suffix");
						
						sender.sendMessage(ChatColor.GREEN + "User " + ChatColor.AQUA + pname + ChatColor.GREEN + " suffix is now removed in world " + ChatColor.AQUA + wname + ChatColor.GREEN + ".");
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That is not a valid world name!");
					}
				}
			}
			}
			return true;
		}
		
		public static String argsToString(String[] args)
		{
			String str = "";
			
			for(int i=3;i<args.length;++i)
			{
				if(i==args.length-1)
				{
					str += args[i];
				}
				else
				{
					str += args[i] + " ";
				}
			}
			
			return str;
		}
}

package net.smudgecraft.smudgeessentials.disabledactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DisabledActionsCommands implements CommandExecutor
{
	public final static HashMap<World, Integer> removedPortals = new HashMap<World, Integer>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("nonether") && args.length>0)
		{
			if(args[0].equalsIgnoreCase("cleanup") && args.length==2)
			{
				if(sender instanceof Player)
				{
					Player player = (Player) sender;
					if(player.isOp())
					{
						String worldName = args[1];
				
						World world = Bukkit.getWorld(worldName);
				
						if(world==null)
						{
							player.sendMessage(ChatColor.AQUA + worldName + ChatColor.RED + " is not a valid worldname! (case-sensitive)");
							return true;
						}
								Chunk[] chunks = world.getLoadedChunks();
								
								int i=0;
								int x=0;
								int third=0;
								int amount=0;
								removedPortals.put(world, amount);
								
								double ticks = chunks.length/3;
								
								double seconds = ticks / 20;
								
								player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Estimated time of cleanup is: " + ChatColor.GOLD + seconds + ChatColor.AQUA + " seconds.");
								
								for(Chunk c : chunks)
								{
									++x;
									if(third==0)
									{
									++i;
									++third;
									}
									else if(third==1)
									{
									++third;
									}
									else if(third==2)
									{
									third=0;
									}
									final int y = x;
									final int length = chunks.length;
									final Chunk chunk = c;
									final Player p = player;
									Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, new Runnable()
									{
										public void run()
										{
										int bx = chunk.getX() << 4;
								        int bz = chunk.getZ() << 4;   
								        for (int xx = bx; xx < bx + 16; xx++) {
								            for (int zz = bz; zz < bz + 16; zz++) {
								                for (int yy = 0; yy < 128; yy++) {
								                    int typeId = chunk.getWorld().getBlockTypeIdAt(xx, yy, zz);
								                    if (typeId == 90) {
								                       chunk.getWorld().getBlockAt(xx, yy, zz).setType(Material.AIR);
								                       int temp = removedPortals.get(chunk.getWorld());
								                       ++temp;
								                       removedPortals.put(chunk.getWorld(), temp);
								                    }
								                }
								            }
								        }
								        if(y==length)
								        {
											p.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.GOLD + removedPortals.get(chunk.getWorld()) + ChatColor.AQUA + " Netherportals cleaned up.");
											removedPortals.remove(chunk.getWorld());
								        }
										}
									}, i * 1L);
								}
					}
				}
				else
				{
					String worldName = args[1];
					
					World world = Bukkit.getWorld(worldName);
			
					ConsoleCommandSender cs = Bukkit.getConsoleSender();
					
					if(world==null)
					{
						cs.sendMessage(ChatColor.AQUA + worldName + ChatColor.RED + " is not a valid worldname! (case-sensitive)");
						return true;
					}
					
						Chunk[] chunks = world.getLoadedChunks();
							
						int i=0;
						int x=0;
						int third=0;
						int amount=0;
						removedPortals.put(world, amount);
						
						double ticks = chunks.length/3;
						
						double seconds = ticks / 20;
						
						cs.sendMessage("[SmudgeEssentials] Estimated time of cleanup is: " + seconds + " seconds.");
						
						
						for(Chunk c : chunks)
						{
							++x;
							if(third==0)
							{
							++i;
							++third;
							}
							else if(third==1)
							{
							++third;
							}
							else if(third==2)
							{
							third=0;
							}
							final int y = x;
							final int length = chunks.length;
							final Chunk chunk = c;
							final ConsoleCommandSender console = cs;
							Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, new Runnable()
							{
								public void run()
								{
								int bx = chunk.getX() << 4;
						        int bz = chunk.getZ() << 4;   
						        for (int xx = bx; xx < bx + 16; xx++) {
						            for (int zz = bz; zz < bz + 16; zz++) {
						                for (int yy = 0; yy < 128; yy++) {
						                    int typeId = chunk.getWorld().getBlockTypeIdAt(xx, yy, zz);
						                    if (typeId == 90) {
						                       chunk.getWorld().getBlockAt(xx, yy, zz).setType(Material.AIR);
						                       int temp = removedPortals.get(chunk.getWorld());
						                       ++temp;
						                       removedPortals.put(chunk.getWorld(), temp);
						                    }
						                }
						            }
						        }
						        if(y==length)
						        {
									console.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.GOLD + removedPortals.get(chunk.getWorld()) + ChatColor.AQUA + " Netherportals cleaned up.");
									removedPortals.remove(chunk.getWorld());
						        }
								}
							}, i * 1L);
						}
					}
			
				}
		}
		else if(commandLabel.equalsIgnoreCase("cleanchests") && args.length>0)
		{
			final List<Material> banned = getBanned();
			
			String worldName = args[0];
			
			if(sender instanceof Player)
			{
				Player player = (Player) sender;
				
				if(player.isOp())
				{
					World world = Bukkit.getWorld(worldName);
					
					if(world==null)
					{
						player.sendMessage(ChatColor.AQUA + worldName + ChatColor.RED + " is not a valid worldname! (case-sensitive)");
						return true;
					}
							Chunk[] chunks = world.getLoadedChunks();
							
							int i=0;
							int x=0;
							int third=0;
							int amount=0;
							removedPortals.put(world, amount);
							
							double ticks = chunks.length/3;
							
							double seconds = ticks / 20;
							
							player.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Estimated time of cleanup is: " + ChatColor.GOLD + seconds + ChatColor.AQUA + " seconds.");
							
							for(Chunk c : chunks)
							{
								++x;
								if(third==0)
								{
								++i;
								++third;
								}
								else if(third==1)
								{
								++third;
								}
								else if(third==2)
								{
								third=0;
								}
								final int y = x;
								final int length = chunks.length;
								final Chunk chunk = c;
								final Player p = player;
								Bukkit.getScheduler().scheduleSyncDelayedTask(SmudgeEssentials.plugin, new Runnable()
								{
									public void run()
									{
									int bx = chunk.getX() << 4;
							        int bz = chunk.getZ() << 4;   
							        for (int xx = bx; xx < bx + 16; xx++) {
							            for (int zz = bz; zz < bz + 16; zz++) {
							                for (int yy = 0; yy < 128; yy++) {
							                    Block block = chunk.getWorld().getBlockAt(xx, yy, zz);
							                    if (block.getState() instanceof Chest) 
							                    {
							                       Chest chest = (Chest) block.getState();
							                       Inventory inv = chest.getInventory();
							                       
							                       boolean contained = false;
							                       int temp = removedPortals.get(chunk.getWorld());
							                       
							                       for(Material b : banned)
							                       {
							                    	   if(inv.contains(b))
							                    	   {
							                    		   inv.remove(b);
							                    		   contained=true;
							                    	   }
							                       }
							                       
							                       if(contained)
							                       {
							                    	   ++temp;
							                    	   removedPortals.put(chunk.getWorld(), temp);
							                       }
							                    	   
							                    }
							                }
							            }
							        }
							        if(y==length)
							        {
							        	if(removedPortals.get(chunk.getWorld())==1)
							        	{
											p.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.GOLD + removedPortals.get(chunk.getWorld()) + ChatColor.AQUA + " Chest cleaned up.");

							        	}
							        	else
							        	{
							        		p.sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.GOLD + removedPortals.get(chunk.getWorld()) + ChatColor.AQUA + " Chests cleaned up.");
							        	}	
							        	removedPortals.remove(chunk.getWorld());
							        }
									}
								}, i * 1L);
							}
				}
			}
		}
		return true;
	}
	
	public List<Material> getBanned()
	{
		List<Material> banned = new ArrayList<Material>();
		banned.add(Material.MONSTER_EGG);
		banned.add(Material.MONSTER_EGGS);
		banned.add(Material.BEDROCK);
		banned.add(Material.BEACON);
		banned.add(Material.MOB_SPAWNER);
		banned.add(Material.EXP_BOTTLE);
		return banned;
	}
}

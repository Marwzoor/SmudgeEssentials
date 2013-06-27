package net.smudgecraft.smudgeessentials.nonetherportals;

import java.util.HashMap;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class NoNetherPortals implements CommandExecutor
{	
	public final static HashMap<World, Integer> removedPortals = new HashMap<World, Integer>();
	
	public static void init()
	{
		PortalListener pl = new PortalListener();
		
		Bukkit.getPluginManager().registerEvents(pl, SmudgeEssentials.plugin);
		
		Bukkit.getConsoleSender().sendMessage("[SmudgeEssentials] NoNetherPortal initialized.");
	
		SmudgeEssentials.plugin.getCommand("nonether").setExecutor(new NoNetherPortals());
	}
	
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
		return true;
	}
}
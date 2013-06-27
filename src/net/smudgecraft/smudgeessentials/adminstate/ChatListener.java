package net.smudgecraft.smudgeessentials.adminstate;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
	
	public ChatListener()
	{
		
	}
	
	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event)
	{
		if(event.isCancelled())
			return;
		
		Player sender = event.getPlayer();
		
		String message = event.getMessage();
		
		for(Player p : AwayCommand.admins)
		{
			if(AwayCommand.adminstate.get(p)==true)
			{
			if(message.toLowerCase().contains(p.getName().toLowerCase()) && !p.equals(sender))
			{
				sender.sendMessage(ChatColor.GREEN + p.getName() + ChatColor.AQUA + " is away/enjoying his/her freetime at this moment");
			}
			}
		}
		
		/*boolean containedName = false;
		
		String[] splitted = message.replaceAll("[?!=-@]", " ").split(" ");
		
		String name = "";
		
		Player tplayer = null;
		
		for(int i=0;i<splitted.length;++i)
		{
			for(Player p: Bukkit.getOnlinePlayers())
			{
				if(splitted[i].equalsIgnoreCase(p.getName()))
				{
					name=splitted[i];
					
					tplayer = p;
					
					containedName = true;
				}
			}
		}
		
		FPlayer fplayer = FPlayers.i.get(sender);
		
		if(containedName)
		{
			boolean worked = false;
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(p.equals(tplayer) && p!=sender && fplayer.getChatMode().equals(ChatMode.PUBLIC) && !AcCommand.isInAdminChat(sender))
				{
					FPlayer tfplayer = FPlayers.i.get(p);
					String prefix = "";
					if(fplayer.getRole().equals(Role.ADMIN))
						prefix = "**";
					else if(fplayer.getRole().equals(Role.MODERATOR))
						prefix = "*";
					else
						prefix = "";
					if(fplayer.hasFaction())
					{
					p.sendMessage(String.format(event.getFormat().replace("[FACTION]", ChatColor.getLastColors(fplayer.getFaction().getTag(tfplayer)) + prefix + fplayer.getFaction().getTag(tfplayer)), sender.getDisplayName(), message.replaceAll("(?i)" + name, ChatColor.DARK_GREEN + name + ChatColor.WHITE)));
					}
					else
					{
					p.sendMessage(String.format(event.getFormat().replace("[FACTION]", ""), sender.getDisplayName(), message.replaceAll("(?i)" + name, ChatColor.DARK_GREEN + name + ChatColor.WHITE)));
					}
					worked = true;
				}
				else if(!p.equals(tplayer) && !AcCommand.isInAdminChat(sender))
				{
					FPlayer tfplayer = FPlayers.i.get(p);
					String prefix = "";
					if(fplayer.getRole().equals(Role.ADMIN))
						prefix = "**";
					else if(fplayer.getRole().equals(Role.MODERATOR))
						prefix = "*";
					else
						prefix = "";
					if(fplayer.hasFaction())
					{
					p.sendMessage(String.format(event.getFormat().replace("[FACTION]", ChatColor.getLastColors(fplayer.getFaction().getTag(tfplayer)) + prefix + fplayer.getFaction().getTag(tfplayer)), sender.getDisplayName(), event.getMessage()));	
					}
					else
					{
					p.sendMessage(String.format(event.getFormat().replace("[FACTION]", ""), sender.getDisplayName(), event.getMessage()));
					}
					worked = true;
				}
			}
			if(worked)
				event.setCancelled(true);
		}*/
	}
}

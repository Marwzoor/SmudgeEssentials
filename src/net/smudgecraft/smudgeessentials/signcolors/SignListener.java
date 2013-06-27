package net.smudgecraft.smudgeessentials.signcolors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener
{
	public SignListener()
	{
		
	}
	
	@EventHandler
	 public void onSignChange(SignChangeEvent event) 
	 {
	    Player player = event.getPlayer();
	    
	    if(player.isOp() || player.hasPermission("signcolors.colors"))
	    {
	    	for(int i=0;i<4;++i)
	    	{
	    		if(!event.getLine(i).isEmpty())
	    		{
	    			event.setLine(i, event.getLine(i).replace("&", "§"));
	    		}
	    	}
	    }
	}
}

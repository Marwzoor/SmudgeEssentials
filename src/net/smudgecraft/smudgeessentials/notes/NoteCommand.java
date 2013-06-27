package net.smudgecraft.smudgeessentials.notes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class NoteCommand implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("note") && args.length>0)
		{
			if(sender instanceof Player)
			{
				if(((Player) sender).isOp())
				{
					String note="";
					for(String s : args)
					{
						note+=s + " ";
					}
					NoteWriter.writeNote(((Player) sender).getName(), note);
					((Player) sender).sendMessage(ChatColor.GREEN + "[SmudgeEssentials]: " + ChatColor.AQUA + "Note saved.");
				}
			}
			else
			{
				String note="";
				for(String s : args)
				{
					note+=s + " ";
				}
				NoteWriter.writeNote("Console", note);
				ConsoleCommandSender cs = (ConsoleCommandSender) sender;
				cs.sendMessage("[SmudgeEssentials] Note saved.");
			}
		}
		return true;
	}
}

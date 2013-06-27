package net.smudgecraft.smudgeessentials.notes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;
import net.smudgecraft.smudgeessentials.util.Util;

public class NoteWriter 
{
	public static void writeNote(String writer, String text)
	{
		File file = new File(SmudgeEssentials.plugin.getDataFolder() + "/notes.txt");
		
		if(!file.exists())
		{
			try
			{
			file.createNewFile();
			}
			catch(Exception e)
			{
				
			}
		}
		
		try
		{
		FileWriter fw = new FileWriter(file, true);
		
		FileReader fr = new FileReader(file);
		
		BufferedReader in = new BufferedReader(fr);
		
		BufferedWriter out = new BufferedWriter(fw);
		
		String dateFormat = Util.getDateFormat();
		
		if(in.readLine()==null)
		{
			out.append("[" + dateFormat + "] " + writer + ": " + text);
		}
		else
		{
			out.newLine();
			out.append("[" + dateFormat + "] " + writer + ": " + text);
		}
		
		out.close();
		in.close();
		
		}
		catch(Exception e)
		{
			
		}
	}
}

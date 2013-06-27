package net.smudgecraft.smudgeessentials.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.ChatColor;

public class Util 
{
	public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
	    ZipOutputStream zip = null;
	    FileOutputStream fileWriter = null;

	    fileWriter = new FileOutputStream(destZipFile);
	    zip = new ZipOutputStream(fileWriter);
	    
	    addFolderToZip("", srcFolder, zip);
	    zip.flush();
	    zip.close();
	  }

	public static void zipFile(String srcFile, String destZipFile) throws Exception
	{
		ZipOutputStream zip = null;
	    FileOutputStream fileWriter = null;

	    fileWriter = new FileOutputStream(destZipFile);
	    zip = new ZipOutputStream(fileWriter);
	    
	    addFileToZipFile("", srcFile, zip);
	    zip.flush();
	    zip.close();
	}
	  private static void addFileToZipFolder(String path, String srcFile, ZipOutputStream zip)
	      throws Exception {

	    File folder = new File(srcFile);
	    if (folder.isDirectory()) {
	      addFolderToZip(path, srcFile, zip);
	    } else {
	      byte[] buf = new byte[1024];
	      int len;
	      FileInputStream in = new FileInputStream(srcFile);
	      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
	      while ((len = in.read(buf)) > 0) {
	        zip.write(buf, 0, len);
	      }
	      in.close();
	    }
	  }

	  private static void addFileToZipFile(String path, String srcFile, ZipOutputStream zip)
		      throws Exception {

		    File folder = new File(srcFile);
		    if (folder.isDirectory()) {
		      addFolderToZip(path, srcFile, zip);
		    } else {
		      byte[] buf = new byte[1024];
		      int len;
		      FileInputStream in = new FileInputStream(srcFile);
		      zip.putNextEntry(new ZipEntry(path + folder.getName()));
		      while ((len = in.read(buf)) > 0) {
		        zip.write(buf, 0, len);
		      }
		      in.close();
		    }
		  }
	  
	  private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
	      throws Exception {
	    File folder = new File(srcFolder);

	    for (String fileName : folder.list()) {
	      if (path.equals("")) {
	        addFileToZipFolder(folder.getName(), srcFolder + "/" + fileName, zip);
	      } else {
	        addFileToZipFolder(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
	      }
	    }
	  }
	  
	  public static String getDateFormat()
		{
			TimeZone tz = TimeZone.getTimeZone("GMT+01");
			Calendar cal = Calendar.getInstance(tz);
			
			String year = Integer.toString(cal.get(Calendar.YEAR));
			int m = cal.get(Calendar.MONTH);
			int d = cal.get(Calendar.DAY_OF_MONTH);
			int h = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			int sec = cal.get(Calendar.SECOND);
			String month;
			String day;
			String hour;
			String minute;
			String second;
			if(m<10)
			{
				month = "0" + Integer.toString(m);
			}
			else
			{
				month = Integer.toString(m);
			}
			if(d<10)
			{
				day = "0" + Integer.toString(d);
			}
			else
			{
				day = Integer.toString(d);
			}
			if(h<10)
			{
				hour = "0" + Integer.toString(h);
			}
			else
			{
				hour = Integer.toString(h);
			}
			if(min<10)
			{
				minute = "0" + Integer.toString(min);
			}
			else
			{
				minute = Integer.toString(min);
			}
			if(sec<10)
			{
				second = "0" + Integer.toString(sec);
			}
			else
			{
				second = Integer.toString(sec);
			}
			
			String dateFormat = year + "-" + month + "-" + day + "_" + hour + "-" + minute + "-" + second;
			return dateFormat;
		}
	  
	  public static String setChatColor(String s) {
		    s = s.replace("&0", "" + ChatColor.BLACK);
		    s = s.replace("&1", "" + ChatColor.DARK_BLUE);
		    s = s.replace("&2", "" + ChatColor.DARK_GREEN);
		    s = s.replace("&3", "" + ChatColor.DARK_AQUA);
		    s = s.replace("&4", "" + ChatColor.DARK_RED);
		    s = s.replace("&5", "" + ChatColor.DARK_PURPLE);
		    s = s.replace("&6", "" + ChatColor.GOLD);
		    s = s.replace("&7", "" + ChatColor.GRAY);
		    s = s.replace("&8", "" + ChatColor.DARK_GRAY);
		    s = s.replace("&9", "" + ChatColor.BLUE);
		    s = s.replace("&a", "" + ChatColor.GREEN);
		    s = s.replace("&b", "" + ChatColor.AQUA);
		    s = s.replace("&c", "" + ChatColor.RED);
		    s = s.replace("&d", "" + ChatColor.LIGHT_PURPLE);
		    s = s.replace("&e", "" + ChatColor.YELLOW);
		    s = s.replace("&f", "" + ChatColor.WHITE);
		    s = s.replace("&o", "" + ChatColor.ITALIC);
		    s = s.replace("&l", "" + ChatColor.BOLD);
		    s = s.replace("&k", "" + ChatColor.MAGIC);
		    s = s.replace("&r", "" + ChatColor.RESET);
		    return s;
		  }
	  public static String[] getMultilineMessage(String message){
		    String[] s = message.split("\n");
		    return s;
		}
}

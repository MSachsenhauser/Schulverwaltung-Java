package database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
public class Error {
	
	public static void out(Exception ex)
	{
		//Out(ex.getLocalizedMessage());
		ex.printStackTrace();
	}
	
	public static void out(Exception ex, String extra)
	{
		////Out(ex.getLocalizedMessage(), extra);
		ex.printStackTrace();
	}
	
	public static void out(String exception)
	{
		//Out(exception, "");
	}
	
	public static void out(String exception, String extra)
	{
		String curDateString = new SimpleDateFormat("dd_MM_yy").format(new Date()) + ".txt";
		File errorFile = new File("Logs\\ErrorLog" + curDateString);
		try {
			FileWriter writer = new FileWriter(errorFile, errorFile.exists());
			writer.write(new Date().toGMTString() + ": \n\t" + 
						 exception + (extra != "" ? "\n\tDetails: " + extra : ""));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package Database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.io.*;
public class Error {
	
	public static void Out(Exception ex)
	{
		Out(ex.getLocalizedMessage());
	}
	
	public static void Out(Exception ex, String extra)
	{
		Out(ex.getLocalizedMessage(), extra);
	}
	
	public static void Out(String exception)
	{
		Out(exception, "");
	}
	
	public static void Out(String exception, String extra)
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
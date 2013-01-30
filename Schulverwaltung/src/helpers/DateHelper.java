package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	public static long DateDiff(Date start, Date end)
	{
		Calendar calendar1 = Calendar.getInstance();
		  Calendar calendar2 = Calendar.getInstance();
		    
		  calendar1.set(start.getYear(), start.getMonth(), start.getDate());
		  calendar2.set(end.getYear(), end.getMonth(), end.getDate());
			    		
		  long milliseconds1 = calendar1.getTimeInMillis();
		  long milliseconds2 = calendar2.getTimeInMillis();
		  long diff = milliseconds2 - milliseconds1;
		  return diff;
	}
	
	public static long DateDiffDays(Date start, Date end)
	{
		return DateDiff(start, end) / (24 * 60 * 60 * 1000);
	}
	
	public static double DateDiffYear(Date start, Date end)
	{
		return DateDiff(start, end) / 365;
	}
	
	public static Date ParseExcelDate(String date)
	{ 
		Date result = new Date();
	
		if (date != null && !date.isEmpty() )
		{
			System.out.println(date);
			if ( date.contains("/"))
			{
			String tmpdate = date.split(" ")[0];
				if (tmpdate != null && !tmpdate.isEmpty())
				{
				
					String[] dateinfo = tmpdate.split("/");
					if (dateinfo != null)
					{
						result.setDate(Integer.parseInt(dateinfo[1]));
						result.setMonth(Integer.parseInt(dateinfo[0]));
						result.setYear(Integer.parseInt(dateinfo[2]));
					}
				}
			}
			else
			{
				try {
					result = new SimpleDateFormat().parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}

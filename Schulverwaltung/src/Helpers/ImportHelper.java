package Helpers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Elements.Company;
import Elements.Guardian;
import Elements.Instructor;
import Elements.Job;
import Elements.Religion;
import Elements.Student;

import jxl.*;
import jxl.read.biff.BiffException;

public class ImportHelper 
{
	public static void Import (InputStream file)
	{
		
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			
			Sheet	sheet = workbook.getSheet(0);
			
			for (int i =1; i< sheet.getRows(); i++)
			{
				SimpleDateFormat formatter = new SimpleDateFormat();
					
					
					Religion religion = new Religion();
					String religionflag = sheet.getCell(12, i).getContents();
					switch (religionflag)
					{
						case "BL":
							religion.setDescription("Bekenntnislos");
							break;
					
						case "RK":
							religion.setDescription("Römischkatholisch");
							break;
					
						case "EV":
							religion.setDescription("Evangelisch");
							break;
					}
					
					if (religion.getDescription().isEmpty())
					{
						System.out.println("keine Daten");
					}
					else
					{
						religion.addToDb();
					}
					
			
				
					Job job = new Job();
					job.setDescription(sheet.getCell(75, i).getContents());
					
				/*
					  String duration1 = duration1.setDuration(sheet.getCell(70,i).getContents());
					  String duration2 = duration2.setDuration(sheet.getCell(71,i).getContents());
					  long milliseconds1 = duration1.getTimeInMillis();
					  long milliseconds2 = duration2.getTimeInMillis();
					  long diff = milliseconds2 - milliseconds1;
					  long diffSeconds = diff / 1000;
					  long diffMinutes = diff / (60 * 1000);
					  long diffHours = diff / (60 * 60 * 1000);
					  long diffDays = diff / (24 * 60 * 60 * 1000);
					  */
					
					if (job.getDescription().isEmpty())
					{
						System.out.println("keine Daten");
					}
					else
					{
						job.addToDb();
					}
					
					try
					{
						
						
						Instructor instructor = new Instructor();
						Company company = new Company ();
						
						System.out.println(sheet.getCell(104, i).getContents());
						
						
						String tmpname = sheet.getCell(104, i).getContents();
								if (tmpname != null && !tmpname.isEmpty())
								{
									String[] name =sheet.getCell(104, i).getContents().split(" ");
								
						
						
									if (name != null)
									{
										instructor.setName(name[1]);
										instructor.setFirstname(name[0]);
									}
								}
						
								
						String tmpadress = 	sheet.getCell(103, i).getContents();
						if (tmpadress != null && !tmpadress.isEmpty())
						{
							String[] adress = sheet.getCell(103, i).getContents().split(", ");
						
								
							if (adress != null)
							{
								instructor.setPhone(adress[2].replaceFirst("Tel. ", ""));
								String[] city = adress[1].split(" ");
								company.setStreet(adress[0]);
								company.setCity(city[1]);
								company.setPlz(city[0]);
								company.setPhone(adress[2].replaceFirst("Tel. ", ""));
							}
						}
						
						instructor.setEmail(sheet.getCell(105, i).getContents());
						company.setName(sheet.getCell(102, i).getContents());
						
							if (instructor.getName().isEmpty()&&instructor.getFirstname().isEmpty())
							{
								System.out.println("keine Daten");
							}
							else
							{
								instructor.addToDb();
							}
						
							
							if (company.getName().isEmpty())
							{
								System.out.println("keine Daten");
							}
							else
							{
								company.addToDb();
							}
						
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
					System.out.println((sheet.getCell(10, i).getContents()));
					Student student = new Student();
				student.setName(sheet.getCell(1, i).getContents());
				student.setFirstname(sheet.getCell(2, i).getContents());
				student.setStreet(sheet.getCell(37,i).getContents());
				student.setCity(sheet.getCell(40, i).getContents());
				student.setPlz(sheet.getCell(39, i).getContents());
				student.setBirthday(ParseExcelDate(sheet.getCell(10, i).getContents()));
				student.setEntry(ParseExcelDate( sheet.getCell(52, i).getContents()));
				student.setShortened(false);
				student.setPhone(sheet.getCell(41, i).getContents());
				
				if (student.getName().isEmpty()&&student.getFirstname().isEmpty())
				{
					System.out.println("keine Daten");
				}
				else
				{
					student.addToDb();
				}
				
				
			}
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Date ParseExcelDate(String date)
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	
		
	}
	
}

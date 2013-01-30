package helpers;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import elements.Company;
import elements.Instructor;
import elements.Job;
import elements.Religion;
import elements.Student;
import elements.Typification;

import jxl.*;
import jxl.read.biff.BiffException;

public class ImportHelper 
{
	public static void Import(InputStream file)
	{
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			
			Sheet sheet = workbook.getSheet(0);
			
			for (int i =1; i < sheet.getRows(); i++)
			{
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
				
				if (!religion.getDescription().isEmpty())
				{
					religion.addToDb();
				}
				
				Job job = new Job();
				job.setDescription(sheet.getCell(75, i).getContents());
				
			
			  String duration1 = sheet.getCell(71,i).getContents();
			  String duration2 = sheet.getCell(72,i).getContents();
			  Date start = DateHelper.ParseExcelDate(duration1);
			  Date end = DateHelper.ParseExcelDate(duration2);
			  
			  job.setDuration(DateHelper.DateDiffYear(start, end));
				  
				
				if (!job.getDescription().isEmpty())
				{
					job.addToDb();
				}
				
				try
				{
					Instructor instructor = new Instructor();
					Company company = new Company ();
					
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
					
					if (!instructor.getName().isEmpty() && !instructor.getFirstname().isEmpty())
					{
						instructor.addToDb();
					}			
					
					if (!company.getName().isEmpty())
					{
						company.addToDb();
					}		
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
					
				Student student = new Student();
				student.setName(sheet.getCell(1, i).getContents());
				student.setFirstname(sheet.getCell(2, i).getContents());
				student.setStreet(sheet.getCell(37,i).getContents());
				student.setCity(sheet.getCell(40, i).getContents());
				student.setPlz(sheet.getCell(39, i).getContents());
				student.setBirthday(DateHelper.ParseExcelDate(sheet.getCell(10, i).getContents()));
				student.setEntry(DateHelper.ParseExcelDate(sheet.getCell(52, i).getContents()));
				student.setShortened(false);
				student.setPhone(sheet.getCell(41, i).getContents());
				Typification typification = new Typification();
				typification.setDescription(sheet.getCell(123,i).getContents());
				typification.addToDb();
				
				if (!student.getName().isEmpty() && !student.getFirstname().isEmpty())
				{
					student.addToDb();
				}
			}
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

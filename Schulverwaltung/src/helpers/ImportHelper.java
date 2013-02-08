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

import database.Database;
import database.Error;

import jxl.*;
import jxl.read.biff.BiffException;

public class ImportHelper {
	public static void Import(InputStream file) {
		try {
			Workbook workbook = Workbook.getWorkbook(file);

			Sheet sheet = workbook.getSheet(0);

			for (int i = 1; i < sheet.getRows(); i++) {
				Religion religion = new Religion();
				String religionflag = sheet.getCell(12, i).getContents();
				switch (religionflag) {
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

				if (!religion.getDescription().isEmpty()) {
					religion.addToDb();
				}

				Job job = new Job();
				job.setDescription(sheet.getCell(75, i).getContents());

				String duration1 = sheet.getCell(71, i).getContents();
				String duration2 = sheet.getCell(72, i).getContents();
				Date start = DateHelper.ParseExcelDate(duration1);
				Date end = DateHelper.ParseExcelDate(duration2);

				job.setDuration(DateHelper.DateDiffYear(start, end));
				
				if (!job.getDescription().isEmpty()) {
					job.addToDb();
				}
				
				Boolean shortened = false;
				int jobId = -1;
				try(Database db = new Database())
				{
					double maxDuration = db.getDouble("SELECT MAX(Duration) FROM job WHERE Description = ?", job.getDescription());
					System.out.println(maxDuration);
					if(job.getDuration() >= maxDuration)
					{
						db.NoQuery("UPDATE student SET jobId = ? WHERE jobId IN (SELECT jobId FROM job WHERE Description = ?)", job.getId(), job.getDescription());
						db.NoQuery("DELETE FROM job WHERE id != ? AND Description = ?", job.getId(), job.getDescription());
						jobId = job.getId();
					}
					else
					{
						jobId = db.getInt("SELECT id FROM job WHERE Duration = (SELECT MAX(Duration) FROM job WHERE Description = ?)", job.getDescription());
						job.removeFromDb();
						shortened = true;
					}
				}
				catch(Exception ex)
				{
					Error.out(ex);
				}
				
				Instructor instructor = new Instructor();
				Company company = new Company();
				try {
					String tmpname = sheet.getCell(104, i).getContents();
					if (tmpname != null && !tmpname.isEmpty()) {
						String[] name = sheet.getCell(104, i).getContents()
								.split(" ");

						if (name != null) {
							instructor.setName(name[1]);
							instructor.setFirstname(name[0]);
						}
					}

					String tmpadress = sheet.getCell(103, i).getContents();
					if (tmpadress != null && !tmpadress.isEmpty()) {
						String[] adress = sheet.getCell(103, i).getContents()
								.split(", ");

						if (adress != null) {
							instructor.setPhone(adress[2].replaceFirst("Tel. ",
									""));
							String[] city = adress[1].split(" ");
							company.setStreet(adress[0]);
							company.setCity(city[1]);
							company.setPlz(city[0]);
							company.setPhone(adress[2]
									.replaceFirst("Tel. ", ""));
						}
					}

					instructor.setEmail(sheet.getCell(105, i).getContents());
					company.setName(sheet.getCell(102, i).getContents());

					if (!company.getName().isEmpty()) {
						company.addToDb();
					}

					if (!instructor.getName().isEmpty()
							&& !instructor.getFirstname().isEmpty()) {
						instructor.setCompanyId(company.getId());
						instructor.addToDb();
					}
				} catch (Exception ex) {
					Error.out(ex);
				}

				Student student = new Student();
				student.setName(sheet.getCell(1, i).getContents());
				student.setFirstname(sheet.getCell(2, i).getContents());
				student.setStreet(sheet.getCell(37, i).getContents());
				student.setCity(sheet.getCell(40, i).getContents());
				student.setPlz(sheet.getCell(39, i).getContents());
				student.setBirthday(DateHelper.ParseExcelDate(sheet.getCell(10,
						i).getContents()));
				student.setEntry(DateHelper.ParseExcelDate(sheet.getCell(52, i)
						.getContents()));
				student.setShortened(shortened);
				student.setPhone(sheet.getCell(41, i).getContents());
				student.setReligionId(religion.getId());
				student.setInstructorId(instructor.getId());
				student.setJobId(jobId);
				Typification typification = new Typification();
				typification
						.setDescription(sheet.getCell(124, i).getContents());
				typification.addToDb();

				if (!student.getName().isEmpty()
						&& !student.getFirstname().isEmpty()) {
					student.addToDb();
				}
			}

		} catch (BiffException e) {
			Error.out(e);
		} catch (IOException e) {
			Error.out(e);
		}
	}
}

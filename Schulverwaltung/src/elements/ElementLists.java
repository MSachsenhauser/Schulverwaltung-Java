package elements;

import java.sql.ResultSet;
import java.util.*;

import database.Database;
import database.Error;


public class ElementLists {
	private static ArrayList<Job> jobs;
	private static ArrayList<Religion> religions;
	private static ArrayList<Teacher> teachers;
	private static ArrayList<Room> rooms;
	private static ArrayList<Subject> subjects;
	private static ArrayList<Company> companies;
	private static ArrayList<Grade> grades;
	private static ArrayList<Student> students;
	private static ArrayList<MarkType> markTypes;
	public static ArrayList<Job> getJobs() {
		jobs = new ArrayList<Job>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT Id FROM Job");
			while(result.next())
			{
				jobs.add(new Job().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return jobs;
	}
	public static ArrayList<Religion> getReligions() {
		religions = new ArrayList<Religion>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT Id FROM Religion");
			while(result.next())
			{
				religions.add(new Religion().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return religions;
	}
	public static ArrayList<Teacher> getTeachers() {
		teachers = new ArrayList<Teacher>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT Id FROM Teacher");
			while(result.next())
			{
				teachers.add(new Teacher().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)	
		{
			Error.out(ex);
		}
		return teachers;
	}
	public static ArrayList<Room> getRooms() {
		rooms = new ArrayList<Room>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT Id FROM Room");
			while(result.next())
			{
				rooms.add(new Room().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)	
		{
			Error.out(ex);
		}
		return rooms;
	}
	public static ArrayList<Subject> getSubjects() {
		subjects = new ArrayList<Subject>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT Id FROM Subject");
			while(result.next())
			{
				subjects.add(new Subject().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)	
		{
			Error.out(ex);
		}
		return subjects;
	}
	public static ArrayList<Company> getCompanies() {
		companies = new ArrayList<Company>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT Id FROM Company");
			while(result.next())
			{
				companies.add(new Company().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)	
		{
			Error.out(ex);
		}
		return companies;
	}
	
	public static ArrayList<Grade> getGrades()
	{
		grades = new ArrayList<Grade>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT Id FROM Grade");
			while(result.next())
			{
				grades.add(new Grade().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)	
		{
			Error.out(ex);
		}
		return grades;
	}
	
	public static ArrayList<Student> getStudents(int gradeId)
	{
		students = new ArrayList<Student>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT ID FROM student WHERE ID NOT IN " + 
											  "(SELECT studentId from student2group " +
											  "LEFT JOIN gradeGroup ON gradeGroup.id = student2group.groupid " + 
											  "WHERE gradeGroup.gradeid = ?) " +
											  "ORDER BY Name", gradeId);
			while(result.next())
			{
				students.add(new Student().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)	
		{
			Error.out(ex);
		}
		return students;
	}
	
	public static ArrayList<MarkType> getMarkTypes()
	{
		markTypes = new ArrayList<MarkType>();
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT id FROM MarkType Order By Weight, Description" );
			while(result.next())
			{
				markTypes.add(new MarkType().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)	
		{
			Error.out(ex);
		}
		return markTypes;
	}
}

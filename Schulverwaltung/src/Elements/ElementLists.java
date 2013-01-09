package Elements;

import java.sql.ResultSet;
import java.util.*;

import Database.Database;

public class ElementLists {
	private static ArrayList<Job> jobs;
	private static ArrayList<Religion> religions;
	private static ArrayList<Teacher> teachers;
	private static ArrayList<Room> rooms;
	private static ArrayList<Subject> subjects;
	private static ArrayList<Company> companies;
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
			ex.printStackTrace();
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
			ex.printStackTrace();
		}
		return religions;
	}
	public static ArrayList<Teacher> getTeachers() {
		return teachers;
	}
	public static ArrayList<Room> getRooms() {
		return rooms;
	}
	public static ArrayList<Subject> getSubjects() {
		return subjects;
	}
	public static ArrayList<Company> getCompanies() {
		return companies;
	}
}

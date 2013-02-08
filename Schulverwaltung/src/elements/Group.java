package elements;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;
import database.Error;


public class Group implements IDatabaseObject<Group>{
	private String description = "";
	private int disableflag = -1;
	private Grade grade = null;
	private int gradeId = -1;
	private int id = -1;
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<GroupSubject> subjects = new ArrayList<GroupSubject>();
	private int timetableId = -1;
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int gradeId = db.getInt("SELECT id FROM gradeGroup WHERE Description=? ", this.getDescription());
			if(gradeId == -1)
			{
				int id = db.getInt("SELECT MAX(Id) FROM gradeGroup");
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);

				db.NoQuery("INSERT INTO gradeGroup(Id, description, gradeId, disableflag)" +
						   " values(?,?, ?,0)",
						   this.getId(),this.getDescription(), this.getGradeId());
			}
			else
			{
				this.setId(gradeId);
			}
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
		
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getDisableflag() {
		return disableflag;
	}
	
	public Grade getGrade()
	{
		if(this.grade == null)
		{
			this.grade = new Grade().setId(this.getGradeId()).load();
		}
		
		return this.grade;
	}

	public int getGradeId() {
		return gradeId;
	}
	
	public int getId() {
		return id;
	}
	public ArrayList<Student> getStudents() {
		return students;
	}
	public ArrayList<GroupSubject> getSubjects()
	{
		return this.subjects;
	}
	public int getTimetableId() {
		return timetableId;
	}
	@Override
	public Group load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM gradeGroup WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setGradeId(result.getInt("gradeId"));
				this.setDisableflag(result.getInt("disableflag"));
			}
			result.close();
			
			this.students = new ArrayList<Student>();
			result = db.getDataRows("SELECT StudentId FROM student2group WHERE GroupId=?", this.getId());
			while(result.next())
			{
				this.students.add(new Student().setId(result.getInt("StudentId")).load());
			}
			result.close();
			
			this.subjects = new ArrayList<GroupSubject>();
			result = db.getDataRows("SELECT id FROM group2subject WHERE GroupId = ?", this.getId());
			while(result.next())
			{
				this.subjects.add(new GroupSubject().setId(result.getInt("Id")).load());
			}
			
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE gradeGroup SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update gradeGroup set description = ?,timetableId = ?,disableflag = ? where id = ?",
					this.getDescription(),
					this.getTimetableId(),
					this.getDisableflag(),
					this.getId());
			
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public Group setDescription(String description) {
		this.description = description;
		return this;
	}
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}

	public Group setGradeId(int gradeId) {
		this.gradeId = gradeId;
		this.grade = null;
		return this;
	}
	public Group setId(int id) {
		this.id = id;
		return this;
	}
	public Group setStudents(ArrayList<Student> students) {
		this.students = students;
		return this;
	}
	public Group setSubjects(ArrayList<GroupSubject> subjects)
	{
		this.subjects = subjects;
		return this;
	}

	public Group setTimetableId(int timetableId) {
		this.timetableId = timetableId;
		return this;
	}
}

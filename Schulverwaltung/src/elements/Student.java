package elements;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import database.Database;
import database.Error;


public class Student extends Person<Student>{
	private Date birthday = new Date();
	private Date entry = new Date();
	private int intructorId = -1;
	private int jobId = -1;
	private int religionId = -1;
	private Boolean shortened = false;
	private String street = "";
	private String plz = "";
	private String city = "";
	private int disableflag = -1;
	private Job job = null;
	private Religion religion = null;
	private Company company = null;
	private Instructor instructor = null;
	private ArrayList<ArrayList<Mark>> marks = null;
	
	public ArrayList<ArrayList<Mark>> getMarks()
	{
		if(marks == null)
		{
			marks = new ArrayList<ArrayList<Mark>>();
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT mark.id, exam.group2subjectId FROM mark " +
										"INNER JOIN exam ON mark.examId = exam.id " +
										"INNER JOIN group2subject ON exam.group2subjectId = group2subject.id " +
										"INNER JOIN subject ON group2subject.subjectId = subject.id " +
										"WHERE mark.studentId = ? " +
										"ORDER BY subject.Short", this.getId());
				int oldSubjectId = -1;
				ArrayList<Mark> curMarks = new ArrayList<Mark>();
				while(result.next())
				{
					int curSubjectId = result.getInt("Group2SubjectId");
					Mark mark = new Mark().setId(result.getInt("Id")).load();;
					if(oldSubjectId == curSubjectId)
					{
						curMarks.add(mark);
					}
					else
					{
						oldSubjectId = curSubjectId;
						if(curMarks.size() > 0)
						{
							marks.add(curMarks);
						}
						curMarks = new ArrayList<Mark>();
						curMarks.add(mark);
					}
				}
				if(curMarks.size() > 0)
				{
					this.marks.add(curMarks);
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		return marks;
	}
	
	public Student setMarks(ArrayList<ArrayList<Mark>> marks)
	{
		this.marks = marks;
		return this;
	}
	
	public Student()
	{
	}
	
	public Student(int id)
	{
		this.setId(id);
	}
	
	public String getStreet() {
		return street;
	}
	public Student setStreet(String street) {
		this.street = street;
		return this;
	}
	public String getPlz() {
		return plz;
	}
	public Student setPlz(String plz) {
		this.plz = plz;
		return this;
	}
	public String getCity() {
		return city;
	}
	public Student setCity(String city) {
		this.city = city;
		return this;
	}

	public Date getBirthday() {
		return birthday;
	}
	public Student setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}
	public Date getEntry() {
		return entry;
	}
	public Student setEntry(Date entry) {
		this.entry = entry;
		return this;
	}
	
	public Company getCompany()
	{
		if(this.company == null )
		{
			if(this.instructor != null)
			{
				this.company = new Company().setId(this.instructor.getCompanyId()).load();
			}
			
			if (this.company == null)
			{
				this.company = new Company();
			}
		}
		return this.company;
	}
	
	public Job getJob()
	{
		if(this.job == null)
		{
			this.job = new Job().setId(this.jobId).load();
		}
		
		return this.job;
	}
	
	public Religion getReligion()
	{
		if(this.religion == null)
		{
			this.religion = new Religion().setId(this.religionId).load();
		}
		
		return this.religion;
	}
	
	public int getJobId() {
		return jobId;
	}
	public Student setJobId(int jobId) {
		this.job = null;
		this.jobId = jobId;
		return this;
	}
	public int getReligionId() {
		return religionId;
	}
	public Student setReligionId(int religionId) {
		this.religion = null;
		this.religionId = religionId;
		return this;
	}
	public Boolean getShortened() {
		return shortened;
	}
	public Student setShortened(Boolean shortened) {
		this.shortened = shortened;
		return this;
	}
	public int getDisableflag() {
		return disableflag;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	public int getIntructorId() {
		return intructorId;
	}

	public void setIntructorId(int intructorId) {
		this.instructor = null;
		this.company = null;
		this.intructorId = intructorId;
	}

	@Override
	public void addToDb()
	{
		try(Database db = new Database())
			{	
				int studentId = db.getInt("SELECT id FROM student WHERE name=? AND Firstname =? AND  Street=? AND City=? AND Plz=? AND Birthday=? AND " +
						"" + "" + "Entry=? AND Shortened=? AND Phone=? AND Email=? AND InstructorId=? AND JobId=? AND ReligionId=? ",
						this.getName(), this.getFirstname(), this.getStreet(), this.getCity(), this.getPlz(), this.getBirthday(),
						this.getEntry(), this.getShortened(),this.getPhone(),this.getEmail(), this.getIntructorId(), this.getJob(), this.getReligionId());
				if(studentId == -1)
				{
					int id = db.getInt("SELECT MAX(Id) FROM student");
					if(id == -1)
					{
						id = 1;
					}
					else
					{
						id++;
					}
					
					this.setId(id);
					
					db.NoQuery("INSERT INTO student(Id, Name, Firstname, Street, City, Plz, Birthday, " + "" +
							   "Entry, Shortened, Phone, Email, InstructorId, JobId, ReligionId, disableflag)" +
							   " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)",
							   this.getId(), this.getName(), this.getFirstname(), this.getStreet(), this.getCity(),
							   this.getPlz(), this.getBirthday(), this.getEntry(), this.getShortened(), this.getPhone(),
							   this.getEmail(), this.getIntructorId(), this.getJobId(), this.getReligionId());
			}
			else
			{
				this.setId(studentId);
			}
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	
	@Override
	public void removeFromDb()
	{
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Student SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	
	@Override
	public void save(){
	try(Database db = new Database())
	{
		db.NoQuery("update student set birthday = ?,city = ?,email = ?, entry = ?, firstname = ?, jobId = ?, name = ?, plz = ?, religionId = ? , shortened = ? , phone = ? , disableflag  = ? where id = ?",
				this.getBirthday(),
				this.getCity(),
				this.getEmail(),
				this.getEntry(),
				this.getFirstname(),
				this.getJobId(),
				this.getName(),
				this.getPlz(),
				this.getReligionId(),
				this.getShortened(),
				this.getPhone(),
				this.getDisableflag(),
				this.getId());
	}
	catch(Exception ex)
	{
		Error.out(ex);
	}
}
	public Student load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM student WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setBirthday(result.getDate("birthday"));
				this.setCity(result.getString("city"));
				this.setEmail(result.getString("email"));
				this.setEntry(result.getDate("entry"));
				this.setFirstname(result.getString("firstname"));
				this.setJobId(result.getInt("jobId"));
				this.setName(result.getString("name"));
				this.setPlz(result.getString("plz"));
				this.setReligionId(result.getInt("religionId"));
				this.setShortened(result.getBoolean("shortened"));
				this.setPhone(result.getString("phone"));
				this.setDisableflag(result.getInt("disableflag"));
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
}

package elements;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import database.Database;
import database.Error;


public class Student extends Person<Student>{
	private ArrayList<Absence> absence = null;
	private Date birthday = new Date();
	private String city = "";
	private Company company = null;
	private ArrayList<Delay> delays = null;
	private int disableflag = -1;
	private Date entry = new Date();
	private Grade grade = null;
	private int gradeId = -1;
	private Guardian guardian1 = null;
	private Guardian guardian2 = null;
	private int guardianId1 = -1;
	private int guardianId2 = -1;
	private Instructor instructor = null;
	private int instructorId = -1;
	private Boolean isGermanFree = false;
	private Boolean isReligionFree = false;
	private Boolean isSportFree = false;
	private Job job = null;
	private int jobId = -1;
	private ArrayList<ArrayList<Mark>> marks = null;
	private String plz = "";
	private Religion religion = null;
	
	private int religionId = -1;

	private Boolean shortened = false;
	private String street = "";
	private Typification typification = null;
	private int typificationId = -1;
	public Student()
	{
	}
	
	public Student(int id)
	{
		this.setId(id);
	}
	
	@Override
	public void addToDb()
	{
		try(Database db = new Database())
			{	
				int studentId = db.getInt("SELECT id FROM student WHERE name=? AND Firstname =? AND  Street=? AND City=? AND Plz=? AND Birthday=? AND " +
						"" + "" + "Entry=? AND Shortened=? AND Phone=? AND Email=? AND InstructorId=? AND JobId=? AND ReligionId=? ",
						this.getName(), this.getFirstname(), this.getStreet(), this.getCity(), this.getPlz(), this.getBirthday(),
						this.getEntry(), this.getShortened(),this.getPhone(),this.getEmail(), this.getInstructorId(), this.getJobId(), this.getReligionId());
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
						   this.getEmail(), this.getInstructorId(), this.getJobId(), this.getReligionId());
				ArrayList<Subject> subjects = ElementLists.getSubjects();
				if(this.getIsGermanFree())
				{
					int subjectId = -1;
					for(Subject subject:subjects)
					{
						if(subject.getShortName().equalsIgnoreCase("D") || subject.getDescription().equalsIgnoreCase("Deutsch"))
						{
							subjectId = subject.getId();
							break;
						}
					}
					
					db.NoQuery("INSERT INTO free2subjekt (subjectId, studentId, freeDate) VALUES (?,?, SYSDATE())", subjectId, this.getId());
				}
				
				if(this.getIsReligionFree())
				{
					int subjectId = -1;
					for(Subject subject:subjects)
					{
						if(subject.getShortName().equalsIgnoreCase("Rel") || subject.getDescription().equalsIgnoreCase("Religion"))
						{
							subjectId = subject.getId();
							break;
						}
					}
					
					db.NoQuery("INSERT INTO free2subjekt (subjectId, studentId, freeDate) VALUES (?,?, SYSDATE())", subjectId, this.getId());
				}
				
				if(this.getIsSportFree())
				{
					int subjectId = -1;
					for(Subject subject:subjects)
					{
						if(subject.getShortName().equalsIgnoreCase("Sp") || subject.getDescription().equalsIgnoreCase("Sport"))
						{
							subjectId = subject.getId();
							break;
						}
					}
					
					db.NoQuery("INSERT INTO free2subjekt (subjectId, studentId, freeDate) VALUES (?,?, SYSDATE())", subjectId, this.getId());
				}
				
				db.NoQuery("INSERT INTO student2typification (studentId, typificationId) VALUES (?,?)", this.getId(), this.getTypificationId());
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

	public ArrayList<Absence> getAbsence()
	{
		if(this.absence == null)
		{
			this.absence = new ArrayList<Absence>();
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT id FROM absence WHERE StudentId =?", this.getId());
				while(result.next())
				{
					this.absence.add(new Absence().setId(result.getInt("id")).load());
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		return this.absence;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getCity() {
		return city;
	}

	public Company getCompany()
	{
		if(this.company == null )
		{
			if(this.getInstructor() != null)
			{
				this.company = new Company().setId(this.getInstructor().getCompanyId()).load();
			}
			
			if (this.company == null)
			{
				this.company = new Company();
			}
		}
		return this.company;
	}

	public ArrayList<Delay> getDelays()
	{
		if(this.delays == null)
		{
			this.delays = new ArrayList<Delay>();
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT id FROM delay WHERE StudentId =?", this.getId());
				while(result.next())
				{
					this.delays.add(new Delay().setId(result.getInt("id")).load());
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		return this.delays;
	}

	public int getDisableflag() {
		return disableflag;
	}
	
	public Date getEntry() {
		return entry;
	}
	
	public Grade getGrade() {
		if(grade == null)
		{
			grade = new Grade().setId(this.gradeId).load();
		}
		
		return grade;
	}

	public int getGradeId() {
		return gradeId;
	}

	public Guardian getGuardian1()
	{
		if(this.guardian1 == null)
		{
			this.guardian1 = new Guardian().setId(this.getGuardianId1()).load();
		}
		
		return this.guardian1;
	}

	public Guardian getGuardian2()
	{
		if(this.guardian2 == null)
		{
			this.guardian2 = new Guardian().setId(this.getGuardianId2()).load();
		}
		
		return this.guardian2;
	}

	public int getGuardianId1() {
		return guardianId1;
	}

	public int getGuardianId2() {
		return guardianId2;
	}

	public Instructor getInstructor()
	{
		if(this.instructor == null)
		{
			this.instructor = new Instructor().setId(this.getInstructorId()).load();
		}
		
		return this.instructor;
	}
	public int getInstructorId()
	{
		return this.instructorId;
	}
	
	public Boolean getIsGermanFree() {
		return isGermanFree;
	}
	
	public Boolean getIsReligionFree() {
		return isReligionFree;
	}
	
	public Boolean getIsSportFree() {
		return isSportFree;
	}
	
	public Job getJob()
	{
		if(this.job == null)
		{
			this.job = new Job().setId(this.jobId).load();
		}
		
		return this.job;
	}
	
	public int getJobId() {
		return jobId;
	}
	
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
	
	public String getPlz() {
		return plz;
	}
	public Religion getReligion()
	{
		if(this.religion == null)
		{
			this.religion = new Religion().setId(this.religionId).load();
		}
		
		return this.religion;
	}
	public int getReligionId() {
		return religionId;
	}
	public Boolean getShortened() {
		return shortened;
	}
	public String getStreet() {
		return street;
	}
	public Typification getTypification()
	{
		if(this.typification == null)
		{
			typification = new Typification().setId(this.typificationId).load();
		}
		return this.typification;
	}

	public int getTypificationId() {
		return typificationId;
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
				this.setInstructorId(result.getInt("instructorId"));
				this.setStreet(result.getString("street"));
				this.setReligionId(result.getInt("religionId"));
				this.setShortened(result.getBoolean("shortened"));
				this.setPhone(result.getString("phone"));
				this.setDisableflag(result.getInt("disableflag"));
			}
			
			this.setGradeId(db.getInt("SELECT gradeId FROM grade " +
					"LEFT JOIN gradeGroup ON gradeId = grade.Id " +
					"LEFT JOIN student2Group ON student2group.groupId = gradeGroup.Id " +
					"WHERE student2group.studentId = ?", this.getId()));
			
			result.close();
			result = db.getDataRows("SELECT guardianId FROM student2guardian WHERE studentId = ?", this.getId());
			int i = 1;
			while(result.next())
			{
				if(i == 1)
				{
					this.setGuardianId1(result.getInt(1));
				}
				else
				{
					this.setGuardianId2(result.getInt(1));
				}
				i++;
			}
			result.close();
			
			this.setIsGermanFree(db.getInt("SELECT COUNT(*) FROM free2subjekt WHERE studentId=? AND subjectId IN (SELECT id FROM subject WHERE UPPER(short)='D' OR description='Deutsch')", this.getId()) > 0);
			this.setIsSportFree(db.getInt("SELECT COUNT(*) FROM free2subjekt WHERE studentId=? AND subjectId IN (SELECT id FROM subject WHERE description='Sport')", this.getId()) > 0);
			this.setIsReligionFree(db.getInt("SELECT COUNT(*) FROM free2subjekt WHERE studentId=? AND subjectId IN (SELECT id FROM subject WHERE description='Religion' OR UPPER(short)='REL')", this.getId()) > 0);
			
			int typificationId = db.getInt("SELECT typificationid FROM student2Typification WHERE studentId =?", this.getId());
			this.setTypificationId(typificationId);
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
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
		
		ArrayList<Subject> subjects = ElementLists.getSubjects();
		if(this.getIsGermanFree())
		{
			int subjectId = -1;
			for(Subject subject:subjects)
			{
				if(subject.getShortName().equalsIgnoreCase("Deu") || subject.getDescription().equalsIgnoreCase("Deutsch"))
				{
					subjectId = subject.getId();
					break;
				}
			}
			int count = db.getInt("SELECT COUNT(*) FROM free2subjekt WHERE subjectId = ? AND studentId = ?", subjectId, this.getId());
			if(count == 0 && subjectId > -1)
			{
				db.NoQuery("INSERT INTO free2subjekt (subjectId, studentId, freeDate) VALUES (?,?, SYSDATE())", subjectId, this.getId());
			}
		}
		else
		{
			int subjectId = -1;
			for(Subject subject:subjects)
			{
				if(subject.getShortName().equalsIgnoreCase("Deu") || subject.getDescription().equalsIgnoreCase("Deutsch"))
				{
					subjectId = subject.getId();
					break;
				}
			}
			
			db.NoQuery("DELETE FROM free2subjekt WHERE subjectId = ? AND studentId = ?", subjectId, this.getId());
		}
		
		if(this.getIsReligionFree())
		{
			int subjectId = -1;
			for(Subject subject:subjects)
			{
				if(subject.getShortName().equalsIgnoreCase("Rel") || subject.getDescription().equalsIgnoreCase("Religion"))
				{
					subjectId = subject.getId();
					break;
				}
			}

			int count = db.getInt("SELECT COUNT(*) FROM free2subjekt WHERE subjectId = ? AND studentId = ?", subjectId, this.getId());
			if(count == 0 && subjectId > -1)
			{
				db.NoQuery("INSERT INTO free2subjekt (subjectId, studentId, freeDate) VALUES (?,?, SYSDATE())", subjectId, this.getId());
			}
		}
		else
		{
			int subjectId = -1;
			for(Subject subject:subjects)
			{
				if(subject.getShortName().equalsIgnoreCase("Rel") || subject.getDescription().equalsIgnoreCase("Religion"))
				{
					subjectId = subject.getId();
					break;
				}
			}
			
			db.NoQuery("DELETE FROM free2subjekt WHERE subjectId = ? AND studentId = ?", subjectId, this.getId());
		}
		
		if(this.getIsSportFree())
		{
			int subjectId = -1;
			for(Subject subject:subjects)
			{
				if(subject.getShortName().equalsIgnoreCase("Sp") || subject.getDescription().equalsIgnoreCase("Sport"))
				{
					subjectId = subject.getId();
					break;
				}
			}
			
			int count = db.getInt("SELECT COUNT(*) FROM free2subjekt WHERE subjectId = ? AND studentId = ?", subjectId, this.getId());
			if(count == 0 && subjectId > -1)
			{
				db.NoQuery("INSERT INTO free2subjekt (subjectId, studentId, freeDate) VALUES (?,?, SYSDATE())", subjectId, this.getId());
			}
		}
		else
		{
			int subjectId = -1;
			for(Subject subject:subjects)
			{
				if(subject.getShortName().equalsIgnoreCase("Sp") || subject.getDescription().equalsIgnoreCase("Sport"))
				{
					subjectId = subject.getId();
					break;
				}
			}
			
			db.NoQuery("DELETE FROM free2subjekt WHERE subjectId = ? AND studentId = ?", subjectId, this.getId());
		}
		
		db.NoQuery("UPDATE student2typification SET typificationid =? WHERE studentId =?", this.getTypificationId(), this.getId());
	}
	catch(Exception ex)
	{
		Error.out(ex);
	}
}
	
	public Student setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}
	
	public Student setCity(String city) {
		this.city = city;
		return this;
	}
	
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	
	public Student setEntry(Date entry) {
		this.entry = entry;
		return this;
	}
	
	public Student setGradeId(int gradeId) {
		this.gradeId = gradeId;
		this.grade = null;
		return this;
	}
	
	public Student setGuardianId1(int guardianId1) {
		this.guardianId1 = guardianId1;
		this.guardian1 = null;
		return this;
	}
	public Student setGuardianId2(int guardianId2) {
		this.guardianId2 = guardianId2;
		this.guardian2 = null;
		return this;
	}
	public Student setInstructorId(int intructorId) {
		this.instructor = null;
		this.company = null;
		this.instructorId = intructorId;
		return this;
	}
	public Student setIsGermanFree(Boolean isGermanFree) {
		this.isGermanFree = isGermanFree;
		return this;
	}
	public Student setIsReligionFree(Boolean isReligionFree) {
		this.isReligionFree = isReligionFree;
		return this;
	}
	public Student setIsSportFree(Boolean isSportFree) {
		this.isSportFree = isSportFree;
		return this;
	}
	public Student setJobId(int jobId) {
		this.job = null;
		this.jobId = jobId;
		return this;
	}

	public Student setMarks(ArrayList<ArrayList<Mark>> marks)
	{
		this.marks = marks;
		return this;
	}

	public Student setPlz(String plz) {
		this.plz = plz;
		return this;
	}

	public Student setReligionId(int religionId) {
		this.religion = null;
		this.religionId = religionId;
		return this;
	}
	
	public Student setShortened(Boolean shortened) {
		this.shortened = shortened;
		return this;
	}
	
	public Student setStreet(String street) {
		this.street = street;
		return this;
	}
	public Student setTypificationId(int typificationId) {
		this.typificationId = typificationId;
		this.typification = null;
		return this;
	}
}

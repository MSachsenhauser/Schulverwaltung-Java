package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Exam implements IDatabaseObject<Exam>{
	private int id = -1;
	private int typeid = -1;
	private Date executionDate = new Date();
	private int teacherId = -1;
	private Teacher teacher = null;
	private int subjectId = -1;
	private Subject subject = new Subject();
	private int disableflag = -1;
	private Date announceDate = new Date();

	public Teacher getTeacher() {
		if(teacher == null)
		{
			teacher = new Teacher().setId(this.teacherId).load();
		}
		return teacher;
	}
	public Subject getSubject() {
		if(this.subject == null)
		{
			this.subject = new Subject().setId(this.subjectId).load();
		}
		return subject;
	}
	public int getId() {
		return id;
	}
	public Exam setId(int id) {
		this.id = id;
		return this;
	}
	public int getTypeId() {
		return typeid;
	}
	public Exam setTypeId(int typeId) {
		this.typeid = typeId;
		return this;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public Exam setExecutionDate(Date string) {
		this.executionDate = string;
		return this;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public Exam setTeacherId(int string) {
		this.teacherId = string;
		this.teacher = null;
		return this;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public Exam setSubjectId(int string) {
		this.subjectId = string;
		this.subject = null;
		return this;
	}
	
	public int getDisableflag() {
		return disableflag;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	
	public Date getAnnounceDate() {
		return announceDate;
	}
	public void setAnnounceDate(Date announceDate) {
		this.announceDate = announceDate;
	}
	
	@Override
	public void addToDb() 
	{
		try(Database db = new Database())
		{
			int id = db.getInt("SELECT MAX(Id) FROM exam");
			if(id == -1)
			{
				id = 1;
			}
			else
			{
				id++;
			}
			
			this.setId(id);
			/*
			id int primary key,
			typeId int,
			executionDate date,
			subjectId int,
			teacherId int,
			announceDate date,
			disableflag int default 0
			 * 
			 */
			db.NoQuery("INSERT INTO exam(Id,typeid,executionDate, subjectId, teacherId, announceDate,  disableflag)" +
					   " values(?,?,?,?,?,?,0)",
					   this.getId(), this.getTypeId(), this.getExecutionDate(), this.getSubjectId(), this.getTeacherId(),this.getAnnounceDate());
		}
		
		catch(Exception ex)
		{
			
		}
		
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Exam SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update exam set executionDate = ?, announceDate = ?,subjectId = ?,teacherId = ?, typeId = ?, disableflag  = ? where id = ?",
					this.getExecutionDate(),
					this.getAnnounceDate(),
					this.getSubjectId(),
					this.getTeacherId(),
					this.getTypeId(),
					this.getDisableflag(),
					this.getId());
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}
	@Override
	public Exam load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM exam WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setExecutionDate(result.getDate("executionDate"));
				this.setAnnounceDate(result.getDate("announceDate"));
				this.setSubjectId(result.getInt("subjectId"));
				this.setTeacherId(result.getInt("teacherId"));
				this.setTypeId(result.getInt("typeId"));
				this.setDisableflag(result.getInt("disableflag"));
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
		
	}
}

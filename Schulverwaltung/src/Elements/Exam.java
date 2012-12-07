package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Exam implements IDatabaseObject<Exam>{
	private int id = -1;
	private int typeid = -1;
	private Date examdate = new Date();
	private int teacherId = -1;
	private Teacher teacher = new Teacher();
	private int subjectId = -1;
	private Subject subject = new Subject();
	private int disableflag = -1;
	
	public Teacher getTeacher() {
		return teacher;
	}
	public Subject getSubject() {
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
	public Date getExamdate() {
		return examdate;
	}
	public Exam setExamdate(Date string) {
		this.examdate = string;
		return this;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public Exam setTeacherId(int string) {
		this.teacherId = string;
		return this;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public Exam setSubjectId(int string) {
		this.subjectId = string;
		return this;
	}
	
	public int getDisableflag() {
		return disableflag;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	@Override
	public void addToDb() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeFromDb() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update exam set date = ?,subjectId = ?,teacherId = ?, typeId = ?, disableflag  = ? where id = ?",
					this.getExamdate(),
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
				this.setExamdate(result.getDate("date"));
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

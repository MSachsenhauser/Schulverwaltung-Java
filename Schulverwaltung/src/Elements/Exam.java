package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Exam implements IDatabaseObject<Exam>{
	private int id = -1;
	private int typeId = -1;
	private Date date = new Date();
	private int teacherId = -1;
	private Teacher teacher = new Teacher();
	private int subjectId = -1;
	private Subject subject = new Subject();
	private int sperrkennzeichen = -1;
	
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
		return typeId;
	}
	public Exam setTypeId(int typeId) {
		this.typeId = typeId;
		return this;
	}
	public Date getDate() {
		return date;
	}
	public Exam setDate(Date string) {
		this.date = string;
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
	
	public int getSperrkennzeichen() {
		return sperrkennzeichen;
	}

	public void setSperrkennzeichen(int sperrkennzeichen) {
		this.sperrkennzeichen = sperrkennzeichen;
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public Exam load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM pruefung WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDate(result.getDate("date"));
				this.setSubjectId(result.getInt("subjectId"));
				this.setTeacherId(result.getInt("teacherId"));
				this.setTypeId(result.getInt("typeId"));
				this.setSperrkennzeichen(result.getInt("sperrkennzeichen"));
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
		
	}
}

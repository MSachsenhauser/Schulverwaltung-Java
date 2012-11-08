package Elements;

import java.util.Date;

public class Exam implements IDatabaseObject{
	private int id = -1;
	private int typeId = -1;
	private Date date = new Date();
	private int teacherId = -1;
	private Teacher teacher = new Teacher();
	private int subjectId = -1;
	private Subject subject = new Subject();
	
	public Teacher getTeacher() {
		return teacher;
	}
	public Subject getSubject() {
		return subject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
	public void load() {
		// TODO Auto-generated method stub
		
	}
}

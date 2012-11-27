package Elements;

import java.util.Date;

public class Exam implements IDatabaseObject<Exam>{
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
	public Exam setDate(Date date) {
		this.date = date;
		return this;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public Exam setTeacherId(int teacherId) {
		this.teacherId = teacherId;
		return this;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public Exam setSubjectId(int subjectId) {
		this.subjectId = subjectId;
		return this;
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
		return this;
	}
}

package elements;

import java.sql.ResultSet;
import java.util.Date;

import database.*;
import database.Error;

public class Delay implements IDatabaseObject<Delay>{
	private int id = -1;
	private int studentId = -1;
	private Student student = null;
	private Date start = new Date();
	private Date end = new Date();
	private String description = "";
	private Boolean valid = false;
	public int getId() {
		return id;
	}
	public Delay setId(int id) {
		this.id = id;
		return this;
	}
	public int getStudentId() {
		return studentId;
	}
	public Delay setStudentId(int studentId) {
		this.studentId = studentId;
		this.student = null;
		return this;
	}
	public Date getStart() {
		return start;
	}
	public Delay setStart(Date start) {
		this.start = start;
		return this;
	}
	public Date getEnd() {
		return end;
	}
	public Delay setEnd(Date end) {
		this.end = end;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Delay setDescription(String description) {
		this.description = description;
		return this;
	}
	public Boolean getValid() {
		return valid;
	}
	public Delay setValid(Boolean valid) {
		this.valid = valid;
		return this;
	}
	public Student getStudent() {
		if(student == null)
		{
			this.student = new Student().setId(this.getId()).load();
		}
		return student;
	}
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			db.NoQuery("INSERT INTO Delay (id, studentId, start, end, description, valid) VALUES (?,?,?,?,?,?)",
					this.getId(), this.getStudentId(), this.getStart(), this.getEnd(),
					this.getDescription(), this.getValid() ? 1: 0);
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	@Override
	public void removeFromDb() {
		try(Database db = new Database())
		{
			db.NoQuery("DELETE FROM Delay WHERE Id=?", this.getId());
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
			db.NoQuery("UPDATE delay SET studentId = ?, start=?, end=?, description =?, valid=? WHERE Id=?",
					this.getStudentId(), this.getStart(),this.getEnd(),
					this.getDescription(), this.getValid(), this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	@Override
	public Delay load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM delay WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setStudentId(result.getInt("StudentId"));
				this.setStart(result.getDate("start"));
				this.setEnd(result.getDate("end"));
				this.setValid(result.getInt("valid") == 1);
				this.setDescription(result.getString("description"));
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
}

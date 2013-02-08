package elements;

import java.sql.ResultSet;
import java.util.Date;

import database.*;
import database.Error;

public class Delay implements IDatabaseObject<Delay>{
	private String description = "";
	private Date end = new Date();
	private int id = -1;
	private Date start = new Date();
	private Student student = null;
	private int studentId = -1;
	private Boolean valid = false;
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
	public String getDescription() {
		return description;
	}
	public Date getEnd() {
		return end;
	}
	public int getId() {
		return id;
	}
	public Date getStart() {
		return start;
	}
	public Student getStudent() {
		if(student == null)
		{
			this.student = new Student().setId(this.getId()).load();
		}
		return student;
	}
	public int getStudentId() {
		return studentId;
	}
	public Boolean getValid() {
		return valid;
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
	public Delay setDescription(String description) {
		this.description = description;
		return this;
	}
	public Delay setEnd(Date end) {
		this.end = end;
		return this;
	}
	public Delay setId(int id) {
		this.id = id;
		return this;
	}
	public Delay setStart(Date start) {
		this.start = start;
		return this;
	}
	public Delay setStudentId(int studentId) {
		this.studentId = studentId;
		this.student = null;
		return this;
	}
	public Delay setValid(Boolean valid) {
		this.valid = valid;
		return this;
	}
}

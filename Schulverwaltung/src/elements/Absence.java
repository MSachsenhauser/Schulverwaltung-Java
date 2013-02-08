package elements;

import java.sql.ResultSet;
import java.util.Date;

import database.Database;
import database.Error;


public class Absence implements IDatabaseObject<Absence>{
	private Boolean certificate = false;
	private Date end = new Date();
	private Boolean excusedByEmail = false;
	private Boolean excusedByPhone = false;
	private int id = -1;
	private Date start = new Date();
	private Student student = null;
	private int studentId = -1;
	
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{	
				int id = db.getInt("SELECT MAX(Id) FROM absence");

				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);

				db.NoQuery("INSERT INTO absence(Id, studentId, start, end, excusedByPhone, excusedByEmail, certificate) " + 
						   " values (?,?,?,?,?,?,?)",
						   this.getId(), this.getStudentId(), this.getStart(), this.getEnd(), this.excusedByPhone ? 1 : 0,
						   this.excusedByEmail ? 1 : 0, this.getCertificate() ? 1 : 0);
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public Boolean getCertificate() {
		return certificate;
	}
	public Date getEnd() {
		return end;
	}
	public Boolean getExcusedByEmail() {
		return excusedByEmail;
	}
	public Boolean getExcusedByPhone() {
		return excusedByPhone;
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
			student = new Student().setId(this.getStudentId()).load();
		}
		return student;
	}
	public int getStudentId() {
		return studentId;
	}
	@Override
	public Absence load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM absence WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setStudentId(result.getInt("studentId"));
				this.setStart(result.getDate("start"));
				this.setEnd(result.getDate("end"));
				this.setExcusedByEmail(result.getInt("excusedByEmail") == 1);
				this.setExcusedByPhone(result.getInt("excusedByPhone") == 1);
				this.setCertificate(result.getInt("certificate") == 1);
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
		// TODO Auto-generated method stub
		try(Database db = new Database())
		{
			db.NoQuery("DELETE FROM absence WHERE Id = ?", this.getId());
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
			db.NoQuery("UPDATE absence SET studentId = ?, start = ?, end = ?, excuesByPhone = ?," +
					" excusedByEmail=?, certificate = ? WHERE Id=?",
					this.getStudentId(), this.getStart(), this.getEnd(),
					this.getExcusedByPhone() ? 1 : 0, this.getExcusedByEmail() ? 1 : 0,
					this.getCertificate() ? 1 : 0, this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public Absence setCertificate(Boolean certificate) {
		this.certificate = certificate;
		return this;
	}
	public Absence setEnd(Date end) {
		this.end = end;
		return this;
	}
	public Absence setExcusedByEmail(Boolean excusedByEmail) {
		this.excusedByEmail = excusedByEmail;
		return this;
	}
	
	public Absence setExcusedByPhone(Boolean excusedByPhone) {
		this.excusedByPhone = excusedByPhone;
		return this;
	}
	public Absence setId(int id) {
		this.id = id;
		this.student = null;
		return this;
	}
	public Absence setStart(Date start) {
		this.start = start;
		return this;
	}
	public Absence setStudentId(int studentId) {
		this.studentId = studentId;
		return this;
	}
}

package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Exam implements IDatabaseObject<Exam>{
	private int id = -1;
	private int typeid = -1;
	private Date executionDate = new Date();
	private int groupSubjectId = -1;
	private GroupSubject groupSubject = new GroupSubject();
	private int disableflag = -1;
	private Date announceDate = new Date();
	private MarkType type = null;
	
	public MarkType getMarkType()
	{
		if(this.type == null)
		{
			this.type = new MarkType().setId(this.typeid).load();
		}
		
		return this.type;
	}
	
	public GroupSubject getGroupSubject() {
		if(this.groupSubject == null)
		{
			this.groupSubject = new GroupSubject().setId(this.groupSubjectId).load();
		}
		return groupSubject;
	}
	public int getId() {
		return id;
	}
	public Exam setId(int id) {
		this.id = id;
		return this;
	}
	public int getTypeId() {
		this.type = null;
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
	public int getGroupSubjectId() {
		return groupSubjectId;
	}
	public Exam setGroupSubjectId(int id) {
		this.groupSubjectId = id;
		this.groupSubject = null;
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
			db.NoQuery("INSERT INTO exam(Id,typeid,executionDate, subjectId, announceDate,  disableflag)" +
					   " values(?,?,?,?,?,?,0)",
					   this.getId(), this.getTypeId(), this.getExecutionDate(), this.getGroupSubjectId(), this.getAnnounceDate());
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
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
			db.NoQuery("update exam set executionDate = ?, announceDate = ?,subjectId = ?, typeId = ?, disableflag  = ? where id = ?",
					this.getExecutionDate(),
					this.getAnnounceDate(),
					this.getGroupSubjectId(),
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
				this.setGroupSubjectId(result.getInt("subjectId"));
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

package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Religion implements IDatabaseObject<Religion>{
	private int id = -1;
	private String description ="";
	private int subjectId = -1;
	private int disableflag = -1;
	
	public int getId() {
		return id;
	}
	public Religion setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Religion setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public Religion setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
		try(Database db = new Database())
		{
			int religionId = db.getInt("SELECT id FROM Religion WHERE Description=? AND SubjectId = ?", this.getDescription(), this.getSubjectId());
			if(religionId == -1)
			{
				int id = db.getInt("SELECT MAX(Id) FROM religion");	
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				this.setId(id);
				db.NoQuery("INSERT INTO religion(Id, Description, Subjectid, disableflag)" +
						   " values(?,?,?,0)",
						   this.getId(), this.getDescription(), this.getSubjectId());
			}
			else
			{
				this.setId(religionId);
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Religion SET Disableflag = 1 WHERE Id = ?", this.getId());
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
			db.NoQuery("update religion set description = ?,subjectId = ?,disableflag = ?, where id = ?",
					this.getDescription(),
					this.getSubjectId(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	@Override
	public Religion load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM religion WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setDescription(result.getString("description"));
				this.setSubjectId(result.getInt("subjectId"));
				this.setDisableflag(result.getInt("disableflag"));
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
}

package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Subject implements IDatabaseObject<Subject>{
	private int id = -1;
	private String description ="";
	private int disableflag = -1;
	private String shortName = "";
	public String getShortName() {
		return shortName;
	}
	public Subject setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}
	public int getId() {
		return id;
	}
	public Subject setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Subject setDescription(String description) {
		this.description = description;
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
			int subjectId = db.getInt("SELECT id FROM subject WHERE Description=? ", this.getDescription());
			if(subjectId == -1)
			{

				int id = db.getInt("SELECT MAX(Id) FROM subject");
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);

				db.NoQuery("INSERT INTO subject(Id, description, short, disableflag)" +
						   " values(?,?,?,0)",
						   this.getId(),this.getDescription(), this.getShortName());
			}
			else
			{
				this.setId(subjectId);
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
			db.NoQuery("UPDATE Subject SET Disableflag = 1 WHERE Id = ?", this.getId());
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
			db.NoQuery("update subject set description = ?, disableflag  = ?, short = ? where id = ?",
					this.getDescription(),
					this.getDisableflag(),
					this.getShortName(),
					this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	@Override
	public Subject load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM subject WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setShortName(result.getString("short"));
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

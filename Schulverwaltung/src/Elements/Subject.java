package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

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
			/*
			 *	 id int primary key,
				description varchar(500),
				disableflag int default 0
			 */
			db.NoQuery("INSERT INTO subject(Id, description, disableflag)" +
					   " values(?,?,0)",
					   this.getId(),this.getDescription());
		}
		
		catch(Exception ex)
		{
			
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
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update subject set description = ?, short = ?, disableflag  = ? where id = ?",
					this.getDescription(),
					this.getShortName(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
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
			ex.printStackTrace();
		}
		return this;
	}
}

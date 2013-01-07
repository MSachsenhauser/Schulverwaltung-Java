package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Subject implements IDatabaseObject<Subject>{
	private int id = -1;
	private String description ="";
	private int disableflag = -1;
	
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
		// TODO Auto-generated method stub
		
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
			db.NoQuery("update subject set description = ?, disableflag  = ? where id = ?",
					this.getDescription(),
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
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM subject WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString(description));
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

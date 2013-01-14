package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Group implements IDatabaseObject<Group>{
	private int id = -1;
	private String description = "";
	private int timetableId = -1;
	private int disableflag = -1;
	
	public int getId() {
		return id;
	}
	public Group setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Group setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getTimetableId() {
		return timetableId;
	}
	public Group setTimetableId(int timetableId) {
		this.timetableId = timetableId;
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
			int id = db.getInt("SELECT MAX(Id) FROM group");
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
			 * 	id int primary key,
				description varchar(500),
				disableflag int default 0
			 */
			db.NoQuery("INSERT INTO group(Id, description, disableflag)" +
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
			db.NoQuery("UPDATE Group SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update group set description = ?,timetableId = ?,disableflag = ? where id = ?",
					this.getDescription(),
					this.getTimetableId(),
					this.getDisableflag(),
					this.getId());
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}

	@Override
	public Group load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM group WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setTimetableId(result.getInt("timetableId"));
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

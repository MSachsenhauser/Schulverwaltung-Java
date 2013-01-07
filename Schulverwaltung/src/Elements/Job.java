package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Job implements IDatabaseObject<Job>{
	private int id = -1;
	private String description = "";
	private double duration = 0.00;
	private int disableflag = -1;
	
	public int getId() {
		return id;
	}
	public Job setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Job setDescription(String description) {
		this.description = description;
		return this;
	}
	public double getDuration() {
		return duration;
	}
	public Job setDuration(double duration) {
		this.duration = duration;
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
			db.NoQuery("UPDATE Job SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update job set description = ?,duration = ?,disableflag = ? where id = ?",
					this.getDescription(),
					this.getDuration(),
					this.getDisableflag(),
					this.getId());
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}
	@Override
	public Job load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM job WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setDuration(result.getInt("duration"));
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

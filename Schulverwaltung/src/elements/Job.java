package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Job implements IDatabaseObject<Job>{
	private String description = "";
	private int disableflag = -1;
	private double duration = 0.00;
	private int id = -1;
	
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int jobId = db.getInt("SELECT id FROM job WHERE description=? AND duration = ?", this.getDescription(), this.getDuration());
			if(jobId == -1)
			{
				
			
				int id = db.getInt("SELECT MAX(Id) FROM job");
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
					description varchar (500),
					duration int default 3,
					disableflag int default 0
				 */
				db.NoQuery("INSERT INTO job(Id, description, duration, disableflag )" +
						   " values(?,?,?,0)",
						   this.getId(), this.getDescription(), this.getDuration());
			}
			else
			{
				this.setId(jobId);
			}
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public String getDescription() {
		return description;
	}
	public int getDisableflag() {
		return disableflag;
	}
	public double getDuration() {
		return duration;
	}
	public int getId() {
		return id;
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
			Error.out(ex);
		}
		return this;
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Job SET Disableflag = 1 WHERE Id = ?", this.getId());
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
			db.NoQuery("update job set description = ?,duration = ?,disableflag = ? where id = ?",
					this.getDescription(),
					this.getDuration(),
					this.getDisableflag(),
					this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public Job setDescription(String description) {
		this.description = description;
		return this;
	}
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	public Job setDuration(double duration) {
		this.duration = duration;
		return this;
	}
	public Job setId(int id) {
		this.id = id;
		return this;
	}
}

package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Typification implements IDatabaseObject<Typification>{
	
	private int id = -1;
	private String description = "";
	private int disableflag = -1;
	public int getId() {
		return id;

	}
	public String getDescription() {
		return description;
	}
	public Typification setDescription(String description) {
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
			int id = db.getInt("SELECT MAX(Id) FROM student");
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
				disableflag int default 0
			 */
			db.NoQuery("INSERT INTO typification(Id, description, disableflag)" +
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
			db.NoQuery("UPDATE Typification SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update typification set description = ?, disableflag  = ? where id = ?",
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
	public Typification load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM typification WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setDescription(result.getString("description"));
				this.setDisableflag(result.getInt("sperrkennzeichen"));
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}
	@Override
	public Typification setId(int id) {
		this.id = id;
		return this;
	}
}

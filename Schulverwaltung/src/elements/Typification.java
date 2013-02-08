package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Typification implements IDatabaseObject<Typification>{
	
	private String description = "";
	private int disableflag = -1;
	private int id = -1;
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int typificationId = db.getInt("SELECT id FROM typification WHERE description =?", this.getDescription());
			if(typificationId == -1)
			{
				int id = db.getInt("SELECT MAX(Id) FROM typification");
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);
	
				db.NoQuery("INSERT INTO typification(Id, description, disableflag)" +
						   " values(?,?,0)",
						   this.getId(),this.getDescription());
			}
			else
			{
				this.setId(typificationId);
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
	
	public int getId() {
		return id;

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
				this.setDisableflag(result.getInt("disableFlag"));
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
			db.NoQuery("UPDATE Typification SET Disableflag = 1 WHERE Id = ?", this.getId());
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
			db.NoQuery("update typification set description = ?, disableflag  = ? where id = ?",
					this.getDescription(),
					this.getDisableflag(),
					this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public Typification setDescription(String description) {
		this.description = description;
		return this;
	}
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	@Override
	public Typification setId(int id) {
		this.id = id;
		return this;
	}
}

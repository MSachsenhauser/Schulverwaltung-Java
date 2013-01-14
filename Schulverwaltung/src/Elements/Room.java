package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Room implements IDatabaseObject<Room>{
	private int id = -1;
	private String number = "";
	private String description ="";
	private int disableflag = -1;
	public int getId() {
		return id;
	}
	public Room setId(int id) {
		this.id = id;
		return this;
	}
	public String getNumber() {
		return number;
	}
	public Room setNumber(String number) {
		this.number = number;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Room setDescription(String description) {
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
			int id = db.getInt("SELECT MAX(Id) FROM room");
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
				number varchar (20),
				description varchar (500),
				disableflag int default 0
				
			 */
			db.NoQuery("INSERT INTO room(Id, number, description, disableflag)" +
					   " values(?,?,?,0)",
					   this.getId(), this.getNumber(), this.getDescription());
		}
		
		catch(Exception ex)
		{
			
		}
		
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Room SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update room set description = ?,number = ?, disableflag  = ? where id = ?",
					this.getDescription(),
					this.getNumber(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}
	@Override
	public Room load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM room WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setDescription(result.getString("description"));
				this.setNumber(result.getString("number"));
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

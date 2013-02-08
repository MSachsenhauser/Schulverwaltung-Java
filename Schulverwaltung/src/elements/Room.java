package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Room implements IDatabaseObject<Room>{
	private String description ="";
	private int disableflag = -1;
	private int id = -1;
	private String number = "";
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int roomId = db.getInt("SELECT id FROM room WHERE number=? AND description = ?",this.getNumber(), this.getDescription());
			if(roomId == -1)
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
				db.NoQuery("INSERT INTO room(Id, number, description, disableflag)" +
						   " values(?,?,?,0)",
						   this.getId(), this.getNumber(), this.getDescription());
			}
			else
			{
				this.setId(roomId);
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
	public String getNumber() {
		return number;
	}
	@Override
	public Room load() {
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
			Error.out(ex);
		}
		return this;
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Room SET Disableflag = 1 WHERE Id = ?", this.getId());
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
			db.NoQuery("update room set description = ?,number = ?, disableflag  = ? where id = ?",
					this.getDescription(),
					this.getNumber(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	public Room setDescription(String description) {
		this.description = description;
		return this;
	}
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	public Room setId(int id) {
		this.id = id;
		return this;
	}
	public Room setNumber(String number) {
		this.number = number;
		return this;
	}
}

package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Room implements IDatabaseObject<Room>{
	private int id = -1;
	private String number = "";
	private String description ="";
	private int sperrkennzeichen = -1;
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
	public int getSperrkennzeichen() {
		return sperrkennzeichen;
	}

	public void setSperrkennzeichen(int sperrkennzeichen) {
		this.sperrkennzeichen = sperrkennzeichen;
	}

	@Override
	public void addToDb() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeFromDb() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Room load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM raum WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setDescription(result.getString("description"));
				this.setNumber(result.getString("number"));
				this.setSperrkennzeichen(result.getInt("sperrkennzeichen"));
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}
}

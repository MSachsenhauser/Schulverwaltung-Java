package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Group implements IDatabaseObject<Group>{
	private int id = -1;
	private String description = "";
	private int timetableId = -1;
	private int sperrkennzeichen = -1;
	
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
	public Group load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM gruppe WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setTimetableId(result.getInt("timetableId"));
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

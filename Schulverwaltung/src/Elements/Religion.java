package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Religion implements IDatabaseObject<Religion>{
	private int id = -1;
	private String description ="";
	private int subjectId = -1;
	private int sperrkennzeichen = -1;
	
	public int getId() {
		return id;
	}
	public Religion setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Religion setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public Religion setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
	public Religion load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM religion WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setDescription(result.getString("description"));
				this.setSubjectId(result.getInt("subjectId"));
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

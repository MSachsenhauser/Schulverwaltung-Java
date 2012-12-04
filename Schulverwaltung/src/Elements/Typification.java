package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Typification implements IDatabaseObject<Typification>{
	
	private int id = -1;
	private String bezeichnung = "";
	private int sperrkennzeichen = -1;
	public int getId() {
		return id;

	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public Typification setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
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
	public Typification load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			
		
			ResultSet result = db.getDataRows("SELECT * FROM vorbildung WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setBezeichnung(result.getString("bezeichnung"));
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

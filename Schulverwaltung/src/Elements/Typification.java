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
	public String getBezeichnung() {
		return description;
	}
	public Typification setBezeichnung(String description) {
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
		// TODO Auto-generated method stub
		
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
					this.getBezeichnung(),
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
				
				this.setBezeichnung(result.getString("description"));
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

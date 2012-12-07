package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Guardian extends Person<Guardian>{
	private String street = "";
	private String plz = "";
	private int disableflag = -1;
	public String getStreet() {
		return street;
	}
	public Guardian setStreet(String street) {
		this.street = street;
		return this;
	}
	public String getPlz() {
		return plz;
	}
	public Guardian setPlz(String plz) {
		this.plz = plz;
		return this;
	}
	public String getCity() {
		return city;
	}
	public Guardian setCity(String city) {
		this.city = city;
		return this;
	}
	private String city = "";
	
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update guardian set city = ?,email = ?,firstname = ?, name = ?, plz  = ?,street = ?, phone = ?, disableflag = ? where id = ?",
					this.getCity(),
					this.getEmail(),
					this.getFirstname(),
					this.getName(),
					this.getPlz(),
					this.getStreet(),
					this.getTelefon(),
					this.getDisableflag(),
					this.getId());
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}

	@Override
	public Guardian load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM guardian WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setCity(result.getString("city"));
				this.setEmail(result.getString("email"));
				this.setFirstname(result.getString("firstname"));
				this.setName(result.getString("name"));
				this.setPlz(result.getString("plz"));
				this.setStreet(result.getString("street"));
				this.setTelefon(result.getString("phone"));
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

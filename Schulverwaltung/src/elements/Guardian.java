package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Guardian extends Person<Guardian>{
	private String city = "";
	private int disableflag = -1;
	private String plz = "";
	private String street = "";
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int guardianId = db.getInt("SELECT id FROM guardian WHERE name=? AND firstname=? AND phone=? AND street=? AND city=? AND plz=? ",
				this.getName(), this.getFirstname(), this.getPhone(), this.getStreet(), this.getCity(), this.getPlz());
			if(guardianId == -1)
			{
				int id = db.getInt("SELECT MAX(Id) FROM guardian");
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);
				db.NoQuery("INSERT INTO guardian(Id, Name, Firstname, phone, street, city, plz, " + "" +
						   "disableflag)" +
						   " values(?,?,?,?,?,?,?,0)",
						   this.getId(), this.getName(), this.getFirstname(),this.getPhone(), this.getStreet(), this.getCity(),
						   this.getPlz());
			}
			else
			{
				this.setId(guardianId);
			}
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public String getCity() {
		return city;
	}
	public int getDisableflag() {
		return disableflag;
	}
	public String getPlz() {
		return plz;
	}
	public String getStreet() {
		return street;
	}
	@Override
	public Guardian load() {

		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM guardian WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setCity(result.getString("city"));
				this.setFirstname(result.getString("firstname"));
				this.setName(result.getString("name"));
				this.setPlz(result.getString("plz"));
				this.setStreet(result.getString("street"));
				this.setPhone(result.getString("phone"));
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
			db.NoQuery("UPDATE Guardian SET Disableflag = 1 WHERE Id = ?", this.getId());
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
			db.NoQuery("update guardian set city = ?,firstname = ?, name = ?, plz  = ?,street = ?, phone = ?, disableflag = ? where id = ?",
					this.getCity(),
					this.getFirstname(),
					this.getName(),
					this.getPlz(),
					this.getStreet(),
					this.getPhone(),
					this.getDisableflag(),
					this.getId());
			
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	
	public Guardian setCity(String city) {
		this.city = city;
		return this;
	}
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	public Guardian setPlz(String plz) {
		this.plz = plz;
		return this;
	}

	public Guardian setStreet(String street) {
		this.street = street;
		return this;
	}
}

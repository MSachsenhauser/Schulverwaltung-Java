package elements;

import java.sql.ResultSet;

import database.*;
import database.Error;


public class Company implements IDatabaseObject<Company>{
	private int id = -1;
	private String name = "";
	private String street = "";
	private String plz = "";
	private String city = "";
	private String phone = "";
	private int disableflag = -1;
	
	public Company()
	{
		
	}
	
	public int getId() {
		return id;
	}
	public Company setId(int id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Company setName(String name) {
		this.name = name;
		return this;
	}
	public String getStreet() {
		return street;
	}
	public Company setStreet(String street) {
		this.street = street;
		return this;
	}
	public String getPlz() {
		return plz;
	}
	public Company setPlz(String plz) {
		this.plz = plz;
		return this;
	}
	public String getCity() {
		return city;
	}
	public Company setCity(String city) {
		this.city = city;
		return this;
	}
	public String getPhone() {
		return phone;
	}
	public Company setPhone(String phone) {
		this.phone = phone;
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
			int companyId = db.getInt("SELECT id FROM company WHERE name=? AND street = ? AND city = ? AND plz = ? AND phone = ?", 
					this.getName(), this.getStreet(), this.getCity(), this.getPlz(), this.getPhone());
			if(companyId == -1)
			{
				int id = db.getInt("SELECT MAX(Id) FROM company") ;
									
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);

				db.NoQuery("INSERT INTO company(Id, Name, Street, City, Plz, phone, disableflag)" +
						   " values(?,?,?,?,?,?,0)",
						   this.getId(), this.getName(), this.getStreet(), this.getCity(),
						   this.getPlz(), this.getPhone());
			}
			else
			{
				this.setId(companyId);
			}
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Company SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		try(Database db = new Database())
		{
			db.NoQuery("update company set name = ?,street = ?,city = ?, plz = ?, phone  = ?, disableflag = ? where id = ?",
					this.getName(),
					this.getStreet(),
					this.getCity(),
					this.getPlz(),
					this.getPhone(),
					this.getDisableflag(),
					this.getId());
			
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	@Override
	public Company load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM company WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setName(result.getString("name"));
				this.setCity(result.getString("city"));
				this.setPhone(result.getString("phone"));
				this.setPlz(result.getString("plz"));
				this.setStreet(result.getString("street"));
				this.setDisableflag(result.getInt("disableflag"));
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
}

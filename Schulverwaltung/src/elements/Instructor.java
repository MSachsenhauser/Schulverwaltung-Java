package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Instructor extends Person<Instructor>{
	private Company company = null;
	private int companyId = -1;
	private int disableflag = -1;
	
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int instructorId = db.getInt("SELECT id FROM instructor WHERE name=? AND firstname = ? AND phone = ? AND email = ? AND companyid = ?", 
				this.getName(), this.getFirstname(), this.getPhone(), this.getEmail(), this.getCompanyId());
			if(instructorId == -1)
			{
			
				int id = db.getInt("SELECT MAX(Id) FROM instructor");
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);
				db.NoQuery("INSERT INTO instructor(Id, Name, Firstname, phone, email, companyid, disableflag)" +
						   " values(?,?,?,?,?,?,0)",
						   this.getId(), this.getName(), this.getFirstname(), this.getPhone(), this.getEmail(), this.getCompanyId());
			}
			else
			{
				this.setId(instructorId);
			}
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
		
	}
	public Company getCompany() {
		if(this.company == null)
		{
			this.company = new Company().setId(this.companyId).load();
		}
		return company;
	}

	public int getCompanyId() {
		return companyId;
	}
	public int getDisableflag() {
		return disableflag;
	}

	@Override
	public Instructor load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM instructor WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setCompanyId(result.getInt("companyId"));
				this.setEmail(result.getString("email"));
				this.setFirstname(result.getString("firstname"));
				this.setName(result.getString("name"));
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
			db.NoQuery("UPDATE Instructor SET Disableflag = 1 WHERE Id = ?", this.getId());
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
			db.NoQuery("update instructor set companyId = ?,email = ?,firstname = ?, name = ?,phone = ?, disableflag  = ? where id = ?",
					this.getCompanyId(),
					this.getEmail(),
					this.getFirstname(),
					this.getName(),
					this.getPhone(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	public Instructor setCompanyId(int companyId) {
		this.companyId = companyId;
		this.company = null;
		return this;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
}

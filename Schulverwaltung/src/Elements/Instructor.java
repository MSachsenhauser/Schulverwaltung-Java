package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Instructor extends Person<Instructor>{
	private int companyId = -1;
	private int disableflag = -1;
	private Company company = null;
	
	public Company getCompany() {
		if(this.company == null)
		{
			this.company = new Company().setId(this.companyId).load();
		}
		return company;
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
		// TODO Auto-generated method stub
		
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
					this.getTelefon(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}
	public int getCompanyId() {
		return companyId;
	}

	public Instructor setCompanyId(int companyId) {
		this.companyId = companyId;
		this.company = null;
		return this;
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

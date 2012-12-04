package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Instructor extends Person<Instructor>{
	private int companyId = -1;
	private int sperrkennzeichen = -1;
	private Company company = new Company();
	
	public Company getCompany() {
		return company;
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

	public int getCompanyId() {
		return companyId;
	}

	public Instructor setCompanyId(int companyId) {
		this.companyId = companyId;
		this.company = new Company(companyId);
		return this;
	}

	@Override
	public Instructor load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM ausbilder WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setCompanyId(result.getInt("companyId"));
				this.setEmail(result.getString("email"));
				this.setFirstname(result.getString("firstname"));
				this.setName(result.getString("name"));
				this.setTelefon(result.getString("phone"));
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

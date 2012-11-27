package Elements;

public class Instructor extends Person<Instructor>{
	private int companyId = -1;
	private Company company = new Company();
	
	public Company getCompany() {
		return company;
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
		// TODO Auto-generated method stub
		return this;
	}
}

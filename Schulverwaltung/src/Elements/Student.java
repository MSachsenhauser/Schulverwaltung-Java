package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Student extends Person<Student>{
	private Date birthday = new Date();
	private Date entry = new Date();
	private int companyId = -1;
	private int jobId = -1;
	private int religionId = -1;
	private Boolean shortened = false;
	private String street = "";
	private String plz = "";
	private String city = "";
	private int sperrkennzeichen = -1;
	
	public Student()
	{
	}
	
	public Student(int id)
	{
		this.setId(id);
	}
	
	public String getStreet() {
		return street;
	}
	public Student setStreet(String street) {
		this.street = street;
		return this;
	}
	public String getPlz() {
		return plz;
	}
	public Student setPlz(String plz) {
		this.plz = plz;
		return this;
	}
	public String getCity() {
		return city;
	}
	public Student setCity(String city) {
		this.city = city;
		return this;
	}

	public Date getBirthday() {
		return birthday;
	}
	public Student setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}
	public Date getEntry() {
		return entry;
	}
	public Student setEntry(Date entry) {
		this.entry = entry;
		return this;
	}
	public int getCompanyId() {
		return companyId;
	}
	public Student setCompanyId(int companyId) {
		this.companyId = companyId;
		return this;
	}
	
	public Company getCompany()
	{
		return new Company();
	}
	
	public Job getJob()
	{
		return new Job();//.setId(this.jobId).load();
	}
	
	public Religion getReligion()
	{
		return new Religion();//.setId(this.religionId).load();
	}
	
	public int getJobId() {
		return jobId;
	}
	public Student setJobId(int jobId) {
		this.jobId = jobId;
		return this;
	}
	public int getReligionId() {
		return religionId;
	}
	public Student setReligionId(int religionId) {
		this.religionId = religionId;
		return this;
	}
	public Boolean getShortened() {
		return shortened;
	}
	public Student setShortened(Boolean shortened) {
		this.shortened = shortened;
		return this;
	}
	public int getSperrkennzeichen() {
		return sperrkennzeichen;
	}

	public void setSperrkennzeichen(int sperrkennzeichen) {
		this.sperrkennzeichen = sperrkennzeichen;
	}
	
	@Override
	public void addToDb()
	{
		
	}
	
	@Override
	public void removeFromDb()
	{
		
	}
	
	@Override
	public void save()
	{
		
	}
	@Override
	public Student load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM schueler WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setBirthday(result.getDate("birthday"));
				this.setCity(result.getString("city"));
				this.setCompanyId(result.getInt("companyId"));
				this.setEmail(result.getString("email"));
				this.setEntry(result.getDate("entry"));
				this.setFirstname(result.getString("firstname"));
				this.setJobId(result.getInt("jobId"));
				this.setName(result.getString("name"));
				this.setPlz(result.getString("plz"));
				this.setReligionId(result.getInt("religionId"));
				this.setShortened(result.getBoolean("shortened"));
				this.setBirthday(result.getDate("birthday"));
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

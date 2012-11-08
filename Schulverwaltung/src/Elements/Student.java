package Elements;

import java.util.Date;

public class Student extends Person{
	private Date birthday = new Date();
	private Date entry = new Date();
	private int companyId = -1;
	private int jobId = -1;
	private int religionId = -1;
	private Boolean shortened = false;
	private String street = "";
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	private String plz = "";
	private String city = "";
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getEntry() {
		return entry;
	}
	public void setEntry(Date entry) {
		this.entry = entry;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public int getReligionId() {
		return religionId;
	}
	public void setReligionId(int religionId) {
		this.religionId = religionId;
	}
	public Boolean getShortened() {
		return shortened;
	}
	public void setShortened(Boolean shortened) {
		this.shortened = shortened;
	}
	
	public void addToDb()
	{
		
	}
	
	public void removeFromDb()
	{
		
	}
	
	public void save()
	{
		
	}
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
}

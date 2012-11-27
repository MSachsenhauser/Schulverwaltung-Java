package Elements;

public class Guardian extends Person<Guardian>{
	private String street = "";
	private String plz = "";
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
	@Override
	public Guardian load() {
		// TODO Auto-generated method stub
		return this;
	}
}

package Elements;

public class Company implements IDatabaseObject{
	private int id = -1;
	private String name = "";
	private String street = "";
	private String plz = "";
	private String city = "";
	private String phone = "";
	
	public Company()
	{
		
	}
	
	public Company(int id)
	{
		// Hier Firma nachladen
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
}

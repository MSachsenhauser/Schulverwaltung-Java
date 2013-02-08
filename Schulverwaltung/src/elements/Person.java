package elements;

@SuppressWarnings("unchecked")
public abstract class Person <T> implements IDatabaseObject<T> {
	private String email ="";
	private String firstname ="";
	private int id = -1;
	private String name ="";
	private String phone ="";
	
	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}
	public String getFullName()
	{
		return this.getName() + " " + this.getFirstname();
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public T setEmail(String email) {
		this.email = email;
		return (T) this;
	}
	public T setFirstname(String firstname) {
		this.firstname = firstname;
		return (T) this;
	}
	public T setId(int id) {
		this.id = id;
		return (T) this;
	}
	public T setName(String name) {
		this.name = name;
		return (T) this;
	}
	
	public T setPhone(String phone) {
		this.phone = phone;
		return (T) this;
	}
}

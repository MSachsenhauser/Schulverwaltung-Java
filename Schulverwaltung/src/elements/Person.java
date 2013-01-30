package elements;

@SuppressWarnings("unchecked")
public abstract class Person <T> implements IDatabaseObject<T> {
	private int id = -1;
	private String name ="";
	private String firstname ="";
	private String phone ="";
	private String email ="";
	
	public int getId() {
		return id;
	}

	public T setId(int id) {
		this.id = id;
		return (T) this;
	}
	public String getName() {
		return name;
	}
	public T setName(String name) {
		this.name = name;
		return (T) this;
	}
	public String getFirstname() {
		return firstname;
	}
	public T setFirstname(String firstname) {
		this.firstname = firstname;
		return (T) this;
	}
	public String getPhone() {
		return phone;
	}
	public T setPhone(String phone) {
		this.phone = phone;
		return (T) this;
	}
	public String getEmail() {
		return email;
	}
	public T setEmail(String email) {
		this.email = email;
		return (T) this;
	}
}

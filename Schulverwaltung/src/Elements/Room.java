package Elements;

public class Room implements IDatabaseObject<Room>{
	private int id = -1;
	private String number = "";
	private String description ="";
	public int getId() {
		return id;
	}
	public Room setId(int id) {
		this.id = id;
		return this;
	}
	public String getNumber() {
		return number;
	}
	public Room setNumber(String number) {
		this.number = number;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Room setDescription(String description) {
		this.description = description;
		return this;
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
	public Room load() {
		// TODO Auto-generated method stub
		return this;
	}
}

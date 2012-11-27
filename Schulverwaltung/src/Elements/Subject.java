package Elements;

public class Subject implements IDatabaseObject<Subject>{
	private int id = -1;
	private String description ="";
	
	public int getId() {
		return id;
	}
	public Subject setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Subject setDescription(String description) {
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
	public Subject load() {
		// TODO Auto-generated method stub
		return this;
	}
}

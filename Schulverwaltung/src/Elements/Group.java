package Elements;

public class Group implements IDatabaseObject<Group>{
	private int id = -1;
	private String description = "";
	private int timetableId = -1;
	
	public int getId() {
		return id;
	}
	public Group setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Group setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getTimetableId() {
		return timetableId;
	}
	public Group setTimetableId(int timetableId) {
		this.timetableId = timetableId;
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
	public Group load() {
		// TODO Auto-generated method stub
		return this;
	}
}

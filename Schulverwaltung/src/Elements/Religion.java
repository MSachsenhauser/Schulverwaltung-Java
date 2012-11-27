package Elements;

public class Religion implements IDatabaseObject<Religion>{
	private int id = -1;
	private String description ="";
	private int subjectId = -1;
	
	public int getId() {
		return id;
	}
	public Religion setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Religion setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public Religion setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
	public Religion load() {
		// TODO Auto-generated method stub
		return this;
	}
}

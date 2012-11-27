package Elements;

public class Job implements IDatabaseObject<Job>{
	private int id = -1;
	private String description = "";
	private double duration = 0.00;
	
	public int getId() {
		return id;
	}
	public Job setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Job setDescription(String description) {
		this.description = description;
		return this;
	}
	public double getDuration() {
		return duration;
	}
	public Job setDuration(double duration) {
		this.duration = duration;
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
	public Job load() {
		// TODO Auto-generated method stub
		return this;
	}
}

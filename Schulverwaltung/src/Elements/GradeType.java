package Elements;

public class GradeType implements IDatabaseObject<GradeType>{
	private int id = -1;
	private String description ="";
	private double weight = 0.00;
	
	public int getId() {
		return id;
	}
	public GradeType setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public GradeType setDescription(String description) {
		this.description = description;
		return this;
	}
	public double getWeight() {
		return weight;
	}
	public GradeType setWeight(double weight) {
		this.weight = weight;
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
	public GradeType load() {
		// TODO Auto-generated method stub
		return this;
	}
}

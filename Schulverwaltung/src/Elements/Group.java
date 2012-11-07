package Elements;

public class Group {
	private int id = -1;
	private String description = "";
	private int timetableId = -1;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTimetableId() {
		return timetableId;
	}
	public void setTimetableId(int timetableId) {
		this.timetableId = timetableId;
	}
}

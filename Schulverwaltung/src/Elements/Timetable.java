package Elements;

import java.util.Date;

public class Timetable implements IDatabaseObject<Timetable>{
	private int id = -1;
	private Date validTill = new Date();
	
	public int getId() {
		return id;
	}
	public Timetable setId(int id) {
		this.id = id;
		return this;
	}
	public Date getValidTill() {
		return validTill;
	}
	public Timetable setValidTill(Date validTill) {
		this.validTill = validTill;
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
	public Timetable load() {
		// TODO Auto-generated method stub
		return this;
	}
}

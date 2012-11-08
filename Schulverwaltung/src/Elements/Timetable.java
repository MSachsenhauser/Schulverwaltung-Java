package Elements;

import java.util.Date;

public class Timetable implements IDatabaseObject{
	private int id = -1;
	private Date validTill = new Date();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getValidTill() {
		return validTill;
	}
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
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
	public void load() {
		// TODO Auto-generated method stub
		
	}
}

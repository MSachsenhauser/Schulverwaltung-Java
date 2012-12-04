package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Grade implements IDatabaseObject<Grade>{
	private int id = -1;
	private String description = "";
	private int roomId = -1;
	private int teacherId = -1;
	private Teacher gradeteacher = new Teacher();
	private int sperrkennzeichen = -1;
	
	public int getId() {
		return id;
	}
	public Grade setId(int id) {
		this.id = id;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Grade setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getRoomId() {
		return roomId;
	}
	public Grade setRoomId(int roomId) {
		this.roomId = roomId;
		return this;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public Grade setTeacherId(int teacherId) {
		this.teacherId = teacherId;
		return this;
	}
	public Teacher getGradeTeacher() {
		return gradeteacher;
	}
	public int getSperrkennzeichen() {
		return sperrkennzeichen;
	}

	public void setSperrkennzeichen(int sperrkennzeichen) {
		this.sperrkennzeichen = sperrkennzeichen;
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
	public Grade load() {
		// TODO Auto-generated method stub
				
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM klasse WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setDescription(result.getString("description"));
				this.setRoomId(result.getInt("roomId"));
				this.setTeacherId(result.getInt("teacherId"));
				this.setSperrkennzeichen(result.getInt("sperrkennzeichen"));
				
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}
}

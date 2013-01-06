package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Grade implements IDatabaseObject<Grade>{
	private int id = -1;
	private String description = "";
	private int roomId = -1;
	private int teacherId = -1;
	private Teacher teacher = null;
	private int disableflag = -1;
	
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
		this.teacher = null;
		return this;
	}
	public Teacher getTeacher() {
		if(teacher == null)
		{
			teacher = new Teacher().setId(this.teacherId).load();
		}
		return teacher;
	}
	public int getDisableflag() {
		return disableflag;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
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
		try(Database db = new Database())
		{
			db.NoQuery("update grade set description = ?,roomId = ?,teacherId = ?, disableflag = ? where id = ?",
					this.getDescription(),
					this.getRoomId(),
					this.getTeacherId(),
					this.getDisableflag(),
					this.getId());
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	

		
	}
	@Override
	public Grade load() {
		// TODO Auto-generated method stub
				
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM grade WHERE Id=?", this.getId());
			while(result.next())
			{
				
				this.setDescription(result.getString("description"));
				this.setRoomId(result.getInt("roomId"));
				this.setTeacherId(result.getInt("teacherId"));
				this.setDisableflag(result.getInt("disableflag"));
				
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}
}

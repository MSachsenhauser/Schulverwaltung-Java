package elements;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;
import database.Error;


public class Grade implements IDatabaseObject<Grade>{
	private String description = "";
	private int disableflag = -1;
	private ArrayList<Group> groups = new ArrayList<Group>();
	private int id = -1;
	private Room room = null;
	private int roomId = -1;
	private Teacher teacher = null;
	private int teacherId = -1;
	
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
				int id = db.getInt("SELECT MAX(Id) FROM grade");
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);
				db.NoQuery("INSERT INTO grade(Id, description, roomId, teacherId, disableflag)" +
						   " values(?,?,?,?,0)",
						   this.getId(), this.getDescription(), this.getRoomId(), this.getTeacherId());
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}


	public String getDescription() {
		return description;
	}


	public int getDisableflag() {
		return disableflag;
	}
	
	public ArrayList<Group> getGroups() {
		return groups;
	}
	public int getId() {
		return id;
	}
	public Room getRoom()
	{
		room = new Room().setId(this.roomId).load();
		return room;
	}
	public int getRoomId() {
		return roomId;
	}
	public Teacher getTeacher() {
		if(teacher == null)
		{
			teacher = new Teacher().setId(this.teacherId).load();
		}
		return teacher;
	}
	public int getTeacherId() {
		return teacherId;
	}
	@Override
	public Grade load() {
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
			
			result.close();
			result = db.getDataRows("SELECT id FROM gradeGroup WHERE GradeId=?", this.getId());
			while(result.next())
			{
				this.groups.add(new Group().setId(result.getInt("Id")).load());
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Grade SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
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
			Error.out(ex);
		}
	}
	public Grade setDescription(String description) {
		this.description = description;
		return this;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}
	
	public Grade setId(int id) {
		this.id = id;
		return this;
	}
	public Grade setRoomId(int roomId) {
		this.roomId = roomId;
		return this;
	}
	public Grade setTeacherId(int teacherId) {
		this.teacherId = teacherId;
		this.teacher = null;
		return this;
	}
}

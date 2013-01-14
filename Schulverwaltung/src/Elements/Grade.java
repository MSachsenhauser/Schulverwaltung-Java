package Elements;

import java.sql.ResultSet;
import java.util.ArrayList;

import Database.Database;
import Database.Error;

public class Grade implements IDatabaseObject<Grade>{
	private int id = -1;
	private String description = "";
	private int roomId = -1;
	private int teacherId = -1;
	private Teacher teacher = null;
	private int disableflag = -1;
	private Room room = null;
	private ArrayList<Group> groups = new ArrayList<Group>();
	public ArrayList<Group> getGroups() {
		return groups;
	}
	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}
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
		this.room = null;
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
	
	public Room getRoom()
	{
		if(room == null)
		{
			room = new Room().setId(this.roomId).load();
		}
		return room;
	}
	
	public int getDisableflag() {
		return disableflag;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	@Override
	public void addToDb() {
		try (Database db = new Database())
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
			db.NoQuery("INSERT INTO grade (id, description, roomId, teacherId, disableflag) VALUES (?,?,?,?,0)", this.getId(), this.getDescription(), this.getRoomId(), this.getTeacherId());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
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
			/*
			 * 	id int primary key,
				description varchar (500),
				roomId int (100),
				teacherId int (100),
				disableflag int default 0
			 */
			db.NoQuery("INSERT INTO grade(Id, description, roomId, teacherId, disableflag)" +
					   " values(?,?,?,?,0)",
					   this.getId(), this.getDescription(), this.getRoomId(), this.getTeacherId());
		}
		
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Grade SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
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
			Error.Out(ex);
		}
	

		
	}
	@Override
	public Grade load() {
		// TODO Auto-generated method stub
				
		try(Database db = new Database())
		{
			this.setGroups(new ArrayList<Group>());
			ResultSet result = db.getDataRows("SELECT * FROM grade WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setRoomId(result.getInt("roomId"));
				this.setTeacherId(result.getInt("teacherId"));
				this.setDisableflag(result.getInt("disableflag"));
			}
			result.close();
			result = db.getDataRows("SELECT groupId FROM group2grade WHERE gradeId=?", this.getId());
			while(result.next())
			{
				this.groups.add(new Group().setId(result.getInt(1)).load());
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}
}

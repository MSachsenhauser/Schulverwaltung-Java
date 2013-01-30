package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class GroupSubject implements IDatabaseObject<GroupSubject> {
	private int id = -1;
	private int teacherId = -1;
	private int roomId = -1;
	private int subjectId = -1;
	private int disableflag = -1;
	private int groupId = -1;
	private String description = "";
	
	public String getDescription()
	{
		return this.description;
	}
	
	public GroupSubject setDescription(String description)
	{
		this.description = description;
		return this;
	}
	
	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.room = null;
		this.roomId = roomId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subject = null;
		this.subjectId = subjectId;
	}

	public int getDisableflag() {
		return disableflag;
	}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.group = null;
		this.groupId = groupId;
	}

	public Teacher getTeacher() {
		if(teacher == null)
		{
			teacher = new Teacher().setId(this.teacherId).load();
		}
		return teacher;
	}

	public Subject getSubject() {
		if(subject == null)
		{
			subject = new Subject().setId(this.subjectId).load();
		}
		return subject;
	}

	public Room getRoom() {
		if(room == null)
		{
			room = new Room().setId(this.roomId).load();
		}
		return room;
	}

	private Teacher teacher = null;
	private Subject subject = null;
	private Room room = null;
	
	private Group group = null;
	
	public Group getGroup()
	{
		if(this.group == null)
		{
			group = new Group().setId(this.groupId).load();
		}
		return this.group;
	}
	
	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int id = db.getInt("SELECT MAX(Id) FROM group2subject");
			if(id == -1)
			{
				id = 1;
			}
			else
			{
				id++;
			}
			
			this.setId(id);
			
			db.NoQuery("INSERT INTO group2subject (id, description, groupid, roomid, subjectid, teacherid, disableflag) VALUES (?, ?, ?, ?, ?, ?, ?)", this.getDescription(), this.getGroupId(), this.getRoomId(), this.getSubjectId(), this.getTeacherId(), this.getDisableflag(), this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	@Override
	public void removeFromDb() {
		try(Database db = new Database())
		{
			int result = db.getInt("SELECT COUNT(*) FROM exam WHERE group2subjectId=?", this.getId());
			if(result == 0)
			{
				db.NoQuery("DELETE FROM group2subject WHERE Id = ?", this.getId());
			}
			else
			{
				db.NoQuery("UPDATE group2subject SET disableflag = -1 WHERE Id=?",  this.getId());
			}
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
			db.NoQuery("UPDATE group2subject SET description = ?, groupId = ?, roomId = ?, subjectId = ?, teacherId = ?, disableflag = ? WHERE id = ?", this.getDescription(), this.getGroupId(), this.getRoomId(), this.getSubjectId(), this.getTeacherId(), this.getDisableflag(), this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	@Override
	public GroupSubject load() {
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM group2subject WHERE Id = ?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("Description"));
				this.setGroupId(result.getInt("GroupId"));
				this.setRoomId(result.getInt("RoomId"));
				this.setSubjectId(result.getInt("SubjectId"));
				this.setTeacherId(result.getInt("TeacherId"));
				this.setDisableflag(result.getInt("DisableFlag"));
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}

	@Override
	public GroupSubject setId(int id) {
		this.id = id;
		return this;
	}

	public int getId() {
		return id;
	}
}

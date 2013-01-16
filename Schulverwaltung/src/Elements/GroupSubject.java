package Elements;

public class GroupSubject implements IDatabaseObject<GroupSubject> {
	private int id = -1;
	private int teacherId = -1;
	private int roomId = -1;
	private int subjectId = -1;
	private int disableflag = -1;
	private int groupId = -1;
	
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
	public GroupSubject load() {
		// TODO Auto-generated method stub
		return null;
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

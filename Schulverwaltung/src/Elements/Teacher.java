package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Teacher extends Person<Teacher>{
	private Date birthday = new Date();
	private int roomId = -1;
	private double workhours = 0.00;
	private int disableflag = -1;
	private String shortName ="";
	
	public String getShortName() {
		return shortName;
	}

	public Teacher setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}

	public Date getBirthday() {
			return birthday;
		}

		public Teacher setBirthday(Date birthday) {
			this.birthday = birthday;
			return this;
		}

		public int getRoomId() {
			return roomId;
		}

		public Teacher setRoomId(int roomId) {
			this.roomId = roomId;
			return this;
		}

		public double getWorkhours() {
			return workhours;
		}

		public Teacher setWorkhours(double workhours) {
			this.workhours = workhours;
			return this;
		}
		
		public int getDisableflag() {
			return disableflag;
		}

		public void setDisableflag(int disableflag) {
			this.disableflag = disableflag;
		}

	@Override
	public void addToDb() {
		try(Database db = new Database())
		{
			int teacherId = db.getInt("SELECT id FROM teacher WHERE name=? AND firstname = ? and short = ?and phone = ? and email = ? and roomid=? and birthday = ? and workhours = ?",
				this.getName(), this.getFirstname(), this.getShortName(), this.getPhone(), 
				this.getEmail(), this.getRoomId(), this.getBirthday(), this.getWorkhours());
			if(teacherId == -1)
			{
			
				int id = db.getInt("SELECT MAX(Id) FROM teacher");
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
				 	id int primary key,
					name varchar (100),
					firstname varchar (100),
					phone varchar (100),
					email varchar (100),
					roomid int,
					birthday date,
					workhours dec(2,2),
					disableflag int default 0
				 */
				db.NoQuery("INSERT INTO teacher(Id, Name, Firstname, short, phone, email, roomid, Birthday, " + "" +
						   "workhours,disableflag)" +
						   " values(?,?,?,?,?,?,?,?,?,0)",
						   this.getId(), this.getName(), this.getFirstname(),this.getShortName(), this.getPhone(), this.getEmail(), this.getRoomId(), this.getBirthday(),
						   this.getWorkhours());
			}
			else
			{
				this.setId(teacherId);
			}
		}
		
		catch(Exception ex)
		{
			
		}
		
	}

	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Teacher SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}

	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update teacher set birthday = ?,email = ?,firstname = ?, name = ?, roomId = ?,phone = ?,workhours = ?, disableflag  = ?, short = ? where id = ?",
					this.getBirthday(),
					this.getEmail(),
					this.getFirstname(),
					this.getName(),
					this.getRoomId(),
					this.getPhone(),
					this.getWorkhours(),
					this.getDisableflag(),
					this.getShortName(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}

	@Override
	public Teacher load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM teacher WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setBirthday(result.getDate("birthday"));
				this.setEmail(result.getString("email"));
				this.setFirstname(result.getString("firstname"));
				this.setName(result.getString("name"));
				this.setRoomId(result.getInt("roomId"));
				this.setPhone(result.getString("phone"));
				this.setWorkhours(result.getDouble("workhours"));
				this.setDisableflag(result.getInt("disableflag"));
				this.setShortName(result.getString("short"));
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}

}

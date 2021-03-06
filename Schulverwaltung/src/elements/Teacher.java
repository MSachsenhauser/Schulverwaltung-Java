package elements;

import java.sql.ResultSet;
import java.util.Date;

import database.Database;
import database.Error;


public class Teacher extends Person<Teacher>{
	private Date birthday = new Date();
	private int disableflag = -1;
	private int roomId = -1;
	private String shortName ="";
	private double workhours = 0.00;
	
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
			Error.out(ex);
		}
	}

	public Date getBirthday() {
			return birthday;
		}

	public int getDisableflag() {
		return disableflag;
	}

		public int getRoomId() {
			return roomId;
		}

		public String getShortName() {
			return shortName;
		}

		public double getWorkhours() {
			return workhours;
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
				Error.out(ex);
			}
			return this;
		}

		@Override
		public void removeFromDb() {
			try (Database db = new Database())
			{
				db.NoQuery("UPDATE Teacher SET Disableflag = 1 WHERE Id = ?", this.getId());
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
				Error.out(ex);
			}
		}

		public Teacher setBirthday(Date birthday) {
			this.birthday = birthday;
			return this;
		}

	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}

	public Teacher setRoomId(int roomId) {
		this.roomId = roomId;
		return this;
	}

	public Teacher setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}

	public Teacher setWorkhours(double workhours) {
		this.workhours = workhours;
		return this;
	}

}
